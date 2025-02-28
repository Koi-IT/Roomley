package Roomley.controller;

import Roomley.persistence.TaskDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple servlet to find all users in the database.
 * @author pwaite
 */

public class TaskGrabber extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Create taskDao object
        TaskDao taskDao = new TaskDao();

        // Get all tasks
        req.setAttribute("users", taskDao.getAllTasks());

        RequestDispatcher dispatcher = req.getRequestDispatcher("results.jsp");
        dispatcher.forward(req, resp);
    }
}