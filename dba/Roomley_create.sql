-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-08 04:55:09.284

-- tables
-- Table: Groceries
CREATE TABLE Groceries (
    item_id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    type varchar(30)  NOT NULL,
    item_name varchar(30)  NOT NULL,
    CONSTRAINT Groceries_pk PRIMARY KEY (item_id)
);

-- Table: Household_Members
CREATE TABLE Household_Members (
    household_id int  NOT NULL,
    user_id int  NOT NULL,
    role enum('owner', 'member')  NOT NULL DEFAULT 'member',
    CONSTRAINT Household_Members_pk PRIMARY KEY (household_id,user_id)
);

-- Table: Households
CREATE TABLE Households (
    household_id int  NOT NULL AUTO_INCREMENT,
    group_name varchar(30)  NOT NULL,
    created_by_user_id int  NOT NULL,
    CONSTRAINT Households_pk PRIMARY KEY (household_id)
);

-- Table: Ingredients
CREATE TABLE Ingredients (
    ingredient_id int  NOT NULL AUTO_INCREMENT,
    meal_id int  NOT NULL,
    ingredient_name varchar(255)  NOT NULL,
    CONSTRAINT Ingredients_pk PRIMARY KEY (ingredient_id)
);

-- Table: Invitations
CREATE TABLE Invitations (
    Invitation_id int  NOT NULL AUTO_INCREMENT,
    household_id int  NOT NULL,
    invited_by_user_id int  NOT NULL,
    invited_user_email varchar(255)  NOT NULL,
    created_at timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'accepted', 'declined')  NOT NULL DEFAULT 'pending',
    CONSTRAINT Invitations_pk PRIMARY KEY (Invitation_id)
);

-- Table: Meals
CREATE TABLE Meals (
    meal_id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    item_id int  NOT NULL,
    meal_name varchar(30)  NOT NULL,
    CONSTRAINT Meals_pk PRIMARY KEY (meal_id)
);

-- Table: Tasks
CREATE TABLE Tasks (
    task_id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    task_name varchar(50)  NOT NULL,
    task_description varchar(250)  NOT NULL,
    status bool  NOT NULL,
    completed_by varchar(255)  NULL,
    updated_at timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT Tasks_pk PRIMARY KEY (task_id)
);

-- Table: Users
CREATE TABLE Users (
    user_id int  NOT NULL AUTO_INCREMENT,
    cognito_sub varchar(255)  NOT NULL,
    display_name varchar(255)  NOT NULL,
    email varchar(255)  NOT NULL,
    created_at timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login timestamp  NOT NULL,
    role varchar(20)  NOT NULL,
    UNIQUE INDEX unique_cognito_sub (cognito_sub),
    UNIQUE INDEX unique_email (email),
    CONSTRAINT Users_pk PRIMARY KEY (user_id)
);

-- foreign keys
-- Reference: Household_Members_Households (table: Household_Members)
ALTER TABLE Household_Members ADD CONSTRAINT Household_Members_Households FOREIGN KEY Household_Members_Households (household_id)
    REFERENCES Households (household_id);

-- Reference: Household_Members_Users (table: Household_Members)
ALTER TABLE Household_Members ADD CONSTRAINT Household_Members_Users FOREIGN KEY Household_Members_Users (user_id)
    REFERENCES Users (user_id);

-- Reference: Households_Users (table: Households)
ALTER TABLE Households ADD CONSTRAINT Households_Users FOREIGN KEY Households_Users (created_by_user_id)
    REFERENCES Users (user_id);

-- Reference: INGREDIENTS_MEALS (table: Ingredients)
ALTER TABLE Ingredients ADD CONSTRAINT INGREDIENTS_MEALS FOREIGN KEY INGREDIENTS_MEALS (meal_id)
    REFERENCES Meals (meal_id);

-- Reference: Invitations_Households (table: Invitations)
ALTER TABLE Invitations ADD CONSTRAINT Invitations_Households FOREIGN KEY Invitations_Households (household_id)
    REFERENCES Households (household_id);

-- Reference: Lists_Users (table: Groceries)
ALTER TABLE Groceries ADD CONSTRAINT Lists_Users FOREIGN KEY Lists_Users (user_id)
    REFERENCES Users (user_id);

-- Reference: MEALS_GROCERIES (table: Meals)
ALTER TABLE Meals ADD CONSTRAINT MEALS_GROCERIES FOREIGN KEY MEALS_GROCERIES (item_id)
    REFERENCES Groceries (item_id);

-- Reference: Meals_Users (table: Meals)
ALTER TABLE Meals ADD CONSTRAINT Meals_Users FOREIGN KEY Meals_Users (user_id)
    REFERENCES Users (user_id);

-- Reference: Task_Completed_By (table: Tasks)
ALTER TABLE Tasks ADD CONSTRAINT Task_Completed_By FOREIGN KEY Task_Completed_By (completed_by)
    REFERENCES Users (cognito_sub)
    ON DELETE SET NULL;

-- Reference: Tasks_Users (table: Tasks)
ALTER TABLE Tasks ADD CONSTRAINT Tasks_Users FOREIGN KEY Tasks_Users (user_id)
    REFERENCES Users (user_id);

-- End of file.

