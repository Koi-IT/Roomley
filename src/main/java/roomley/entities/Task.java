package roomley.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * The type Task.
 */
@Entity(name = "Task")
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int task_id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "status")
    private boolean taskStatus;

    @Column(name = "task_description")
    private String taskDescription;

    @ManyToOne
    @JoinColumn(name = "Users_user_id")
    private User user;

    /**
     * No argument constructor for Task
     */
    public Task () {

    }

    /**
     * Constructor to build Task object
     *
     * @param taskName        The name of the Task
     * @param taskDescription The description of the Task
     * @param taskStatus      The status of the Task
     */
    public Task (String taskName,  String taskDescription, boolean taskStatus, User user) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.user = user;

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
    public int getId() { return this.task_id; }

    /**
     * Sets task id
     *
     * @param task_id Task id
     */
    public void setId(int task_id) { this.task_id = task_id; }

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

}
