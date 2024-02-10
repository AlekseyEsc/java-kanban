package service;

import model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @DisplayName("HistoryManager don't change task")
    @Test
    void shouldBeTaskInHistoryWithoutChanges() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("a", "b");
        historyManager.add(task);
        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(task, history.get(0), "HistoryManager is not allowed to modify the task");
    }

    @DisplayName("Don't add duplicate")
    @Test
    void shouldBeReturnSize1IfAddTheSameTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("a", "b");
        historyManager.add(task1);
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size());
    }

}