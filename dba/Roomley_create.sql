-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-03-02 02:24:12.205

-- tables
-- Table: Households
CREATE TABLE Households (
    household_id int  NOT NULL AUTO_INCREMENT,
    group_name varchar(30)  NOT NULL,
    group_description varchar(30)  NOT NULL,
    CONSTRAINT Households_pk PRIMARY KEY (household_id)
);

-- Table: Lists
CREATE TABLE Lists (
    list_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    type varchar(30)  NOT NULL,
    list_contents varchar(30)  NOT NULL,
    CONSTRAINT Lists_pk PRIMARY KEY (list_id)
);

-- Table: Meals
CREATE TABLE Meals (
    meal_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    meal_name varchar(30)  NOT NULL,
    ingredients varchar(30)  NOT NULL,
    CONSTRAINT Meals_pk PRIMARY KEY (meal_id)
);

-- Table: Notifications
CREATE TABLE Notifications (
    notification_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    notification_type varchar(30)  NOT NULL,
    content varchar(30)  NOT NULL,
    CONSTRAINT Notifications_pk PRIMARY KEY (notification_id)
);

-- Table: Tasks
CREATE TABLE Tasks (
    task_id int  NOT NULL AUTO_INCREMENT,
    Users_user_id int  NOT NULL,
    task_name varchar(50)  NOT NULL,
    status bool  NOT NULL,
    task_description varchar(250)  NOT NULL,
    task_type varchar(30)  NOT NULL,
    CONSTRAINT Tasks_pk PRIMARY KEY (task_id)
);

-- Table: Users
CREATE TABLE Users (
    user_id int  NOT NULL AUTO_INCREMENT,
    Households_household_id int  NOT NULL,
    username varchar(30)  NOT NULL,
    password varchar(30)  NOT NULL,
    name varchar(30)  NOT NULL,
    email varchar(30)  NOT NULL,
    user_level varchar(30)  NOT NULL,
    user_type varchar(30)  NOT NULL,
    house_hold varchar(30)  NOT NULL,
    CONSTRAINT Users_pk PRIMARY KEY (user_id)
);

-- foreign keys
-- Reference: Lists_Users (table: Lists)
ALTER TABLE Lists ADD CONSTRAINT Lists_Users FOREIGN KEY Lists_Users (Users_user_id)
    REFERENCES Users (user_id);

-- Reference: Meals_Users (table: Meals)
ALTER TABLE Meals ADD CONSTRAINT Meals_Users FOREIGN KEY Meals_Users (Users_user_id)
    REFERENCES Users (user_id);

-- Reference: Notifications_Users (table: Notifications)
ALTER TABLE Notifications ADD CONSTRAINT Notifications_Users FOREIGN KEY Notifications_Users (Users_user_id)
    REFERENCES Users (user_id);

-- Reference: Tasks_Users (table: Tasks)
ALTER TABLE Tasks ADD CONSTRAINT Tasks_Users FOREIGN KEY Tasks_Users (Users_user_id)
    REFERENCES Users (user_id);

-- Reference: Users_Households (table: Users)
ALTER TABLE Users ADD CONSTRAINT Users_Households FOREIGN KEY Users_Households (Households_household_id)
    REFERENCES Households (household_id);

-- End of file.

