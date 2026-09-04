/** Represents a task scheduled between a start and end time. */
public class Event extends Task {
    private final String by;
    private final String from;

    /** Creates an event task with its description and time range. */
    public Event(String description, String from, String by) {
        super(description, TaskType.EVENT);
        this.by = by;
        this.from = from;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from +" to: "+by+ ")";
    }
}
