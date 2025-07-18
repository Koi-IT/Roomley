package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Household;
import roomley.persistence.GenericDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A simple servlet to find all tasks in the database.
 *
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/updateHousehold"
)
public class UpdateHousehold extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get request to remove session and redirect to home page
     *
     * @param req  http request
     * @param resp http response
     * @throws IOException      Input output exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("login.jsp"); // or some fallback
            return;
        }

        String householdIdParam = req.getParameter("householdId");

        try {
            int householdId = Integer.parseInt(householdIdParam);

            GenericDao<Household, Integer> householdDao = new GenericDao<>(Household.class);
            Household newHousehold = householdDao.getById(householdId);

            session.setAttribute("currentHousehold", newHousehold);

            if (newHousehold != null) {
                session.setAttribute("currentHousehold", newHousehold);
                logger.info("Updated currentHousehold in session to: " + newHousehold.getGroupName());
            } else {
                logger.warn("Household not found with ID: " + householdId);
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid household ID: " + householdIdParam, e);
        }

        resp.sendRedirect("userProfile.jsp");
    }


}