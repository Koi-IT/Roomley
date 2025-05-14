package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Household member id.
 */
@Embeddable
public class HouseholdMemberId implements Serializable {

    @Column(name = "household_id")
    private int householdId;

    @Column(name = "user_id")
    private int userId;

    /**
     *
     */
    public HouseholdMemberId() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdMemberId that = (HouseholdMemberId) o;
        return householdId == that.householdId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(householdId, userId);
    }

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
}
