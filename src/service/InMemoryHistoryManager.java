package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> historyTask = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyTask);
    }

    @Override
    public void add(Task task) {
        int maxSizeHistory = 10;

        if (historyTask.size() >= maxSizeHistory) {
            historyTask.remove(0);
        }

        if (historyTask.contains(task)) {
            historyTask.remove(task);
            historyTask.add(task);
            return;
        }
        historyTask.add(task);
    }
}
