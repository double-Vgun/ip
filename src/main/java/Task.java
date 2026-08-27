package athena;

/** Represents a task with a description and completion state. */
public class Task {
    private String description = "";
    private boolean isDone = false;
    /** Sets whether this task is complete. */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
    /** Sets this task's description. */
    public void setDescription(String description) {
        this.description = description;
    }
    /** Returns this task's description. */
    public String getDescription() {
        return description;
    }
    /** Returns whether this task is complete. */
    public boolean isDone() {
        return isDone;
    }
    /** Returns this task formatted with its completion status. */
    public String getStatus() {
        if (isDone) {
            return "[X] " + getDescription();
        } else {
            return "[ ] " + getDescription();
        }
    }
}
