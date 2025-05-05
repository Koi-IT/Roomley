-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-04 22:13:13.107

-- foreign keys
ALTER TABLE household_members
    DROP FOREIGN KEY fk_household_members_households;

ALTER TABLE household_members
    DROP FOREIGN KEY fk_household_members_users;

ALTER TABLE ingredients
    DROP FOREIGN KEY fk_ingredients_meals;

ALTER TABLE groceries
    DROP FOREIGN KEY fk_groceries_users;

ALTER TABLE meals
    DROP FOREIGN KEY fk_meals_groceries;

ALTER TABLE meals
    DROP FOREIGN KEY fk_meals_users;

ALTER TABLE tasks
    DROP FOREIGN KEY fk_tasks_users;



-- tables
DROP TABLE groceries;

DROP TABLE household_members;

DROP TABLE households;

DROP TABLE ingredients;

DROP TABLE meals;

DROP TABLE tasks;

DROP TABLE users;



-- End of file.

