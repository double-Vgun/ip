package command;

/** Marks one task as not done. */
public class UnmarkCommand extends Command {
    /** Creates an unmark command from the user's complete input. */
    public UnmarkCommand(String fullCommand) {
        super(fullCommand);
    }

    /** Executes this command by marking the selected task as not done. */
    @Override
    public void execute(CommandExecutor executor) {
        executor.unmark(getFullCommand());
    }
}
