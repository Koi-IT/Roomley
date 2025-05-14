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
        GenericDao<User> userDao = new GenericDao<>(User.class);
        GenericDao<Household> householdDao = new GenericDao<>(Household.class);
        GenericDao<HouseholdMember> memberDao = new GenericDao<>(HouseholdMember.class);
        GenericDao<Invitation> invitationDao = new GenericDao<>(Invitation.class);
        List<User> matchedUsers;
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
        User user = userDao.getByPropertyEqual("cognito_sub", userSub).get(0);
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
        householdDao.insert(household);

        // Set user as household owner and add him to the household
        HouseholdMember ownerMember = new HouseholdMember();
        HouseholdMemberId ownerId = new HouseholdMemberId();
        ownerId.setHouseholdId(household.getHouseholdId());
        ownerId.setUserId(user.getId());

        ownerMember.setId(ownerId);
        ownerMember.setHousehold(household);
        ownerMember.setUser(user);
        ownerMember.setRole(HouseholdMember.HouseholdRole.owner);

        try {
            memberDao.insert(ownerMember);
        } catch (Exception e) {
            logger.error("Failed to insert household member for user: " + ownerMember.getUser(), e);
        }
        householdMembers.add(ownerMember);

        // Add users to household
        loopUsers(userDao, memberDao, householdMembers, users, household);

        // Set members of household
        household.setHouseholdMembers(householdMembers);

        // Set user invitations
        for (HouseholdMember householdMember : householdMembers) {
            Invitation invitation = new Invitation();

            invitation.setHousehold(household);
            invitation.setInvitedByUserId(user);
            invitation.setInvitationCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            invitation.setInviteStatus("pending");

            invitationDao.insert(invitation);
        }

        resp.sendRedirect("taskGrabber");

    }

    private void loopUsers(GenericDao<User> userDao, GenericDao<HouseholdMember> memberDao, List<HouseholdMember> householdMembers, String[] users, Household household) {
        List<User> matchedUsers;
        for (String username : users) {
            matchedUsers = userDao.getByPropertyEqual("username", username);

            if (!matchedUsers.isEmpty()) {
                User matchedUser = matchedUsers.get(0);  // Get the matched user
                HouseholdMember householdMember = getHouseholdMember(matchedUser, household);
                try {
                    memberDao.insert(householdMember);
                    logger.info("Processing user: {}", username);
                } catch (Exception e) {
                    logger.error("Failed to insert household member for user: " + matchedUser.getUsername(), e);
                }  // Insert the household member
                householdMembers.add(householdMember);  // Add to the list of household members
            } else {
                logger.warn("User with username '{}' not found.", username);
            }
        }

    }


    private static HouseholdMember getHouseholdMember(User matchedUser, Household household) {
        HouseholdMember householdMember = new HouseholdMember();
        HouseholdMemberId memberId = new HouseholdMemberId();
        memberId.setHouseholdId(household.getHouseholdId());
        memberId.setUserId(matchedUser.getId());

        householdMember.setId(memberId);
        householdMember.setHousehold(household);
        householdMember.setUser(matchedUser);
        householdMember.setRole(HouseholdMember.HouseholdRole.member);

        return householdMember;
    }

}