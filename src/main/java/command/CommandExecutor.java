package command;

/** Defines the task operations that commands can invoke. */
public interface CommandExecutor {
    /** Adds a task described by the given command. */
    void add(String fullCommand);

    /** Deletes the task selected by the given command. */
    void delete(String fullCommand);

    /** Finds tasks whose descriptions contain the keyword in the given command. */
    void find(String fullCommand);

    /** Lists tasks after validating the given command. */
    void list(String fullCommand);

    /** Marks the task selected by the given command as done. */
    void mark(String fullCommand);

    /** Marks the task selected by the given command as not done. */
    void unmark(String fullCommand);
}
