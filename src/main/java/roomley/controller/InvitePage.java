package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Invitation;
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

/**
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/invitePage"
)
public class InvitePage extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get request to remove session and redirect to home page
     * @param req http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException Input output exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check session
        HttpSession session = req.getSession(false);

        // Get user
        User user = (User) session.getAttribute("user");

        // Get invitations
        GenericDao<Invitation, Integer> invitationDao = new GenericDao<>(Invitation.class);
        List<Invitation> invitations = invitationDao.getByPropertyEqual("invitedUser", user);

        logger.info("Invitation list: " + invitations);
        // Send invitations

        req.setAttribute("invitations", invitations);

        RequestDispatcher dispatcher = req.getRequestDispatcher("invites.jsp");
        dispatcher.forward(req, resp);
        return;

    }

}