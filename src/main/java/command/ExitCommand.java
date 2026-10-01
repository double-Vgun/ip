package command;

/** Ends the current application session. */
public class ExitCommand extends Command {
    /** Creates an exit command from the user's complete input. */
    public ExitCommand(String fullCommand) {
        super(fullCommand);
    }

    @Override
    public void execute(CommandExecutor executor) {
        // No task operation is needed when exiting.
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
