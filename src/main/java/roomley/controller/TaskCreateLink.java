package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

        } else {
            String username = (String) session.getAttribute("username");

            if (username == null) {
                logger.info("Username is null, redirecting to login.");
                resp.sendRedirect("loginPage.jsp");

            } else {
                logger.info("Session found, redirecting to task creation page.");
                resp.sendRedirect("taskCreation.jsp");

            }

        }

    }

}