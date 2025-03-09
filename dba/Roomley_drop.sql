-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-03-02 02:24:12.205

-- foreign keys
ALTER TABLE Lists
    DROP FOREIGN KEY Lists_Users;

ALTER TABLE Meals
    DROP FOREIGN KEY Meals_Users;

ALTER TABLE Notifications
    DROP FOREIGN KEY Notifications_Users;

ALTER TABLE Tasks
    DROP FOREIGN KEY Tasks_Users;

ALTER TABLE Users
    DROP FOREIGN KEY Users_Households;

-- tables
DROP TABLE Households;

DROP TABLE Lists;

DROP TABLE Meals;

DROP TABLE Notifications;

DROP TABLE Tasks;

DROP TABLE Users;

-- End of file.

