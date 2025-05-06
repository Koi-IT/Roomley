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

/**
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/taskGrabber"
)
public class TaskGrabber extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userSub") == null) {
            resp.sendRedirect("loginPage.jsp"); // Redirect to login if session is invalid
            return;
        }

        String userSub = (String) session.getAttribute("userSub");
        String userName = (String) session.getAttribute("username");
        String userEmail = (String) session.getAttribute("userEmail");
        String role = (String) session.getAttribute("role");

        // Log user details for debugging
        logger.debug("User Sub: " + userSub);
        logger.debug("User Name: " + userName);
        logger.debug("User Email: " + userEmail);
        logger.debug("Role: " + role);

        // Fetch user and tasks
        UserDao userDao = new UserDao();
        TaskDao taskDao = new TaskDao();
        User user = userDao.getBySub(userSub);

        if (user == null) {
            logger.error("No user found for userSub: " + userSub);
            req.setAttribute("errorMessage", "User not found.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("errorPage.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        int userId = user.getId();
        try {
            session.setAttribute("tasks", taskDao.getAllTasks());
            session.setAttribute("userAssignedTasks", taskDao.getTasksByUser(userId));
            session.setAttribute("completedTasks", taskDao.getCompletedTasksByUser(userId));
        } catch (Exception e) {
            logger.error("Error fetching tasks", e);
            req.setAttribute("errorMessage", "Error fetching tasks.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("errorPage.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // Forward to the user home page
        RequestDispatcher dispatcher = req.getRequestDispatcher("userHomePage.jsp");
        dispatcher.forward(req, resp);
    }

    static {
        System.out.println("TaskGrabber static block loaded.");
    }

}
