package Roomley.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import Roomley.entities.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskDao {

    public TaskDao() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


        sessionFactory.close();
    }

    public void createTask(){

    }

    public void deleteTask() {

    }

    public void updateTask() {

    }

    public void insertTask() {

    }

    public List<Task> getAllTasks() {


        List<Task> allTasks = new ArrayList<>();



        return allTasks;
    }

}
