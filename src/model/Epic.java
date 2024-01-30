package model;

import java.util.ArrayList;

/*
Также советуем применить знания о методах equals() и hashCode(), чтобы реализовать идентификацию задачи по её id.
При этом две задачи с одинаковым id должны выглядеть для менеджера как одна и та же.
💡 Эти методы нежелательно переопределять в наследниках. Ваша задача — подумать, почему.

toString переопределил
*/


public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtaskId(Integer id) {
        subtaskIds.add(id);
    }

    public void removeSubtaskId(Subtask subtask) {
        subtaskIds.remove(subtask.getId());
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + this.getName() + "'" +
                ", description='" + this.getDescription() + "'" +
                ", id='" + this.getId() + "'" +
                ", status='" + this.getStatus() + "'" +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
