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
 *
 * @author Koi -dev
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
        User user = (User) session.getAttribute("user");
        String userSub = user.getCognitoSub();

        if (userSub == null || userSub.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "index.jsp");
            return;

        }

        // Get specified members
        String[] users = req.getParameterValues("users[]");
        logger.info("Users received: {}", Arrays.toString(users));

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

        // Add owner to household
        household.setHouseholdMembers(ownerMember.getHousehold().getHouseholdMembers());
        List<String> missingUsers = new ArrayList<>();
        List<String> foundUsers = new ArrayList<>();

        // Add other users to the household as members (besides the owner)
        if (users != null) {
            for (String username : users) {
                try {
                    List<User> userList = userDao.getByPropertyEqual("displayName", username);

                    if (userList.isEmpty()) {
                        missingUsers.add(username);
                        continue;
                    }

                    User invitedUser = userList.get(0);
                    foundUsers.add(invitedUser.getDisplayName()); // Add to found

                    if (invitedUser.getId() != user.getId()) {
                        HouseholdMember member = new HouseholdMember();
                        HouseholdMemberId memberId = new HouseholdMemberId();
                        memberId.setHouseholdId(household.getHouseholdId());
                        memberId.setUserId(invitedUser.getId());

                        member.setId(memberId);
                        member.setHousehold(household);
                        member.setUser(invitedUser);
                        member.setRole(HouseholdMember.HouseholdRole.MEMBER);

                        try {
                            memberDao.insert(member);
                            logger.info("Added user {} to household {}.", invitedUser.getUserId(), household.getHouseholdId());
                            householdMembers.add(member);
                        } catch (Exception e) {
                            logger.error("Error adding user {} to household.", invitedUser.getUserId(), e);
                        }
                    }

                } catch (Exception e) {
                    logger.error("Error processing user: {}", username, e);
                }
            }
        }

        session.setAttribute("missingUsers", missingUsers);
        session.setAttribute("foundUsers", foundUsers);



        // Send user invitations
        for (HouseholdMember householdMember : householdMembers) {

            if (householdMember.getUser().getId() != ownerMember.getUser().getId()) {

                User invitedUser = householdMember.getUser();

                logger.debug("Invited user object: {}", invitedUser);
                logger.debug("Invited user ID: {}", invitedUser.getUserId());
                logger.debug("Household is: {}", household);
                logger.debug("Household ID is: {}", household.getHouseholdId());
                logger.info("Inviting {}", ownerMember.getUser());
                logger.info("Inviting {}", householdMember.getUser());

                Invitation invitation = new Invitation();
                invitation.setHousehold(household);
                invitation.setInvitedByUser(ownerMember.getUser());
                invitation.setInvitedUser(householdMember.getUser());
                invitation.setInvitationCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                invitation.setInviteStatus("pending");
                invitationDao.insert(invitation);

            }

        }

        session.setAttribute("currentHousehold", household);

        // Redirect to householdCreated
        resp.sendRedirect("householdCreated.jsp");

    }

    /**
     * Sets the user as a part of the household
     * @param matchedUser
     * @param household
     * @return
     */
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