package roomley.controller;

import roomley.persistence.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/taskGrabber"
)
public class TaskGrabber extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Create taskDao object
        TaskDao taskDao = new TaskDao();

        // Get all tasks
        req.setAttribute("tasks", taskDao.getAllTasks());

        // Send tasks to webpage
        RequestDispatcher dispatcher = req.getRequestDispatcher("tasks.jsp");
        dispatcher.forward(req, resp);
    }

    static {
        System.out.println("TaskGrabber static block loaded.");
    }
}