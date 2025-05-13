package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The type Household member.
 */
@Entity(name = "HouseholdMember")
@Table(name = "household_members")
public class HouseholdMember {

    @EmbeddedId
    private HouseholdMemberId id;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @MapsId("householdId")
    @JoinColumn(name = "household_id", insertable = false, updatable = false)
    private Household household;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

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
}
