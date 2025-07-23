package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import roomley.entities.Task;
import roomley.entities.User;
import roomley.persistence.GenericDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A simple servlet to find all tasks in the database.
 *
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/distributeTasks"
)
public class DistributeTasks extends HttpServlet {

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

        // Invalidate Session
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp"); // or your login page
            return;
        }

        // Get Users in current users household
        User user = (User) session.getAttribute("user");
        GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);
        user.distributeTasks(taskDao);

        // Redirect home
        resp.sendRedirect("taskGrabber");

    }

}