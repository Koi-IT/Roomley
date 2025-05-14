package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Household member.
 */
@Entity(name = "HouseholdMember")
@Table(name = "household_members")
public class HouseholdMember implements Serializable {

    @EmbeddedId
    private HouseholdMemberId id;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @MapsId("householdId") // Maps the householdId from HouseholdMemberId
    @JoinColumn(name = "household_id", insertable = false, updatable = false)
    private Household household;

    @ManyToOne
    @MapsId("userId") // Maps the userId from HouseholdMemberId
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // Ensure this is correct
    private User user;

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
     * Gets id.
     *
     * @return the id
     */
    public HouseholdMemberId getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(HouseholdMemberId id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "HouseholdMember{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", household=" + household +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdMember that = (HouseholdMember) o;
        return Objects.equals(id, that.id) && Objects.equals(role, that.role) && Objects.equals(household, that.household) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, household, user);
    }
}
