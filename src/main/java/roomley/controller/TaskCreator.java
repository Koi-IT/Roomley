package roomley.controller;

import roomley.entities.Task;
import roomley.entities.User;
import roomley.persistence.UserDao;
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
 * A simple servlet create a new task.
 * @author Koi-dev
 */
@WebServlet(
        urlPatterns = "/taskCreator"
)
public class TaskCreator extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get the data from the submitted form
        String taskName = req.getParameter("taskName");
        String taskDescription = req.getParameter("taskDescription");

        // Get Cognito sub from user session
        HttpSession session = req.getSession(false);

        // Set userSub
        String userSub = (String) session.getAttribute("userSub");

        // Create task using userSub, taskName, and TaskDescription
        Task newTask = createTask(userSub, taskName, taskDescription);

        // Insert new task into rds
        TaskDao taskDao = new TaskDao();
        taskDao.insert(newTask);

        // Send tasks to webpage
        resp.sendRedirect("taskGrabber");
    }

    private static Task createTask(String userSub, String taskName, String taskDescription) throws ServletException {

        // Get currentUser
        UserDao userDao = new UserDao();
        List<User> matches = userDao.getByPropertyEqual("cognito_sub", userSub);
        if (matches.isEmpty()) {
            throw new ServletException("Logged‑in user not found in RDS!");
        }
        User currentUser = matches.get(0);

        // Create a new task object
        Task newTask = new Task();

        // Assign values to the newTask
        newTask.setTaskName(taskName);
        newTask.setTaskDescription(taskDescription);
        newTask.setTaskStatus(false);
        newTask.setUser(currentUser);
        return newTask;
    }

}