import ui.Ui;

/** Provides the command-line entry point for the ATHENA task manager. */
public class ATHENA {
    /** Starts the command-line task manager. */
    public static void main(String[] args) {
        try (Ui ui = new Ui()) {
            TaskHandler.loadTasks();
            ui.showGreeting();

            while (ui.hasNextCommand()) {
                String line = ui.readCommand();

                if (line.trim().equals("bye")) {
                    ui.showFarewell();
                    break;
                }
                ui.showHorizontalLine();
                try {
                    new TaskHandler(line);
                } catch (AthenaException exception) {
                    ui.showError(exception.getMessage());
                }
                ui.showHorizontalLine();
            }
        } catch (AthenaException exception) {
            System.out.println("Unable to start ATHENA. " + exception.getMessage());
        }
    }
}
