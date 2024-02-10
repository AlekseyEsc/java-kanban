package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> historyTask = new ArrayList<>();
    private final int maxSizeHistory = 10;

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyTask);
    }

    @Override
    public void add(Task task) {
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
