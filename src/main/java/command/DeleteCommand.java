package command;

/** Deletes one task from the task list. */
public class DeleteCommand extends Command {
    /** Creates a delete command from the user's complete input. */
    public DeleteCommand(String fullCommand) {
        super(fullCommand);
    }

    /** Executes this command by deleting the task selected by the user. */
    @Override
    public void execute(CommandExecutor executor) {
        executor.delete(getFullCommand());
    }
}
