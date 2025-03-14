-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-03-14 13:19:17.874

-- foreign keys
ALTER TABLE Ingredients
    DROP FOREIGN KEY INGREDIENTS_MEALS;

ALTER TABLE Groceries
    DROP FOREIGN KEY Lists_Users;

ALTER TABLE Meals
    DROP FOREIGN KEY MEALS_GROCERIES;

ALTER TABLE Meals
    DROP FOREIGN KEY Meals_Users;

ALTER TABLE Notifications
    DROP FOREIGN KEY Notifications_Users;

ALTER TABLE Tasks
    DROP FOREIGN KEY Tasks_Users;

ALTER TABLE Users
    DROP FOREIGN KEY Users_Households;

-- tables
DROP TABLE Groceries;

DROP TABLE Households;

DROP TABLE Ingredients;

DROP TABLE Meals;

DROP TABLE Notifications;

DROP TABLE Tasks;

DROP TABLE Users;

-- End of file.

