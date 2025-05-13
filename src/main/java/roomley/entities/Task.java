package roomley.entities;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * The type Task.
 */
@Entity(name = "Task")
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "task_id")
    private int taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "status")
    private boolean taskStatus;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_difficulty")
    private int taskDifficulty;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * No argument constructor for Task
     */
    public Task () {

    }

    /**
     * Constructor to build Task object
     *
     * @param user            the user
     * @param taskName        The name of the Task
     * @param taskDescription The description of the Task
     * @param taskStatus      The status of the Task
     * @param taskDifficulty  the task difficulty
     */
    public Task (User user, String taskName,  String taskDescription, boolean taskStatus, int taskDifficulty) {
        this.user = user;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskDifficulty = taskDifficulty;

    }

    /**
     * Gets the task name
     *
     * @return Task name
     */
    public String getTaskName() { return this.taskName; }

    /**
     * Sets the task name
     *
     * @param taskName Task name
     */
    public void setTaskName(String taskName) { this.taskName = taskName; }

    /**
     * Gets task status
     *
     * @return Task status
     */
    public boolean getTaskStatus() { return this.taskStatus; }

    /**
     * Sets tast status
     *
     * @param taskStatus Task status
     */
    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * Gets task description
     *
     * @return Task description
     */
    public String getTaskDescription() { return this.taskDescription; }

    /**
     * Sets task description
     *
     * @param taskDescription Task description
     */
    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    /**
     * Gets task id
     *
     * @return Task id
     */
    public int getTaskId() { return this.taskId; }

    /**
     * Sets task id
     *
     * @param taskId Task id
     */
    public void setTaskId(int taskId) { this.taskId = taskId; }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() { return this.user; }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) { this.user = user; }

    /**
     * Gets difficulty
     *
     * @return the difficulty
     */
    public int getTaskDifficulty() { return taskDifficulty; }

    /**
     * Sets task type.
     *
     * @param taskDifficulty the task type
     */
    public void setTaskDifficulty(int taskDifficulty) { this.taskDifficulty = taskDifficulty; }

    /**
     * Override the equals function to allow for equals between 2 objects to resolve properly
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return taskId == task.taskId;

    }

    /**
     * Return object hashcode
     * @return Object Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

    /**
     * To string for Task
     * @return task string
     */
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskDifficulty=" + taskDifficulty +
                '}';
    }


}
