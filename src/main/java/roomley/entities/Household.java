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
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "household_id")
    private int householdId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "created_by_user_id")
    private int createdByUserId;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<HouseholdMember> householdMembers;

    /**
     * Gets group name.
     *
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Gets created by user id.
     *
     * @return the created by user id
     */
    public int getCreatedByUserId() {
        return createdByUserId;
    }

    /**
     * Sets created by user id.
     *
     * @param createdByUserId the created by user id
     */
    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

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
     * Gets group name.
     *
     * @return the group name
     */
    public String setGroupName() {
        return groupName;
    }

    /**
     * Sets group name.
     *
     * @param groupName the group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Gets household members.
     *
     * @return the household members
     */
    public List<HouseholdMember> getHouseholdMembers() {
        return householdMembers;
    }

    /**
     * Sets household members.
     *
     * @param householdMembers the household members
     */
    public void setHouseholdMembers(List<HouseholdMember> householdMembers) {
        householdMembers = householdMembers;
    }

    @Override
    public String toString() {
        return "Household{" +
                "householdId=" + householdId +
                ", groupName='" + groupName + '\'' +
                ", HouseholdMembers=" + householdMembers +
                '}';
    }
}
