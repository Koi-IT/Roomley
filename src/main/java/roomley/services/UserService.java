package roomley.services;

import roomley.entities.*;
import roomley.persistence.GenericDao;
import org.hibernate.Hibernate;
import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import roomley.entities.User;
import roomley.persistence.GenericDao;
import javax.transaction.Transactional;
import java.util.*;

/**
 * The type User service.
 */
public class UserService {

    private final GenericDao<HouseholdMember, Integer> householdMemberDao = new GenericDao<>(HouseholdMember.class);
    private final GenericDao<User, Integer> userDao = new GenericDao<>(User.class);
    private final GenericDao<Household, Integer> householdDao = new GenericDao<>(Household.class);
    private final GenericDao<Task, Integer> taskDao = new GenericDao<>(Task.class);

    /**
     * Remove user from household and delete user.
     *
     * @param householdId the household id
     * @param userId      the user id
     */
    @Transactional
    public void removeUserFromHouseholdAndDeleteUser(int householdId, int userId) {

        HouseholdMember member = findHouseholdMember(householdId, userId);
        if (member != null) {
            // Transfer ownership if necessary
            if (member.getRole() == HouseholdMember.HouseholdRole.OWNER) {
                transferOwnership(householdId);
            }

            // Delete the household member entry
            householdMemberDao.delete(member);
        }

        deleteUserTasks(userId);

        deleteUser(userId);
    }

    /**
     * Deletes user with tasks
     * @param userId
     */
    private void deleteUserTasks(int userId) {
        // Find all tasks associated with the user
        List<Task> tasks = taskDao.getByPropertiesEqual(Map.of("user.userId", userId));

        // If tasks are found, delete them
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                taskDao.delete(task);
            }
        }
    }

    /**
     * Find the household member
     * @param householdId
     * @param userId
     * @return
     */
    private HouseholdMember findHouseholdMember(int householdId, int userId) {
        List<HouseholdMember> members = householdMemberDao.getByPropertiesEqual(Map.of(
                "household.householdId", householdId,
                "user.userId", userId
        ));
        return members.isEmpty() ? null : members.get(0);
    }

    /**
     * Transfer household ownership
     * @param householdId
     */
    private void transferOwnership(int householdId) {

        List<HouseholdMember> members = householdMemberDao.getByPropertiesEqual(Map.of(
                "household.householdId", householdId,
                "role", HouseholdMember.HouseholdRole.MEMBER
        ));

        if (!members.isEmpty()) {
            HouseholdMember newOwner = members.get(0);
            newOwner.setRole(HouseholdMember.HouseholdRole.OWNER);
            householdMemberDao.update(newOwner);

            Household household = householdDao.getById(householdId);
            household.setCreatedByUserId(newOwner.getId().getUserId());
            householdDao.update(household);

        } else {
            System.out.println("Warning: No other members to transfer ownership.");
        }



    }

    /**
     * Delete user.
     *
     * @param userId the user id
     */
    public void deleteUser(int userId) {
        User user = userDao.getById(userId);
        if (user == null) return;


        List<HouseholdMember> members = new ArrayList<>(userDao.getById(userId).getHouseholdMembers());

        for (HouseholdMember member : members) {
            householdMemberDao.delete(member);
        }

        userDao.delete(user);
    }

}
