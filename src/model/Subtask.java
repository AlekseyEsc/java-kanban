package model;

public class Subtask extends Task {
    private final int EPIC_ID;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.EPIC_ID = epic.getId();
    }

    @Override
    public void setId(int id) {
        if (id != EPIC_ID) {
            super.setId(id);
        }
    }

    public int getEpicId() {
        return EPIC_ID;
    }
}
