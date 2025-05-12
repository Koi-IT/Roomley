package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Task;

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
        urlPatterns = "/updateTask"
)
public class UpdateTask extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Post request to update tasks
     * @param req http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException Input output exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Grab session
        HttpSession session = req.getSession(false);

        // Grab request parameters and instantiate DAO
        String action = req.getParameter("action");
        String taskName = req.getParameter("taskName");
        String taskDescription = req.getParameter("taskDescription");
        String taskId =  req.getParameter("taskId");
        TaskDao taskDao = new TaskDao();

        // Verify user sub
        String userSub = (String) session.getAttribute("userSub");

        if (userSub == null || userSub.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "index.jsp");

        }


        // Verify taskId
        if (taskId == null || taskId.isEmpty()) {
            logger.error("Missing taskId parameter");

            // TODO create errorPage.jsp

            resp.sendRedirect("errorPage.jsp");
            return;

        }

        // Start update based on action
        if ("update".equals(action)) {
            // Update task name/description
            Task task = taskDao.getById(Integer.parseInt(taskId));
            task.setTaskName(taskName);
            task.setTaskDescription(taskDescription);
            taskDao.update(task);

        } else if ("delete".equals(action)) {
            // Delete task using id
            taskDao.delete(taskDao.getById(Integer.parseInt(taskId)));

        } else {
            // Update task status
            Task task = taskDao.getById(Integer.parseInt(taskId));
            task.setTaskStatus(!task.getTaskStatus());
            taskDao.update(task);
            logger.info("Task Status: " + task.getTaskStatus());

        }

        // After update or delete operation, refresh the session
        session.setAttribute("tasks", taskDao.getAllTasks());

        resp.sendRedirect(req.getContextPath() + "/taskGrabber");

    }

}