package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Household member.
 */
@Entity(name = "HouseholdMember")
@Table(name = "household_members")
public class HouseholdMember implements Serializable {

    @EmbeddedId
    private HouseholdMemberId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private HouseholdRole role;

    @ManyToOne
    @MapsId("householdId")
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Instantiates a new Household member.
     */
    public HouseholdMember() {

    }

    /**
     * Instantiates a new Household member.
     *
     * @param id        the id
     * @param role      the role
     * @param household the household
     * @param user      the user
     * @param tasks     the tasks
     */
    public HouseholdMember(HouseholdMemberId id, HouseholdRole role, Household household, User user, List<Task> tasks) {
        this.id = id;
        this.role = role;
        this.household = household;
        this.user = user;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public HouseholdRole getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(HouseholdRole role) {
        this.role = role;
    }

    /**
     * The enum Household role.
     */
    public enum HouseholdRole {
        /**
         * Owner household role.
         */
        OWNER,
        /**
         * Member household role.
         */
        MEMBER
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdMember that = (HouseholdMember) o;
        return Objects.equals(id, that.id) && Objects.equals(role, that.role) && Objects.equals(household, that.household) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, household, user);
    }

    @Override
    public String toString() {
        return "HouseholdMember{" +
                "id=" + id +
                ", role=" + role +
                ", household=" + household +
                ", user=" + user +
                '}';
    }

}
