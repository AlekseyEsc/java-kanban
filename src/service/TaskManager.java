package service;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskCounts = 1;
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubtask = new HashMap<>();
    HashMap<Integer, Epic> allEpics = new HashMap<>();


    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(allTask.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(allSubtask.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(allEpics.values());
    }

    public void removeAllTasks() {
        allTask.clear();
    }

    public void removeAllSubtask() {
        allSubtask.clear();
    }

    public void removeAllEpics() {
        allEpics.clear();
    }

    public Task getTask(int id) {
        return allTask.get(id);
    }

    public void taskInProgress(Task task) {
        task.setStatus(Status.IN_PROGRESS);
    }

    public void taskDone(Task task) {
        task.setStatus(Status.DONE);
    }

    public void addTask(Task task) {
        int id = taskCounts;
        task.setId(id);
        allTask.put(id, task);
        taskCounts++;
    }

    public void updateTask(Task task) {
        if (task.getClass() == Epic.class) {
            ((Epic) task).checkStatus();
        }
        allTask.put(task.getId(), task);
    }

    public void removeTask(int id) {
        allTask.remove(id);
    }

    public ArrayList<Subtask> getSubtasks(Epic epic) {
        Epic selectEpic = (Epic) allTask.get(epic.getId());
        return selectEpic.getSubtasks();
    }

}
