package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The type Household member.
 */
@Entity(name = "Household_Member")
@Table(name = "household_members")
public class Household_Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "household_id")
    private int householdId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role")
    private String role;

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
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Household_Member{" +
                "householdId=" + householdId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }
}
