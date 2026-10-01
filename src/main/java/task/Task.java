package task;

/** Represents a task with a description and completion state. */
public abstract class Task {
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

    /** Returns this task in the format used for persistent storage. */
    public String toFileString() {
        return type.name().charAt(0) + " | " + (isDone ? 1 : 0) + " | " + escapeFileField(description);
    }

    /** Returns text escaped for use as one field in the persistent file format. */
    protected static String escapeFileField(String value) {
        return value.replace("\\", "\\\\").replace("|", "\\|");
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
