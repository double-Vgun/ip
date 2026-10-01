package command;

/** Adds a todo, deadline, or event task. */
public class AddCommand extends Command {
    /** Creates an add command from the user's complete input. */
    public AddCommand(String fullCommand) {
        super(fullCommand);
    }

    @Override
    public void execute(CommandExecutor executor) {
        executor.add(getFullCommand());
    }
}
