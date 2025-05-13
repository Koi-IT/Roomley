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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Grab session
        HttpSession session = req.getSession(false);

        // Verify user sub
        String userSub = (String) session.getAttribute("userSub");

        if (userSub == null || userSub.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "index.jsp");

        }

        // Create household
//        Household household = new Household;

    }

}