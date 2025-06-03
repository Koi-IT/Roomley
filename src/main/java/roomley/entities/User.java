package roomley.entities;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type User.
 */
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "user_id")
    private int userId;

    @Column(name = "cognito_sub")
    private String cognitoSub;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "email")
    private String email;

    @Column(name = "last_login")
    private java.sql.Timestamp lastLogin;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<HouseholdMember> householdMembers = new ArrayList<>();

    /**
     * No argument constructor for User
     */
    public User() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());

    }

    /**
     * User constructor
     *
     * @param cognitoSub Cognito Sub
     * @param displayName    Username
     * @param email       Email
     * @param createdAt  the created at
     * @param lastLogin  Last Login
     * @param role        User Role
     */
    public User( String cognitoSub, String displayName, String email, java.sql.Timestamp createdAt,java.sql.Timestamp lastLogin, String role) {
        this.cognitoSub = cognitoSub;
        this.displayName = displayName;
        this.email = email;
        this.lastLogin = lastLogin;
        this.role = role;
        this.createdAt = createdAt;

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
        this.householdMembers = householdMembers;

    }

    public void addHouseholdMember(HouseholdMember member) {
        householdMembers.add(member);
        member.setUser(this);
    }

    public void removeHouseholdMember(HouseholdMember member) {
        householdMembers.remove(member);
        member.setUser(null);
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return userId;
    }

    /**
     * Gets Cognito Sub
     *
     * @return the Cognito Sub
     */
    public String getCognitoSub() { return cognitoSub; }

    /**
     * Gets displayName.
     *
     * @return the displayName
     */
    public String getUsername() {
        return displayName;
    }

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets user level.
     *
     * @return the user level
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public java.sql.Timestamp getLastLogin() {
        return lastLogin;
    }

    /**
     * Gets user role.
     *
     * @return the user type
     */
    public String getRole() { return role; }

    /**
     * Sets cognitoSub.
     *
     * @param cognitoSub the cognitoSub
     */
    public void setCognitoSub(String cognitoSub) {
        this.cognitoSub = cognitoSub;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets user createdAt.
     *
     * @param createdAt at the user createdAt
     */
    public void setCreateAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets lastLogin.
     *
     * @param lastLogin the lastLogin
     */
    public void setLastLogin(java.sql.Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Sets user role.
     *
     * @param role the user role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Sets displayName
     *
     * @param displayName the displayName
     */
    public void setUsername(String displayName) { this.displayName = displayName; }

    /**
     * Override the equals function to allow for equals between 2 objects to resolve properly
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId == user.userId;

    }

    /**
     * Return object hashcode
     * @return Object Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    /**
     * To string override for logging
     * @return User log
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", cognitoSub='" + cognitoSub + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createdAt=" + createdAt +
                ", email='" + email + '\'' +
                ", lastLogin=" + lastLogin +
                ", role='" + role + '\'' +
                '}';
    }

}