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

/**
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/taskCreateLink"
)
public class TaskCreateLink extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get request to remove session and redirect to home page
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
            resp.sendRedirect("loginPage.jsp");
            return;
        }

        //Create userDao
        GenericDao<User> userDao = new GenericDao<>(User.class);
        GenericDao<HouseholdMember> memberDao = new GenericDao<>(HouseholdMember.class);
        GenericDao<Household> houseDao = new GenericDao<>(Household.class);

        // Get user household
        String userSub = (String) session.getAttribute("userSub");
        User user = userDao.getByPropertyEqual("cognito_sub", userSub).get(0);
        int userId = user.getId();
        List<HouseholdMember> currentMember = memberDao.getByPropertyEqual("id.userId", userId);
        if (currentMember.isEmpty()) {
            logger.warn("No HouseholdMember found for userId: " + userId);
            // You might want to redirect, show a message, or handle it gracefully
            resp.sendRedirect("userHomePage.jsp");
            return;
        }
        HouseholdMember currentMemberHousehold = currentMember.get(0);

        Household currentHousehold = houseDao.getByPropertyEqual("householdId", currentMemberHousehold.getHousehold().getHouseholdId()).get(0);

        // Get users in household
        List<User> usersInHousehold = new ArrayList<>();
        for (HouseholdMember member : currentHousehold.getHouseholdMembers()) {
            usersInHousehold.add(member.getUser());
        }

        if (usersInHousehold.isEmpty()) {
            logger.info("No household found, redirecting to login.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("userHomePage.jsp");
            dispatcher.forward(req, resp);
        }

        // Set attribute and forward
        req.setAttribute("householdUsers", usersInHousehold);
        RequestDispatcher dispatcher = req.getRequestDispatcher("taskCreation.jsp");
        dispatcher.forward(req, resp);

    }

}