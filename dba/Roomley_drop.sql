-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-08 04:55:09.284

-- foreign keys
ALTER TABLE Household_Members
    DROP FOREIGN KEY Household_Members_Households;

ALTER TABLE Household_Members
    DROP FOREIGN KEY Household_Members_Users;

ALTER TABLE Households
    DROP FOREIGN KEY Households_Users;

ALTER TABLE Ingredients
    DROP FOREIGN KEY INGREDIENTS_MEALS;

ALTER TABLE Invitations
    DROP FOREIGN KEY Invitations_Households;

ALTER TABLE Groceries
    DROP FOREIGN KEY Lists_Users;

ALTER TABLE Meals
    DROP FOREIGN KEY MEALS_GROCERIES;

ALTER TABLE Meals
    DROP FOREIGN KEY Meals_Users;

ALTER TABLE Tasks
    DROP FOREIGN KEY Task_Completed_By;

ALTER TABLE Tasks
    DROP FOREIGN KEY Tasks_Users;

-- tables
DROP TABLE Groceries;

DROP TABLE Household_Members;

DROP TABLE Households;

DROP TABLE Ingredients;

DROP TABLE Invitations;

DROP TABLE Meals;

DROP TABLE Tasks;

DROP TABLE Users;

-- End of file.

