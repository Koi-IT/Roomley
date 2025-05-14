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
    private String cognito_sub;

    @Column(name = "display_name")
    private String username;

    @Column(name = "user_created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "email")
    private String email;

    @Column(name = "last_login")
    private java.sql.Timestamp last_login;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<HouseholdMember> householdMembers;

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

    /**
     * No argument constructor for User
     */
    public User() {
        this.created_at = Timestamp.valueOf(LocalDateTime.now());

    }

    /**
     * User constructor
     *
     * @param cognito_sub Cognito Sub
     * @param username    Username
     * @param email       Email
     * @param created_at  the created at
     * @param last_login  Last Login
     * @param role        User Role
     */
    public User( String cognito_sub, String username, String email, java.sql.Timestamp created_at,java.sql.Timestamp last_login, String role) {
        this.cognito_sub = cognito_sub;
        this.username = username;
        this.email = email;
        this.last_login = last_login;
        this.role = role;
        this.created_at = created_at;

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
    public String getCognitoSub() { return cognito_sub; }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return email;
    }

    /**
     * Gets user level.
     *
     * @return the user level
     */
    public Timestamp getCreatedAt() {
        return created_at;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public java.sql.Timestamp getLastLogin() {
        return last_login;
    }

    /**
     * Gets user role.
     *
     * @return the user type
     */
    public String getRole() { return role; }

    /**
     * Returns the list of tasks associated with this user.
     * Modifications to the returned list may affect the internal task list.
     *
     * @return task list for the user
     */
    public List<Task> getTasks() { return tasks; }

    /**
     * Sets cognito_sub.
     *
     * @param cognito_sub the cognito_sub
     */
    public void setCognitoSub(String cognito_sub) {
        this.cognito_sub = cognito_sub;
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
     * Sets user created_at.
     *
     * @param created_at at the user created_at
     */
    public void setCreateAt(java.sql.Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * Sets last_login.
     *
     * @param last_login the last_login
     */
    public void setLastLogin(java.sql.Timestamp last_login) {
        this.last_login = last_login;
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
     * Sets username
     *
     * @param username the username
     */
    public void setUsername(String username) { this.username = username; }

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
                ", cognito_sub='" + cognito_sub + '\'' +
                ", username='" + username + '\'' +
                ", created_at=" + created_at +
                ", email='" + email + '\'' +
                ", last_login=" + last_login +
                ", role='" + role + '\'' +
                '}';
    }

}