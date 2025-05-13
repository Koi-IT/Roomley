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

    @Column(name = "invited_by_user_id")
    private String invitedByUserId;

    @Column(name = "invitation_created_at")
    private Timestamp invitationCreatedAt;

    @Column(name = "status")
    private String inviteStatus;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;


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
     * Gets invited by user id.
     *
     * @return the invited by user id
     */
    public String getInvitedByUserId() {
        return invitedByUserId;
    }

    /**
     * Sets invited by user id.
     *
     * @param invitedByUserId the invited by user id
     */
    public void setInvitedByUserId(String invitedByUserId) {
        this.invitedByUserId = invitedByUserId;
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
        return "Invitation{" +
                "invitationId=" + invitationId +
                ", invitedByUserId='" + invitedByUserId + '\'' +
                ", invitationCreatedAt=" + invitationCreatedAt +
                ", inviteStatus='" + inviteStatus + '\'' +
                ", household=" + household +
                '}';
    }
}
