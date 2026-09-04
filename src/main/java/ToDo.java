/** Represents a task without a deadline or scheduled time. */
public class ToDo extends Task {

    /** Creates a to-do task with the given description. */
    public ToDo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
