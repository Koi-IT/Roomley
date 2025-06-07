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
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/taskUpdateLink"
)
public class TaskUpdateLink extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Get request to update user task
     * @param req http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException Input output exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get session
        HttpSession session = req.getSession(false);

        // Create DAO objects
        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        GenericDao<HouseholdMember, HouseholdMemberId> memberDao = new GenericDao<>(HouseholdMember.class);
        GenericDao<Household, Integer> householdDao = new GenericDao<>(Household.class);

        // Get task from task ID
        String taskId = req.getParameter("taskId");
        GenericDao<Task, Integer> genericDao = new GenericDao<>(Task.class);
        Task taskToUpdate = genericDao.getById(Integer.parseInt(taskId));

        // Give the task to update, to the session with task variable
        session.setAttribute("taskToUpdate", taskToUpdate);

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
            // TODO: ADD household error
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

        // Forward to the edit task page
        RequestDispatcher dispatcher = req.getRequestDispatcher("editTask.jsp");
        dispatcher.forward(req, resp);

    }

}