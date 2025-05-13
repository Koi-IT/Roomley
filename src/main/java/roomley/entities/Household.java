package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * The type Household.
 */
@Entity(name = "Household")
@Table(name = "households")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "household_id")
    private int householdId;

    @Column(name = "group_name")
    private String taskName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;


    /**
     * Gets household id.
     *
     * @return the household id
     */
    public int getHouseholdId() {
        return householdId;
    }

    /**
     * Sets household id.
     *
     * @param householdId the household id
     */
    public void setHouseholdId(int householdId) {
        this.householdId = householdId;
    }

    /**
     * Gets task name.
     *
     * @return the task name
     */
    public String getTaskName() {
        return taskName;
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
     * Gets created by user.
     *
     * @return the created by user
     */
    public User getCreatedByUser() {
        return createdByUser;
    }

    /**
     * Sets created by user.
     *
     * @param createdByUser the created by user
     */
    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    @Override
    public String toString() {
        return "Household{" +
                "householdId=" + householdId +
                ", taskName='" + taskName + '\'' +
                ", createdByUser=" + createdByUser +
                '}';
    }
}
