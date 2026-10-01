package command;

/** Represents a user instruction that can be executed by the application. */
public abstract class Command {
    private final String fullCommand;

    /** Creates a command from the user's complete input. */
    protected Command(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /** Executes this command using the supplied command operations. */
    public abstract void execute(CommandExecutor executor);

    /** Returns whether this command should end the application. */
    public boolean isExit() {
        return false;
    }

    /** Returns the user's complete command. */
    protected String getFullCommand() {
        return fullCommand;
    }
}
