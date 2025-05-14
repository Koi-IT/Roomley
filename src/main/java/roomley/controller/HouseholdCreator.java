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

        }

        // Get user and specified members
        User user = userDao.getByPropertyEqual("cognito_sub", userSub).get(0);
        String[] users = req.getParameterValues("users");
        
        // Create new household
        Household household = new Household();
        householdDao.insert(household);

        // Set user as household owner and add him to the household
        HouseholdMember ownerMember = new HouseholdMember();
        HouseholdMemberId ownerId = new HouseholdMemberId();
        ownerId.setHouseholdId(household.getHouseholdId());
        ownerId.setUserId(user.getId());

        ownerMember.setId(ownerId);
        ownerMember.setHousehold(household);
        ownerMember.setUser(user);
        ownerMember.setRole("owner");

        memberDao.insert(ownerMember);
        householdMembers.add(ownerMember);

        // Add users to household
        for (String username : users) {
            matchedUsers = userDao.getByPropertyEqual("username", username);

            if (!matchedUsers.isEmpty()) {
                HouseholdMember householdMember = getHouseholdMember(matchedUsers, household);
                memberDao.insert(householdMember);
                householdMembers.add(householdMember);

            } else {
                logger.warn("Userlist is empty.");

            }

        }

        // Set members of household
        household.setHouseholdMembers(householdMembers);

        // Set user invitations
        for (HouseholdMember householdMember : householdMembers) {
            Invitation invitation = new Invitation();

            invitation.setHousehold(household);
            invitation.setInvitedByUserId(user.getId());
            invitation.setInvitationCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            invitation.setInviteStatus("pending");

            invitationDao.insert(invitation);
        }

        resp.sendRedirect("taskGrabber");

    }

    private static HouseholdMember getHouseholdMember(List<User> matchedUsers, Household household) {
        HouseholdMember householdMember = new HouseholdMember();
        User matchedUser = matchedUsers.get(0);

        HouseholdMemberId memberId = new HouseholdMemberId();
        memberId.setHouseholdId(household.getHouseholdId());
        memberId.setUserId(matchedUser.getId());

        householdMember.setId(memberId);
        householdMember.setHousehold(household);
        householdMember.setUser(matchedUser);
        householdMember.setRole("member");
        return householdMember;
    }

}