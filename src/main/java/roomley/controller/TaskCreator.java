package roomley.controller;

import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import roomley.entities.Task;
import roomley.entities.User;
import roomley.persistence.*;

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

    /**
     * Post request to create a new task and redirect to user homepage
     * @param req http request
     * @param resp http response
     * @throws ServletException Servlet exception
     * @throws IOException Input output exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GenericDao<Task> taskDao = new GenericDao<>(Task.class);
        GenericDao<User> userDao = new GenericDao<>(User.class);
        HttpSession session = req.getSession(false);

        // Get the data from the submitted form
        String taskName = req.getParameter("taskName");
        String taskDescription = req.getParameter("taskDescription");

        // Set userSub
        String userSub = (String) session.getAttribute("userSub");
        User currentUser = userDao.getByPropertyLike("cognito_sub", userSub).get(0);

        // Create task using userSub, taskName, and TaskDescription
        Task newTask = createTask(currentUser, taskName, taskDescription);
        currentUser.getTasks().add(newTask);

        // Insert new task into rds
        taskDao.insert(newTask);

        // Send tasks to webpage
        resp.sendRedirect("taskGrabber");
    }

    /**
     * Create new task
     * @param currentUser the current user
     * @param taskName task name
     * @param taskDescription task description
     * @return the new Task object
     * @throws ServletException Servlet exception
     */
    private static Task createTask(User currentUser, String taskName, String taskDescription) throws ServletException {

        // Create householdMemberDao
        GenericDao<HouseholdMember> householdMemberDao = new GenericDao<>(HouseholdMember.class);

        // Create a new task object
        Task newTask = new Task();

        // Get user as household member
        HouseholdMember currentMember = householdMemberDao.getByPropertyEqual("user_id", currentUser.getId()).get(0);

        // Assign values to the newTask
        newTask.setTaskName(taskName);
        newTask.setTaskDescription(taskDescription);
        newTask.setTaskStatus(false);
        newTask.setHouseholdMember(currentMember);
        return newTask;

    }

}