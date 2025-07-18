package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Task;
import roomley.persistence.GenericDao;

import javax.servlet.ServletException;
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
        urlPatterns = "/deleteTaskLink"
)
public class DeleteTaskLink extends HttpServlet {

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

        HttpSession session = req.getSession(false);

        String taskId = req.getParameter("taskId");
        GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);
        Task taskToDelete = taskDao.getById(Integer.parseInt(taskId));

        taskDao.delete(taskToDelete);

        // Redirect home
        resp.sendRedirect("taskGrabber");
    }

}