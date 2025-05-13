package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Task;
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

        // Get session
        HttpSession session = req.getSession(false);

        // Redirect to log in if session is invalid
        if (session == null || session.getAttribute("userSub") == null) {
            resp.sendRedirect("loginPage.jsp");
            return;
        }

        // Create Dao's
        GenericDao<User> userDao = new GenericDao<>(User.class);
        GenericDao<Task> taskDao = new GenericDao<>(Task.class);


        // Fetch user data
        String userSub = (String) session.getAttribute("userSub");
        String userName = (String) session.getAttribute("username");
        String userEmail = (String) session.getAttribute("userEmail");
        String role = (String) session.getAttribute("role");
        String userId = (String) session.getAttribute("userId");

        User user = (User) userDao.getByPropertyEqual("cognito_sub", userSub);

//        List<Task> householdTasks = taskDao.getByPropertyEqual("user", user);
        List<Task> userTasks = user.getTasks();
        List<Task> userCompletedTasks = user.getTasks().stream()
                .filter(Task::getTaskStatus)
                .collect(Collectors.toList());

        // Log user details for debugging
        logger.debug("User Sub: " + userSub);
        logger.debug("User Name: " + userName);
        logger.debug("User Email: " + userEmail);
        logger.debug("Role: " + role);
        logger.debug("ID: " + userId);

        // Set session attributes based on user
        try {
            // TODO get all tasks within household
//            session.setAttribute("tasks", householdTasks);
            session.setAttribute("userAssignedTasks", userTasks);
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
