package Roomley.controller;

import Roomley.persistence.TaskDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple servlet to find all tasks in the database.
 * @author Koi-dev
 */
public class TaskGrabber extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Create taskDao object
        TaskDao taskDao = new TaskDao();

        // Get all tasks
        req.setAttribute("tasks", taskDao.getAllTasks());

        RequestDispatcher dispatcher = req.getRequestDispatcher("tasks.jsp");
        dispatcher.forward(req, resp);
    }
}