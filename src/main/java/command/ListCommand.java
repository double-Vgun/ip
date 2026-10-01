package command;

/** Lists the tasks currently stored by the application. */
public class ListCommand extends Command {
    /** Creates a list command from the user's complete input. */
    public ListCommand(String fullCommand) {
        super(fullCommand);
    }

    @Override
    public void execute(CommandExecutor executor) {
        executor.list(getFullCommand());
    }
}
