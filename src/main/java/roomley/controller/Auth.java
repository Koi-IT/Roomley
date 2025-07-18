package roomley.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import roomley.auth.*;
import roomley.persistence.GenericDao;
import roomley.entities.User;
import roomley.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Authenticates users through aws and creates a user session
 * Inspired by: https://stackoverflow.com/questions/52144721/how-to-get-access-token-using-client-credentials-using-java-code
 */
@WebServlet(
        urlPatterns = {"/auth"}
)
// TODO if something goes wrong it this process, route to an error page. Currently, errors are only caught and logged.
public class Auth extends HttpServlet implements PropertiesLoader {
    Properties properties;
    String CLIENT_ID;
    String CLIENT_SECRET;
    String OAUTH_URL;
    String LOGIN_URL;
    String REDIRECT_URL;
    String REGION;
    String POOL_ID;
    String JDBC_URL;
    String DB_USERNAME;
    String DB_PASSWORD;
    String Last_Login;
    Keys jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Init authorization, load properties and keys
     * @throws ServletException servlet exception
     */
    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
        loadKey();
    }

    /**
     * Gets the auth code from the request and exchanges it for a token containing user info.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException servlet exception
     * @throws IOException IO exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authCode = req.getParameter("code");
        String userName;

        if (authCode == null) {
            //TODO forward to an error page or back to the login

        } else {
            HttpRequest authRequest = buildAuthRequest(authCode);

            try {
                TokenResponse tokenResponse = getToken(authRequest);
                userName = validate(tokenResponse, req);
                req.setAttribute("userName", userName);

            } catch (IOException e) {
                logger.error("Error getting or validating the token: " + e.getMessage(), e);
                //TODO forward to an error page

            } catch (InterruptedException e) {
                logger.error("Error getting token from Cognito oauth url " + e.getMessage(), e);
                //TODO forward to an error page

            }

        }
        resp.sendRedirect("taskGrabber");

    }

    /**
     * Sends the request for a token to Cognito and maps the response
     * @param authRequest the request to the oauth2/token url in cognito
     * @return response from the oauth2/token endpoint which should include id token, access token and refresh token
     * @throws IOException IO Exception
     * @throws InterruptedException Interrupted Exception
     */
    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response;

        response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());

        logger.debug("Response headers: " + response.headers().toString());
        logger.debug("Response body: " + response.body().toString());

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
        logger.debug("Id token: " + tokenResponse.getIdToken());

        return tokenResponse;

    }

    /**
     * Get values out of the header to verify the token is legit. If it is legit, get the claims from it, such
     * as username.
     * @param tokenResponse Token response
     * @return username
     * @throws IOException IO Exception
     */
    private String validate(TokenResponse tokenResponse, HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);

        String keyId = tokenHeader.getKid();
        String alg = tokenHeader.getAlg();

        BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getN()));
        BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getE()));

        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error("Key generation error: " + e.getMessage(), e);

        }

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
        String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .withClaim("token_use", "id")
                .acceptLeeway(60)
                .build();

        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());

        String username = jwt.getClaim("cognito:username").asString();
        String userEmail = jwt.getClaim("email").asString();
        String userSub = jwt.getClaim("sub").asString();

        logger.debug("JWT: " + jwt.getClaims().toString());
        logger.debug("Username: " + username);
        logger.debug("Email: " + userEmail);
        logger.debug("Sub: " + userSub);

        List<String> userGroups = jwt.getClaim("cognito:groups").asList(String.class);
        String role = (userGroups != null && !userGroups.isEmpty()) ? userGroups.get(0) : "User";

        logger.debug("User groups: " + userGroups);
        logger.debug("Assigned role: " + role);

        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        List<User> users = userDao.getByPropertyEqual("cognitoSub", userSub);

        if (users == null || users.isEmpty()) {
            logger.info("No user found with cognitoSub: " + userSub + ". Inserting new user.");
            insertDataIntoRDS(userSub, userEmail, username, role, req);
        } else {
            logger.info("Found " + users.size() + " user(s) with cognitoSub: " + userSub);
            User user = users.get(0);
            createUserSession(req, user);
        }



        logger.debug("JWT Sub: " + jwt.getClaim("sub").asString());
        logger.debug("All available claims: " + jwt.getClaims());

        return username;
    }


    /** Create the auth url and use it to build the request.
     *
     * @param authCode auth code received from Cognito as part of the login process
     * @return the constructed oauth request
     */
    private HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        return HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();

    }

    /**
     * Gets the JSON Web Key Set (JWKS) for the user pool from cognito and loads it
     * into objects for easier use.
     * JSON Web Key Set (JWKS) location: https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
     * Demo url: https://cognito-idp.us-east-2.amazonaws.com/us-east-2_XaRYHsmKB/.well-known/jwks.json
     *
     * @see Keys
     * @see KeysItem
     */
    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("Keys are loaded. Here's e: " + jwks.getKeys().get(0).getE());

        } catch (IOException ioException) {
            logger.error("Cannot load json..." + ioException.getMessage(), ioException);

        } catch (Exception e) {
            logger.error("Error loading json" + e.getMessage(), e);

        }

    }

    /**
     * Read in the cognito props file and get/set the client id, secret, and required urls
     * for authenticating a user.
     */
    // TODO This code appears in a couple classes, consider using a startup servlet similar to adv java project
    private void loadProperties() {

        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");
            properties = loadProperties("/database.properties");
            JDBC_URL = properties.getProperty("url");
            DB_USERNAME = properties.getProperty("username");
            DB_PASSWORD = properties.getProperty("password");

        } catch (IOException ioException) {
            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);

        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);

        }

    }

    /**
     * Fetch data from AWS RDS
     *
     * @param userSub   Cognito Sub
     * @param userEmail User Email
     * @param username  Username
     * @param role      role
     */
    private void insertDataIntoRDS(String userSub, String userEmail, String username, String role, HttpServletRequest req) {

        try {

            // Create new user in AWS RDS from cognito sub
            User newUser = new User();
            newUser.setCognitoSub(userSub);
            newUser.setUsername(username);
            newUser.setEmail(userEmail);
            newUser.setLastLogin(new Timestamp(System.currentTimeMillis()));
            newUser.setRole(role);

            // Create user session to hold cognito sub
            createUserSession(req, newUser);

            GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
            userDao.insert(newUser);

            logger.info("User inserted: " + newUser);


        } catch (Exception e) {
            logger.error("Error inserting User: " + e.getMessage(), e);
        }
        
    }

    /**
     * Create user session to hold user data
     * @param req http request
     * @param user user object
     */
    public void createUserSession(HttpServletRequest req, User user) {

        HttpSession session = req.getSession(true);
        logger.info("Creating new session for userSub: " + user.getCognitoSub() + ", Session ID: " + session.getId());
        logger.debug("Username in session: " + user.getUsername());

        session.setAttribute("user", user);

    }

}