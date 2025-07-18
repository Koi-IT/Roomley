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

        // Get Users in current users household
        User user = (User) session.getAttribute("user");
        Set<HouseholdMember> householdMembers = user.getHouseholdMembers();
        int userId = user.getId();

        // Get Tasks in current users household
        GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);
        GenericDao<HouseholdMember, Integer> householdMemberDao = new GenericDao<>(HouseholdMember.class);
        List<HouseholdMember> members = householdMemberDao.getByPropertyEqual("id.userId", userId);
        Household household = (!members.isEmpty()) ? members.get(0).getHousehold() : null;

        List<Task> householdTasks = taskDao.getByPropertyEqual("household", household)
                .stream()
                .filter(task -> task.getUser() == null) // Only unassigned tasks
                .collect(Collectors.toList());

        // Prepare a Map to keep track of members and their assigned tasks + difficulty sums
        Map<HouseholdMember, List<Task>> assignments = new HashMap<>();
        Map<HouseholdMember, Integer> difficultyTotals = new HashMap<>();

        // Initialize for each member
        for (HouseholdMember member : householdMembers) {
            assignments.put(member, new ArrayList<>());
            difficultyTotals.put(member, 0);
        }

        // Sort tasks by difficulty descending (you can do this with a Comparator)
        householdTasks.sort((t1, t2) -> Integer.compare(t2.getTaskDifficulty(), t1.getTaskDifficulty()));

        // Now assign each task to the member with the lowest difficulty total
        for (Task task : householdTasks) {
            HouseholdMember bestMember = difficultyTotals.entrySet()
                    .stream()
                    .min(Map.Entry.comparingByValue())
                    .get()
                    .getKey();

            assignments.get(bestMember).add(task);
            difficultyTotals.put(bestMember, difficultyTotals.get(bestMember) + task.getTaskDifficulty());

            // Also update the task's assigned user in DB
            task.setUser(bestMember.getUser());
            taskDao.update(task);
        }

        // Redirect home
        resp.sendRedirect("taskGrabber");

    }

}