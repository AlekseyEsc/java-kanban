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

    public Subtask getSubtask(int id) {
        return allSubtask.get(id);
    }

    public Epic getEpic(int id) {
        return allEpics.get(id);
    }
    public Integer generateId() {
        return taskCounts++;
    }

    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);
        allTask.put(id, task);
    }

    public void createSubtask(Subtask subtask) {
        Epic tempEpic = getEpic(subtask.getEPIC_ID());
        if (tempEpic == null) return;

        int id = generateId();
        subtask.setId(id);
        tempEpic.addSubtaskId(id);
        allSubtask.put(id, subtask);
        updateEpicStatus(tempEpic);
    }

    public void createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        allEpics.put(id, epic);
    }

    public void updateTask(Task task) {
        if (allTask.get(task.getId()) == null) return;
        allTask.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        if (allSubtask.get(subtask.getId()) == null) return;
        allSubtask.put(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        if (allEpics.get(epic.getId()) == null) return;
        allEpics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    public void removeTask(int id) {
        allTask.remove(id);
    }

    public void removeSubtask(int id) {
        allSubtask.remove(id);
    }

    public void removeEpic(int id) {
        Epic selectedEpic = allEpics.get(id);
        for (Integer subtaskId : selectedEpic.getSubtaskIds()) { // remove all subtasks epic
            allSubtask.remove(subtaskId);
        }
        allEpics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksByEpic(Epic epic) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        Epic selectedEpic = allEpics.get(epic.getId());
        ArrayList<Integer> subtaskIds = selectedEpic.getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            subtasksByEpic.add(allSubtask.get(subtaskId));
        }
        return subtasksByEpic;
    }

    public void updateEpicStatus(Epic epic) {
        boolean isNew = false;
        boolean isInProgress = false;
        boolean isDone = false;
        Epic selectedEpic = allEpics.get(epic.getId());
        ArrayList<Integer> selectedSubtaskId = selectedEpic.getSubtaskIds();
        for (Integer subtaskId : selectedSubtaskId) {
            Status statusSubtask = allSubtask.get(subtaskId).getStatus();
            if (Status.NEW.equals(statusSubtask)) {
                isNew = true;
            } else if (Status.IN_PROGRESS.equals(statusSubtask)) {
                isInProgress = true;
            } else if (Status.DONE.equals(statusSubtask)) {
                isDone = true;
            }
        }
        if (!isNew && !isInProgress && isDone) {
            selectedEpic.setStatus(Status.DONE);
        } else if (isNew && !isInProgress && !isDone) {
            selectedEpic.setStatus(Status.NEW);
        } else {
            selectedEpic.setStatus(Status.IN_PROGRESS);
        }
    }

    public void inProgress(Task task) {
        task.setStatus(Status.IN_PROGRESS);
    }

    public void inDone(Task task) {
        task.setStatus(Status.DONE);
    }

}
