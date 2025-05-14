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
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    /**
     * No argument constructor for Task
     */
    public Task() {
    }

    /**
     * Constructor to build Task object
     *
     * @param householdMember   the HouseholdMember (representing user_id and household_id)
     * @param taskName          The name of the Task
     * @param taskDescription   The description of the Task
     * @param taskStatus        The status of the Task
     * @param taskDifficulty    The difficulty of the Task
     */
    public Task(HouseholdMember householdMember, String taskName, String taskDescription, boolean taskStatus, int taskDifficulty) {
        this.householdMember = householdMember;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskDifficulty = taskDifficulty;
    }

    // Getter and Setter methods

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public HouseholdMember getHouseholdMember() {
        return this.householdMember;
    }

    public void setHouseholdMember(HouseholdMember householdMember) {
        this.householdMember = householdMember;
    }

    public int getTaskDifficulty() {
        return taskDifficulty;
    }

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

