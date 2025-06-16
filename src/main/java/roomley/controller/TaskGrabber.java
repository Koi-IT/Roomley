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
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple servlet to find all tasks in a household.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/taskGrabber"
)
public class TaskGrabber extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get request to grab all tasks within the users household
     * @param req http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException Input output exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check if session exists
        HttpSession session = req.getSession(false);

        // Redirect to log in if session is invalid
        if (session == null || session.getAttribute("userSub") == null) {
            resp.sendRedirect("/logout");
            logger.warn("UserSub is null!");
            return;
        }

        // Create Dao's
        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);
        GenericDao<HouseholdMember, HouseholdMemberId> householdMemberDao = new GenericDao<>(HouseholdMember.class);
        User user;


        // Fetch user data
        String userSub = (String) session.getAttribute("userSub");
        List<User> users = userDao.getByPropertyEqual("cognitoSub", userSub);

        if (users.isEmpty()) {
            logger.error("No user found with cognitoSub: " + userSub);
            // Handle the case where the user doesn't exist (e.g., redirect to login or show an error)
            resp.sendRedirect("index.jsp");
            return;
        } else {
            user = users.get(0);
            // Proceed with user data
        }

        String userEmail = user.getEmail();
        String role = user.getRole();
        String username = user.getUsername();
        int userId = user.getId();

        Household household = (Household) session.getAttribute("currentHousehold");

        if (household == null) {
            logger.warn("No household found for userSub: " + userSub);
            session.setAttribute("errorMessage", "No household found for your account.");
            resp.sendRedirect(req.getContextPath() + "/householdCreation.jsp");
            return;
        }

        List<Task> householdTasks = taskDao.getByPropertyEqual("household", household);

        List<Task> userTasks = householdTasks.stream()
                .filter(task -> task.getUser() != null && task.getUser().equals(user))
                .collect(Collectors.toList());

        List<Task> unassignedTasks = householdTasks.stream()
                .filter(task -> task.getUser() == null)
                .collect(Collectors.toList());


        List<Task> userCompletedTasks = userTasks.stream()
                .filter(Task::getTaskStatus)
                .collect(Collectors.toList());

        // Log user details for debugging
        logger.debug("User Sub: " + userSub);
        logger.debug("User Name: " + username);
        logger.debug("User Email: " + userEmail);
        logger.debug("Role: " + role);
        logger.debug("ID: " + userId);

        // Set session attributes based on user
        try {
            // TODO get all tasks within household
            logger.debug("tasks: " + unassignedTasks);
            session.setAttribute("tasks", unassignedTasks);
            logger.debug("userTasks: " + userTasks);
            session.setAttribute("userAssignedTasks", userTasks);
            logger.debug("userCompletedTasks: " + userCompletedTasks);
            session.setAttribute("completedTasks", userCompletedTasks);

        } catch (Exception e) {
            logger.error("Error fetching tasks", e);
            req.setAttribute("errorMessage", "Error fetching tasks.");

            // TODO create error page

            RequestDispatcher dispatcher = req.getRequestDispatcher("errorPage.jsp");
            dispatcher.forward(req, resp);
            return;

        }

        // Forward to the user home page
        RequestDispatcher dispatcher = req.getRequestDispatcher("userHomePage.jsp");
        dispatcher.forward(req, resp);

    }

}
