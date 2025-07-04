package roomley.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roomley.entities.Task;
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
        urlPatterns = "/editTask"
)
public class EditTask extends HttpServlet {

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

        // Get task from task ID
        String taskId = req.getParameter("taskId");
        GenericDao<Task, Integer> genericDao = new GenericDao<>(Task.class);
        Task taskToUpdate = genericDao.getById(Integer.parseInt(taskId));

        // Give the task to update, to the session with task variable
        session.setAttribute("taskToUpdate", taskToUpdate);

        // Forward to the edit task page
        RequestDispatcher dispatcher = req.getRequestDispatcher("editTask.jsp");
        dispatcher.forward(req, resp);

    }

}