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

}
