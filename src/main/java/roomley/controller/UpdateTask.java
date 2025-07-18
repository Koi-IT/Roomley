package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.HouseholdMember;
import roomley.entities.Task;
import roomley.entities.User;
import roomley.persistence.GenericDao;

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
        urlPatterns = "/updateTask"
)
public class UpdateTask extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Post request to update tasks
     * @param req http request
     * @param resp http response
     * @throws IOException Input output exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Grab session
        HttpSession session = req.getSession(false);

        // Grab request parameters and instantiate DAO
        String action = req.getParameter("action");
        String taskName = req.getParameter("taskName");
        String taskDescription = req.getParameter("taskDescription");
        String taskId =  req.getParameter("taskId");
        GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);
        GenericDao<HouseholdMember, Integer> memberDao = new GenericDao<>(HouseholdMember.class);
        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        Task task = taskDao.getById(Integer.parseInt(taskId));

        // Verify user sub
        User user = (User) session.getAttribute("user");
        String userSub = user.getCognitoSub();

        if (userSub == null || userSub.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "index.jsp");

        }

        // Get current User/Member
        User currentUser = userDao.getByPropertyEqual("cognitoSub", userSub).get(0);
        HouseholdMember currentMember = memberDao.getByPropertyEqual("user", currentUser.getId()).get(0);

        // Verify taskId
        if (taskId.isEmpty()) {
            logger.error("Missing taskId parameter");

            // TODO create errorPage.jsp

            resp.sendRedirect("errorPage.jsp");
            return;

        }

        // Start update based on action
        if ("update".equals(action)) {
            // Update task name/description
            task.setTaskName(taskName);
            task.setTaskDescription(taskDescription);
            task.setUser(currentUser);
            task.setHousehold(currentMember.getHousehold());
            task.setTaskDifficulty(task.getTaskDifficulty());

            String difficultyStr = req.getParameter("taskDifficulty");
            if (difficultyStr != null && !difficultyStr.isEmpty()) {
                int difficulty = Integer.parseInt(difficultyStr);
                task.setTaskDifficulty(difficulty);
            }

            logger.debug("Updated task: {}", task);
            taskDao.update(task);

        } else {
            // Update task status
            task.setTaskStatus(!task.getTaskStatus());
            task.setUser(currentUser);
            taskDao.update(task);
            logger.info("Task Status: " + task.getTaskStatus());

        }

        // After update or delete operation, refresh the session
        session.setAttribute("tasks", taskDao.getAll());

        resp.sendRedirect(req.getContextPath() + "/taskGrabber");

    }

}