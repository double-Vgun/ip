package command;

/** Marks one task as done. */
public class MarkCommand extends Command {
    /** Creates a mark command from the user's complete input. */
    public MarkCommand(String fullCommand) {
        super(fullCommand);
    }

    @Override
    public void execute(CommandExecutor executor) {
        executor.mark(getFullCommand());
    }
}
