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
    @Column(name = "task_id")
    private int taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "status")
    private boolean taskStatus;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_type")
    private int taskType;

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
     * @param user            the user
     * @param taskName        The name of the Task
     * @param taskDescription The description of the Task
     * @param taskStatus      The status of the Task
     * @param taskType        the task type
     */
    public Task (User user,String taskName,  String taskDescription, boolean taskStatus, int taskType) {
        this.user = user;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskType = taskType;

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
     * Gets task type.
     *
     * @return the task type
     */
    public int getTaskType() { return taskType; }

    /**
     * Sets task type.
     *
     * @param taskType the task type
     */
    public void setTaskType(int taskType) { this.taskType = taskType; }

}
