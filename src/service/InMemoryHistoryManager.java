package service;

import model.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Task> historyTask = new ArrayList<>();
    private final int maxSizeHistory = 10;

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<Task>(historyTask);
    }

    @Override
    public void add(Task task) {
        if (historyTask.size() >= maxSizeHistory) {
            historyTask.remove(0);
        }
        historyTask.add(task);
    }
}