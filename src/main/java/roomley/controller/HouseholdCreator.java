package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.*;
import roomley.persistence.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A servlet to create a household and send out invites.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/householdCreator"
)
public class HouseholdCreator extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get request to get to creating a household page
     * @param req http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException Input output exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        GenericDao<Household, Integer> householdDao = new GenericDao<>(Household.class);
        GenericDao<HouseholdMember, HouseholdMemberId> memberDao = new GenericDao<>(HouseholdMember.class);
        GenericDao<Invitation, Integer> invitationDao = new GenericDao<>(Invitation.class);
        List<HouseholdMember> householdMembers = new ArrayList<>();

        // Grab session
        HttpSession session = req.getSession(false);

        // Verify user sub
        String userSub = (String) session.getAttribute("userSub");

        if (userSub == null || userSub.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "index.jsp");
            return;  // Exit if session is invalid
        }

        // Get user and specified members
        User user = userDao.getByPropertyEqual("cognitoSub", userSub).get(0);
        String[] users = req.getParameterValues("users[]");
        logger.info("Users received: {}", Arrays.toString(users));

        // Check if the 'users' parameter is null or empty
        if (users == null || users.length == 0) {
            logger.error("No users were provided in the form submission.");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No users were added.");
            return;
        }

        // Validate householdName
        if (req.getParameter("householdName") == null || req.getParameter("householdName").isEmpty()) {
            req.setAttribute("errorMessage", "Household name is required!");
            RequestDispatcher dispatcher = req.getRequestDispatcher("userHomePage.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // Check if a household with the same name already exists
        List<Household> existingHouseholds = householdDao.getByPropertyEqual("groupName", req.getParameter("householdName"));
        if (!existingHouseholds.isEmpty()) {
            // If a household with the same name exists, show an error message
            req.setAttribute("errorMessage", "A household with this name already exists. Please choose a different name.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("userHomePage.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // Create new household
        Household household = new Household();
        household.setGroupName(req.getParameter("householdName"));
        household.setCreatedByUserId(user.getId());

        // insert household
        try {
            householdDao.insert(household);

            if (household.getHouseholdId() == 0) {
                logger.error("Failed to insert household, ID is still 0.");
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating household.");
                return;
            }

        } catch (Exception e) {
            logger.error("Exception during household insertion.", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create household.");
            return;
        }

        household = householdDao.getById(household.getHouseholdId());
        if (household == null) {
            logger.error("Failed to fetch the household after insertion.");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch household after creation.");
            return;
        }

        if (household.getHouseholdId() == 0) {
            logger.error("Inserted household, but got ID = 0. Aborting...");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create household.");
            return;
        }

        logger.info("Inserted household with ID: {}", household.getHouseholdId());

        // Set user as household owner and add him to the household
        HouseholdMember ownerMember = new HouseholdMember();
        HouseholdMemberId ownerId = new HouseholdMemberId();
        ownerId.setHouseholdId(household.getHouseholdId());
        ownerId.setUserId(user.getId());

        ownerMember.setId(ownerId);
        ownerMember.setHousehold(household);
        ownerMember.setUser(user);
        ownerMember.setRole(HouseholdMember.HouseholdRole.OWNER);

        try {
            memberDao.insert(ownerMember);
            logger.info("Creating member for household ID: {}", household.getHouseholdId());

        } catch (Exception e) {
            logger.error("Failed to insert household member for user: " + ownerMember.getUser(), e);

        }

        householdMembers.add(ownerMember);

        // Add users to household (newly added)
        loopUsers(userDao, memberDao, householdMembers, users, household);

        // After looping, set the members in the household
        household.setHouseholdMembers(householdMembers);

        // Set user invitations
        for (HouseholdMember householdMember : householdMembers) {

            if (householdMember.getUser().getId() == user.getId()) {
            Invitation invitation = new Invitation();
//            invitation.setHousehold(household);
//            invitation.setInvitedByUserId(user);
            invitation.setInvitationCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            invitation.setInviteStatus("pending");
            invitationDao.insert(invitation);
            }

        }

        // Redirect to taskGrabber
        resp.sendRedirect("taskGrabber");

    }


    private void loopUsers(GenericDao<User, Integer> userDao, GenericDao<HouseholdMember, HouseholdMemberId> memberDao, List<HouseholdMember> householdMembers, String[] users, Household household) {
        if (users == null || users.length == 0) {
            logger.warn("No users to process.");
            return;
        }

        List<User> matchedUsers;
        List<String> failedInserts = new ArrayList<>();
        for (String username : users) {
            logger.debug("Attempting to match user with username: {}", username);

            matchedUsers = userDao.getByPropertyEqual("username", username);
            if (matchedUsers.isEmpty()) {
                logger.warn("User with username '{}' not found.", username);
                continue; // Skip to next user
            }

            User matchedUser = matchedUsers.get(0);
            HouseholdMember householdMember = setHouseholdMember(matchedUser, household);

            try {
                logger.info("Inserting HouseholdMember for householdId={} and userId={}", householdMember.getId().getHouseholdId(), householdMember.getId().getUserId());

                // Check if user is already a member
                List<HouseholdMember> existingMembers = memberDao.getByPropertyEqual("userId", matchedUser.getId());
                if (!existingMembers.isEmpty()) {
                    logger.warn("User '{}' is already a member of the household.", matchedUser.getUsername());
                    continue; // Skip if already a member

                }

                memberDao.insert(householdMember);
                logger.info("Successfully added user '{}' to household.", matchedUser.getUsername());
                householdMembers.add(householdMember);  // Add to the list

            } catch (Exception e) {
                failedInserts.add(matchedUser.getUsername());
                logger.error("Failed to insert household member for user: " + matchedUser.getUsername(), e);
            }
        }

        if (!failedInserts.isEmpty()) {
            logger.error("Failed to add the following users to the household: {}", String.join(", ", failedInserts));
        }

    }



    private HouseholdMember setHouseholdMember(User matchedUser, Household household) {
        HouseholdMemberId memberId = new HouseholdMemberId();

        logger.debug("Creating HouseholdMember for Household ID: " + household.getHouseholdId());

        memberId.setHouseholdId(household.getHouseholdId());
        memberId.setUserId(matchedUser.getId());

        HouseholdMember householdMember = new HouseholdMember();

        householdMember.setId(memberId);
        householdMember.setHousehold(household);
        householdMember.setUser(matchedUser);
        householdMember.setRole(HouseholdMember.HouseholdRole.MEMBER);

        return householdMember;
    }

}