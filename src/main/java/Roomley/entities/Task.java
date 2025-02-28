package Roomley.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Task {

    Task task;
    String taskName;
    boolean taskStatus;
    String taskDescription;

    public void Task() {
        task = new Task();
        task.setTaskStatus(false);

    }

    public String getTaskName() { return this.taskName; }

    public void setTaskName(String taskName) {

    }

    public boolean getTaskStatus() { return this.taskStatus; }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDescription() { return this.taskDescription; }

    public void setTaskDescription(String taskDescription) {

    }

}
