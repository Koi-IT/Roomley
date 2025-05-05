-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-04 21:47:24.132

-- tables
-- Table: Groceries
CREATE TABLE groceries (
    item_id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    type varchar(30)  NOT NULL,
    item_name varchar(30)  NOT NULL,
    CONSTRAINT groceries_pk PRIMARY KEY (item_id)
);

-- Table: Household_Members
CREATE TABLE household_members (
    household_id int  NOT NULL,
    user_id int  NOT NULL,
    role enum('owner', 'member')  NOT NULL DEFAULT 'member',
    CONSTRAINT household_members_pk PRIMARY KEY (household_id,user_id)
);

-- Table: Households
CREATE TABLE households (
    household_id int  NOT NULL AUTO_INCREMENT,
    group_name varchar(30)  NOT NULL,
    created_by_user_id int  NOT NULL,
    CONSTRAINT households_pk PRIMARY KEY (household_id)
);

-- Table: Ingredients
CREATE TABLE ingredients (
    ingredient_id int  NOT NULL,
    meal_id int  NOT NULL,
    ingredient_name varchar(255)  NOT NULL,
    CONSTRAINT ingredients_pk PRIMARY KEY (ingredient_id)
);

-- Table: Meals
CREATE TABLE meals (
    meal_id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    Groceries_item_id int  NOT NULL,
    meal_name varchar(30)  NOT NULL,
    CONSTRAINT meals_pk PRIMARY KEY (meal_id)
);

-- Table: Tasks
CREATE TABLE tasks (
    task_id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    task_name varchar(50)  NOT NULL,
    status bool  NOT NULL,
    task_description varchar(250)  NOT NULL,
    task_type varchar(30)  NOT NULL,
    CONSTRAINT tasks_pk PRIMARY KEY (task_id)
);

-- Table: Users
CREATE TABLE users (
    user_id int  NOT NULL AUTO_INCREMENT,
    cognito_sub varchar(255)  NOT NULL,
    username varchar(255)  NOT NULL,
    email varchar(255)  NOT NULL,
    created_at timestamp  NULL DEFAULT CURRENT_TIMESTAMP,
    last_login timestamp  NOT NULL,
    role varchar(20)  NOT NULL,
    UNIQUE INDEX unique_cognito_sub (cognito_sub),
    CONSTRAINT users_pk PRIMARY KEY (user_id)
);

DESCRIBE users;


-- Foreign keys
-- Reference: Household_Members_Households (table: Household_Members)
ALTER TABLE household_members ADD CONSTRAINT fk_household_members_households FOREIGN KEY (household_id)
    REFERENCES households (household_id) ON DELETE CASCADE;

-- Reference: Household_Members_Users (table: Household_Members)
ALTER TABLE household_members ADD CONSTRAINT fk_household_members_users FOREIGN KEY (user_id)
    REFERENCES users (user_id) ON DELETE CASCADE;

-- Reference: INGREDIENTS_MEALS (table: Ingredients)
ALTER TABLE ingredients ADD CONSTRAINT fk_ingredients_meals FOREIGN KEY (meal_id)
    REFERENCES meals (meal_id) ON DELETE CASCADE;

-- Reference: Lists_Users (table: Groceries)
ALTER TABLE groceries ADD CONSTRAINT fk_groceries_users FOREIGN KEY (user_id)
    REFERENCES users (user_id) ON DELETE CASCADE;

-- Reference: MEALS_GROCERIES (table: Meals)
ALTER TABLE meals ADD CONSTRAINT fk_meals_groceries FOREIGN KEY (Groceries_item_id)
    REFERENCES groceries (item_id) ON DELETE CASCADE;

-- Reference: Meals_Users (table: Meals)
ALTER TABLE meals ADD CONSTRAINT fk_meals_users FOREIGN KEY (user_id)
    REFERENCES users (user_id) ON DELETE CASCADE;

-- Reference: Tasks_Users (table: Tasks)
ALTER TABLE tasks ADD CONSTRAINT fk_tasks_users FOREIGN KEY (user_id)
    REFERENCES users (user_id) ON DELETE CASCADE;


-- End of file.

