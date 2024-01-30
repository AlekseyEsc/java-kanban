package model;

public class Subtask extends Task {
    private final int EPIC_ID;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.EPIC_ID = epic.getId();
    }

    public int getEPIC_ID() {
        return EPIC_ID;
    }
}
