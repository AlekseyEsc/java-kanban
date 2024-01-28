import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    private static int taskCounts = 1;
    HashMap<Integer, Task> allTask = new HashMap<>();


    public Collection<Task> getAllTasks() {
        return allTask.values();
    }

    public void removeAllTasks() {
        allTask.clear();
    }

    public Task getTask(int id) {
        return allTask.get(id);
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
