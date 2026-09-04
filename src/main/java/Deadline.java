/** Represents a task that must be completed by a specified time. */
public class Deadline extends Task {
    private final String by;

    /** Creates a deadline task with its description and due time. */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
