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
    @GenericGenerator(name = "native", strategy = "native")
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
    @JoinColumns({
            @JoinColumn(name = "household_id", referencedColumnName = "household_id", insertable = false, updatable = false),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    })
    private HouseholdMember householdMember;

    @ManyToOne
    @JoinColumn(name = "household_id",referencedColumnName = "household_id")
    private Household household;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    /**
     * Gets household.
     *
     * @return the household
     */
    public Household getHousehold() {
        return household;
    }

    /**
     * Sets household.
     *
     * @param household the household
     */
    public void setHousehold(Household household) {
        this.household = household;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Instantiates a new Task.
     */
    public Task() {
    }

    /**
     * Instantiates a new Task.
     *
     * @param householdMember the household member
     * @param taskName        the task name
     * @param taskDescription the task description
     * @param taskStatus      the task status
     * @param taskDifficulty  the task difficulty
     */
    public Task(HouseholdMember householdMember, String taskName, String taskDescription, boolean taskStatus, int taskDifficulty) {
//        this.householdMember = householdMember;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskDifficulty = taskDifficulty;

    }

    // Getter and Setter methods

    /**
     * Gets task name.
     *
     * @return the task name
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Sets task name.
     *
     * @param taskName the task name
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Gets task status.
     *
     * @return the task status
     */
    public boolean getTaskStatus() {
        return this.taskStatus;
    }

    /**
     * Sets task status.
     *
     * @param taskStatus the task status
     */
    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * Gets task description.
     *
     * @return the task description
     */
    public String getTaskDescription() {
        return this.taskDescription;
    }

    /**
     * Sets task description.
     *
     * @param taskDescription the task description
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Gets task id.
     *
     * @return the task id
     */
    public int getTaskId() {
        return this.taskId;
    }

    /**
     * Sets task id.
     *
     * @param taskId the task id
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    /**
     * Gets task difficulty.
     *
     * @return the task difficulty
     */
    public int getTaskDifficulty() {
        return taskDifficulty;
    }

    /**
     * Sets task difficulty.
     *
     * @param taskDifficulty the task difficulty
     */
    public void setTaskDifficulty(int taskDifficulty) {
        this.taskDifficulty = taskDifficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return taskId == task.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

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

