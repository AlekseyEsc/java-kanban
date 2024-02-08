package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {
    ArrayList<Task> getAllTasks();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Epic> getAllEpics();

    void removeAllTasks();

    void removeAllSubtask();

    void removeAllEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    Integer generateId();

    void createTask(Task task);

    void createSubtask(Subtask subtask);

    void createEpic(Epic epic);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    void removeTask(int id);

    void removeSubtask(int id);

    void removeEpic(int id);

    ArrayList<Subtask> getSubtasksByEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    void inProgress(Task task);

    void inDone(Task task);

    ArrayList<Task> getHistoryTasks();

    void addInHistory(Task task);
}
