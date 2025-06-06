package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import roomley.entities.HouseholdMemberId;
import roomley.entities.User;
import roomley.persistence.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
@WebServlet(urlPatterns = "/taskCreateLink")
public class TaskCreateLink extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handle the GET request to remove session and redirect to home page
     *
     * @param req  http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException      Input output exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check session
        HttpSession session = req.getSession(false);
        if (session == null) {
            logger.info("No session found, redirecting to login.");
            resp.sendRedirect("logout.jsp");
            return;
        }

        // Create DAO objects
        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        GenericDao<HouseholdMember, HouseholdMemberId> memberDao = new GenericDao<>(HouseholdMember.class);
        GenericDao<Household, Integer> householdDao = new GenericDao<>(Household.class);

        // Get user and household info
        String userSub = (String) session.getAttribute("userSub");
        User user = userDao.getByPropertyEqual("cognitoSub", userSub).get(0);
        int userId = user.getId();
        List<HouseholdMember> currentMember = memberDao.getByPropertyEqual("id.userId", userId);

        if (currentMember.isEmpty()) {
            logger.warn("No HouseholdMember found for userId: " + userId);
            resp.sendRedirect("userHomePage.jsp");
            return;
        }

        HouseholdMember currentMemberHousehold = currentMember.get(0);
        Household currentHousehold = householdDao.getByPropertyEqual("householdId", currentMemberHousehold.getHousehold().getHouseholdId()).get(0);

        // Fetch the household with its members
        Household houseWithMembers = ((GenericDao<Household, Integer>) householdDao)
                .getHouseholdWithMembers(currentHousehold.getHouseholdId());

        if (houseWithMembers == null || houseWithMembers.getHouseholdMembers().isEmpty()) {
            logger.warn("No members found in the household for householdId: " + currentHousehold.getHouseholdId());
            resp.sendRedirect("userHomePage.jsp");
            return;
        }

        // Collect users from household members
        List<User> usersInHousehold = houseWithMembers.getHouseholdMembers()
                .stream()
                .map(HouseholdMember::getUser)
                .collect(Collectors.toList());

        // Set attribute and forward to task creation page
        req.setAttribute("householdUsers", usersInHousehold);
        RequestDispatcher dispatcher = req.getRequestDispatcher("taskCreation.jsp");
        dispatcher.forward(req, resp);
    }
}
