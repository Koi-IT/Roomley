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

    // Method to remove a user from the household and transfer ownership if necessary
    @Transactional
    public void removeUserFromHouseholdAndDeleteUser(int householdId, int userId) {
        // Step 1: Remove the user from the household (remove HouseholdMember entry)
        HouseholdMember member = findHouseholdMember(householdId, userId);
        if (member != null) {
            // Transfer ownership if necessary
            if (member.getRole() == HouseholdMember.HouseholdRole.OWNER) {
                transferOwnership(householdId);
            }

            // Delete the household member entry
            householdMemberDao.delete(member);
        }

        // Step 2: Delete user's tasks (dissociate or delete tasks)
        deleteUserTasks(userId);

        // Step 3: Safely delete the user after cleaning up all references
        deleteUser(userId);
    }

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



    // Helper method to find a HouseholdMember by householdId and userId
    private HouseholdMember findHouseholdMember(int householdId, int userId) {
        List<HouseholdMember> members = householdMemberDao.getByPropertiesEqual(Map.of(
                "household.householdId", householdId,
                "user.userId", userId
        ));
        return members.isEmpty() ? null : members.get(0);
    }

    // Method to transfer ownership to another member if necessary
    private void transferOwnership(int householdId) {
        // Step 1: Get all members (excluding the current owner) of the household
        List<HouseholdMember> members = householdMemberDao.getByPropertiesEqual(Map.of(
                "household.householdId", householdId,
                "role", HouseholdMember.HouseholdRole.MEMBER // Only consider non-owner members
        ));

        // Step 2: Check if there are any members to transfer ownership to
        if (!members.isEmpty()) {
            // Transfer ownership
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

    // Method to delete a user by userId
    public void deleteUser(int userId) {
        User user = userDao.getById(userId);
        if (user == null) return;


        // Step 1: Find all HouseholdMember entries with this user
        List<HouseholdMember> members = new ArrayList<>(userDao.getById(userId).getHouseholdMembers());

        // Step 2: Delete all HouseholdMember entries first
        for (HouseholdMember member : members) {
            householdMemberDao.delete(member);
        }

        // Step 3: Now safely delete the user
        userDao.delete(user);
    }

}
