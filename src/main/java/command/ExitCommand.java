package command;

/** Ends the current application session. */
public class ExitCommand extends Command {
    /** Creates an exit command from the user's complete input. */
    public ExitCommand(String fullCommand) {
        super(fullCommand);
    }

    /** Executes this command without performing a task operation. */
    @Override
    public void execute(CommandExecutor executor) {
        // No task operation is needed when exiting.
    }

    /** Returns whether this command should end the application. */
    @Override
    public boolean isExit() {
        return true;
    }
}
