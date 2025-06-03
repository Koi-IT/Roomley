package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Invitation.
 */
@Entity(name = "Invitation")
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "invitation_id")
    private int invitationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invited_by_user", nullable = false)
    private User invitedByUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invited_user", nullable = false)
    private User invitedUser;        // was invitedMember

    @ManyToOne(optional = false)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;     // direct reference

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp invitationCreatedAt;

    @Column(name = "status")
    private String inviteStatus;

    /**
     * Instantiates a new Invitation.
     */
    public Invitation() {
    }

    /**
     * Instantiates a new Invitation.
     *
     * @param invitationId        the invitation id
     * @param invitedUser         the invited user
     * @param household           the household
     * @param invitationCreatedAt the invitation created at
     * @param inviteStatus        the invite status
     */
    public Invitation(int invitationId, User invitedUser, Household household, Timestamp invitationCreatedAt, String inviteStatus) {
        this.invitationId = invitationId;
        this.invitedUser = invitedUser;
        this.household = household;
        this.invitationCreatedAt = invitationCreatedAt;
        this.inviteStatus = inviteStatus;
    }

    /**
     * Gets invited by user.
     *
     * @return the invited by user
     */
    public User getInvitedByUser() {
        return invitedByUser;
    }

    /**
     * Sets invited by user.
     *
     * @param invitedByUser the invited by user
     */
    public void setInvitedByUser(User invitedByUser) {
        this.invitedByUser = invitedByUser;
    }

    /**
     * Gets invitation id.
     *
     * @return the invitation id
     */
    public int getInvitationId() {
        return invitationId;
    }

    /**
     * Sets invitation id.
     *
     * @param invitationId the invitation id
     */
    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    /**
     * Gets invited user.
     *
     * @return the invited user
     */
    public User getInvitedUser() {
        return invitedUser;
    }

    /**
     * Sets invited user.
     *
     * @param invitedUser the invited user
     */
    public void setInvitedUser(User invitedUser) {
        this.invitedUser = invitedUser;
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
     * Gets invitation created at.
     *
     * @return the invitation created at
     */
    public Timestamp getInvitationCreatedAt() {
        return invitationCreatedAt;
    }

    /**
     * Sets invitation created at.
     *
     * @param invitationCreatedAt the invitation created at
     */
    public void setInvitationCreatedAt(Timestamp invitationCreatedAt) {
        this.invitationCreatedAt = invitationCreatedAt;
    }

    /**
     * Gets invite status.
     *
     * @return the invite status
     */
    public String getInviteStatus() {
        return inviteStatus;
    }

    /**
     * Sets invite status.
     *
     * @param inviteStatus the invite status
     */
    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationId=" + invitationId +
                ", invitedUser=" + invitedUser +
                ", household=" + household +
                ", invitationCreatedAt=" + invitationCreatedAt +
                ", inviteStatus='" + inviteStatus + '\'' +
                '}';
    }

}
