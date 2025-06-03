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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("householdId")
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "householdMember", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> tasks;

    @OneToMany(mappedBy = "invitedMember")
    private List<Invitation> receivedInvitations;

    @OneToMany(mappedBy = "invitedByMember")
    private List<Invitation> sentInvitations;


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
        this.tasks = tasks;
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

    /**
     * Gets tasks.
     *
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets tasks.
     *
     * @param tasks the tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets received invitations.
     *
     * @return the received invitations
     */
    public List<Invitation> getReceivedInvitations() {
        return receivedInvitations;
    }

    /**
     * Sets received invitations.
     *
     * @param receivedInvitations the received invitations
     */
    public void setReceivedInvitations(List<Invitation> receivedInvitations) {
        this.receivedInvitations = receivedInvitations;
    }

    /**
     * Gets sent invitations.
     *
     * @return the sent invitations
     */
    public List<Invitation> getSentInvitations() {
        return sentInvitations;
    }

    /**
     * Sets sent invitations.
     *
     * @param sentInvitations the sent invitations
     */
    public void setSentInvitations(List<Invitation> sentInvitations) {
        this.sentInvitations = sentInvitations;
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
                ", tasks=" + tasks +
                '}';
    }

}
