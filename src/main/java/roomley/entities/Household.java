package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Household.
 */
@Entity(name = "Household")
@Table(name = "households")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "household_id")
    private int householdId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "created_by_user")
    private int createdByUserId;

    @OneToMany(mappedBy = "household", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<HouseholdMember> householdMembers = new ArrayList<>();

    /**
     * Instantiates a new Household.
     */
    public Household() {

    }

    /**
     * Instantiates a new Household.
     *
     * @param householdId      the household id
     * @param groupName        the group name
     * @param createdByUserId  the created by user id
     * @param householdMembers the household members
     */
    public Household(int householdId, String groupName, int createdByUserId, List<HouseholdMember> householdMembers) {
        this.householdId = householdId;
        this.groupName = groupName;
        this.createdByUserId = createdByUserId;
        this.householdMembers = householdMembers;
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
    public String getGroupName() {
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
     * @param householdMemberList the household members
     */
    public void setHouseholdMembers(List<HouseholdMember> householdMemberList) {
        householdMembers = householdMemberList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return householdId == household.householdId && createdByUserId == household.createdByUserId && Objects.equals(groupName, household.groupName) && Objects.equals(householdMembers, household.householdMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(householdId, groupName, createdByUserId, householdMembers);
    }

    @Override
    public String toString() {
        return "Household{" +
                "householdId=" + householdId +
                ", groupName='" + groupName + '\'' +
                '}';

    }

}
