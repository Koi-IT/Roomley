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

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "household_id", referencedColumnName = "household_id", insertable = false, updatable = false),
            @JoinColumn(name = "invited_user", referencedColumnName = "user_id", insertable = false, updatable = false)
    })
    private HouseholdMember invitedMember;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "household_id", referencedColumnName = "household_id", insertable = false, updatable = false),
            @JoinColumn(name = "invited_by_user", referencedColumnName = "user_id", insertable = false, updatable = false)
    })
    private HouseholdMember invitedByMember;

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
     * @param invitedMember       the invited member
     * @param invitedByMember     the invited by member
     * @param invitationCreatedAt the invitation created at
     * @param inviteStatus        the invite status
     */
    public Invitation(int invitationId, HouseholdMember invitedMember, HouseholdMember invitedByMember, Timestamp invitationCreatedAt, String inviteStatus) {
        this.invitationId = invitationId;
        this.invitedMember = invitedMember;
        this.invitedByMember = invitedByMember;
        this.invitationCreatedAt = invitationCreatedAt;
        this.inviteStatus = inviteStatus;

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
     * Gets invited member.
     *
     * @return the invited member
     */
    public HouseholdMember getInvitedMember() {
        return invitedMember;
    }

    /**
     * Sets invited member.
     *
     * @param invitedMember the invited member
     */
    public void setInvitedMember(HouseholdMember invitedMember) {
        this.invitedMember = invitedMember;
    }

    /**
     * Gets invited by member.
     *
     * @return the invited by member
     */
    public HouseholdMember getInvitedByMember() {
        return invitedByMember;
    }

    /**
     * Sets invited by member.
     *
     * @param invitedByMember the invited by member
     */
    public void setInvitedByMember(HouseholdMember invitedByMember) {
        this.invitedByMember = invitedByMember;
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
                ", invitedMember=" + invitedMember +
                ", invitedByMember=" + invitedByMember +
                ", invitationCreatedAt=" + invitationCreatedAt +
                ", inviteStatus='" + inviteStatus + '\'' +
                '}';
    }

}
