/** Represents a task with a description and completion state. */
public class Task {
    private String description = "";
    private boolean isDone = false;
    private final TaskType type;

    /** Creates a task with the given description and category. */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
    }
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


    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + getDescription();
        } else {
            return "[ ] " + getDescription();
        }
    }
}
