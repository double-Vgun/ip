package command;

/** Finds tasks whose descriptions contain a keyword. */
public class FindCommand extends Command {
    /** Creates a find command from the user's complete input. */
    public FindCommand(String fullCommand) {
        super(fullCommand);
    }

    @Override
    public void execute(CommandExecutor executor) {
        executor.find(getFullCommand());
    }
}
