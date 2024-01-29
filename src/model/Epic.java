package model;

import model.Subtask;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;
    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void checkStatus() {
        for (Subtask subtask : subtasks) {
            if (Status.NEW.equals(subtask.getStatus()) || Status.IN_PROGRESS.equals(subtask.getStatus())) {
                setStatus(Status.IN_PROGRESS);
                return;
            } else {
                setStatus(Status.DONE);
            }
        }
    }
}
