package roomley.controller;

import roomley.entities.*;
import roomley.persistence.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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

        GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);
        GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
        HttpSession session = req.getSession(false);
        Household currentHousehold = null;

        // Grab the current household
        User user = (User) session.getAttribute("user");
        if (user != null && user.getHouseholdMembers() != null && !user.getHouseholdMembers().isEmpty()) {
            HouseholdMember firstMember = user.getHouseholdMembers().iterator().next();
            currentHousehold = firstMember.getHousehold();

            if (currentHousehold == null) {
                throw new ServletException("Current household not set in session.");
            }

        }



        // Get the data from the submitted form
        String taskName = req.getParameter("taskName");
        String taskDescription = req.getParameter("taskDescription");
        String taskDifficultyString = req.getParameter("taskDifficulty");
        int taskDifficulty = taskDifficultyString == null ? 0 : Integer.parseInt(taskDifficultyString);

        // Set userSub
        User currentUser = (User) session.getAttribute("user");

        // Create task using userSub, taskName, and TaskDescription
        Task newTask = createTask(currentUser, currentHousehold, taskName, taskDescription,taskDifficulty);

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
    private static Task createTask(User currentUser, Household currentHousehold, String taskName, String taskDescription, int taskDifficulty) throws ServletException {

        // Create householdMemberDao
        GenericDao<HouseholdMember, HouseholdMemberId> householdMemberDao = new GenericDao<>(HouseholdMember.class);

        // Create a new task object
        Task newTask = new Task();

        // Get user as household member
        List<HouseholdMember> householdMembers = householdMemberDao.getByPropertyEqual("user", currentUser);
        if (householdMembers.isEmpty()) {
            throw new ServletException("No HouseholdMember found for the user.");
        }

        HouseholdMember currentMember = householdMembers.get(0);

        // Assign values to the newTask
        newTask.setHousehold(currentHousehold);
        newTask.setTaskName(taskName);
        newTask.setTaskDescription(taskDescription);
        newTask.setTaskStatus(false);
        newTask.setTaskDifficulty(taskDifficulty);
        newTask.setUser(null);
        return newTask;

    }

}