package Roomley.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_status")
    private boolean taskStatus;

    @Column(name = "task_description")
    private String taskDescription;

    public void Task() {
        Task task = new Task();
        task.setTaskStatus(false);

    }

    public void Task(String taskName,  String taskDescription, boolean taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() { return this.taskName; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public boolean getTaskStatus() { return this.taskStatus; }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDescription() { return this.taskDescription; }

    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

}
