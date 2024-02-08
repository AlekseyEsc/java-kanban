package service;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private static int taskCounts = 1;
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubtask = new HashMap<>();
    HashMap<Integer, Epic> allEpics = new HashMap<>();
    ArrayList<Task> historyTasks = new ArrayList<>();
    private final int maxSizeHistory = 10;


    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(allTask.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(allSubtask.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(allEpics.values());
    }

    @Override
    public void removeAllTasks() {
        allTask.clear();
    }

    @Override
    public void removeAllSubtask() {
        allSubtask.clear();
    }

    @Override
    public void removeAllEpics() {
        allEpics.clear();
    }

    @Override
    public Task getTask(int id) {
        Task selectTask = allTask.get(id);
        Task returnTask = new Task(selectTask.getName(), selectTask.getDescription());
        returnTask.setId(selectTask.getId());
        addInHistory(returnTask);
        return returnTask;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask selectSubtask = allSubtask.get(id);
        Subtask returnSubtask = new Subtask(selectSubtask.getName(), selectSubtask.getDescription(), getEpic(selectSubtask.getEpicId()));
        returnSubtask.setId(selectSubtask.getId());
        addInHistory(returnSubtask);
        return returnSubtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic selectEpic = allEpics.get(id);
        Epic returnEpic = new Epic(selectEpic.getName(), selectEpic.getDescription());
        returnEpic.setId(selectEpic.getId());
        for (Integer subtaskId : selectEpic.getSubtaskIds()) {
            returnEpic.addSubtaskId(subtaskId);
        }
        addInHistory(returnEpic);
        return returnEpic;
    }
    @Override
    public Integer generateId() {
        return taskCounts++;
    }

    @Override
    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);
        allTask.put(id, task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        Epic tempEpic = getEpic(subtask.getEpicId());
        historyTasks.remove((historyTasks.size()-1));
        if (tempEpic == null) return;

        int id = generateId();
        subtask.setId(id);
        tempEpic.addSubtaskId(id);
        allSubtask.put(id, subtask);
        updateEpicStatus(tempEpic);
    }

    @Override
    public void createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        allEpics.put(id, epic);
    }

    @Override
    public void updateTask(Task task) {
        if (allTask.get(task.getId()) == null) return;
        allTask.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (allSubtask.get(subtask.getId()) == null) return;
        allSubtask.put(subtask.getId(), subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (allEpics.get(epic.getId()) == null) return;
        allEpics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public void removeTask(int id) {
        allTask.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        allSubtask.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        Epic selectedEpic = allEpics.get(id);
        for (Integer subtaskId : selectedEpic.getSubtaskIds()) { // remove all subtasks epic
            allSubtask.remove(subtaskId);
        }
        allEpics.remove(id);
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpic(Epic epic) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        Epic selectedEpic = allEpics.get(epic.getId());
        ArrayList<Integer> subtaskIds = selectedEpic.getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            subtasksByEpic.add(allSubtask.get(subtaskId));
        }
        return subtasksByEpic;
    }

    @Override
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

    @Override
    public void inProgress(Task task) {
        task.setStatus(Status.IN_PROGRESS);
    }

    @Override
    public void inDone(Task task) {
        task.setStatus(Status.DONE);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<Task>(historyTasks) ;
    }

    @Override
    public void addInHistory(Task task) {
        if (historyTasks.size() >= maxSizeHistory) {
            historyTasks.remove(0);
        }
        historyTasks.add(task);
    }
}
