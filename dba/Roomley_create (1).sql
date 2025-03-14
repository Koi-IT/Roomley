-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-03-14 13:17:37.788

-- tables
-- Table: GROCERIES
CREATE TABLE GROCERIES (
    item_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    type varchar(30)  NOT NULL,
    item_name varchar(30)  NOT NULL,
    CONSTRAINT GROCERIES_pk PRIMARY KEY (item_id)
);

-- Table: HOUSEHOLDS
CREATE TABLE HOUSEHOLDS (
    household_id int  NOT NULL AUTO_INCREMENT,
    group_name varchar(30)  NOT NULL,
    group_description varchar(30)  NOT NULL,
    CONSTRAINT HOUSEHOLDS_pk PRIMARY KEY (household_id)
);

-- Table: INGREDIENTS
CREATE TABLE INGREDIENTS (
    ingredient_id int  NOT NULL,
    ingredient_name int  NOT NULL,
    MEALS_meal_id int  NOT NULL
);

-- Table: MEALS
CREATE TABLE MEALS (
    meal_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    meal_name varchar(30)  NOT NULL,
    GROCERIES_item_id int  NOT NULL,
    CONSTRAINT MEALS_pk PRIMARY KEY (meal_id)
);

-- Table: NOTIFICATIONS
CREATE TABLE NOTIFICATIONS (
    notification_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    notification_type varchar(30)  NOT NULL,
    content varchar(30)  NOT NULL,
    CONSTRAINT NOTIFICATIONS_pk PRIMARY KEY (notification_id)
);

-- Table: TASKS
CREATE TABLE TASKS (
    task_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    task_name varchar(50)  NOT NULL,
    status bool  NOT NULL,
    task_description varchar(250)  NOT NULL,
    task_type varchar(30)  NOT NULL,
    CONSTRAINT TASKS_pk PRIMARY KEY (task_id)
);

-- Table: USERS
CREATE TABLE USERS (
    user_id int  NOT NULL AUTO_INCREMENT,
    Households_household_id int  NOT NULL,
    username varchar(30)  NOT NULL,
    password varchar(30)  NOT NULL,
    first_name varchar(30)  NOT NULL,
    last_name varchar(30)  NOT NULL,
    birthdate varchar(30)  NOT NULL,
    email varchar(30)  NOT NULL,
    user_level varchar(30)  NOT NULL,
    user_type varchar(30)  NOT NULL,
    household varchar(30)  NOT NULL,
    CONSTRAINT USERS_pk PRIMARY KEY (user_id)
);

-- foreign keys
-- Reference: INGREDIENTS_MEALS (table: INGREDIENTS)
ALTER TABLE INGREDIENTS ADD CONSTRAINT INGREDIENTS_MEALS FOREIGN KEY INGREDIENTS_MEALS (MEALS_meal_id)
    REFERENCES MEALS (meal_id);

-- Reference: Lists_Users (table: GROCERIES)
ALTER TABLE GROCERIES ADD CONSTRAINT Lists_Users FOREIGN KEY Lists_Users (Users_user_id)
    REFERENCES USERS (user_id);

-- Reference: MEALS_GROCERIES (table: MEALS)
ALTER TABLE MEALS ADD CONSTRAINT MEALS_GROCERIES FOREIGN KEY MEALS_GROCERIES (GROCERIES_item_id)
    REFERENCES GROCERIES (item_id);

-- Reference: Meals_Users (table: MEALS)
ALTER TABLE MEALS ADD CONSTRAINT Meals_Users FOREIGN KEY Meals_Users (Users_user_id)
    REFERENCES USERS (user_id);

-- Reference: Notifications_Users (table: NOTIFICATIONS)
ALTER TABLE NOTIFICATIONS ADD CONSTRAINT Notifications_Users FOREIGN KEY Notifications_Users (Users_user_id)
    REFERENCES USERS (user_id);

-- Reference: Tasks_Users (table: TASKS)
ALTER TABLE TASKS ADD CONSTRAINT Tasks_Users FOREIGN KEY Tasks_Users (Users_user_id)
    REFERENCES USERS (user_id);

-- Reference: Users_Households (table: USERS)
ALTER TABLE USERS ADD CONSTRAINT Users_Households FOREIGN KEY Users_Households (Households_household_id)
    REFERENCES HOUSEHOLDS (household_id);

-- End of file.

