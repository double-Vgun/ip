/** Handles user commands and stores tasks created during the session. */
public class TaskHandler {
    private static final int MAX_NUM_OF_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_NUM_OF_TASKS];
    private static int numberOfTasks = 0;

    /** Marks the selected task as done. */
    public static void mark(String line) {
        Task task = getTask(line);
        if (task == null) {
            return;
        }
        task.setDone(true);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /** Marks the selected task as not done. */
    public static void unmark(String line) {
        Task task = getTask(line);
        if (task == null) {
            return;
        }
        task.setDone(false);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    /** Lists all stored tasks. */
    public static void list() {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
    }

    /** Adds a todo task. */
    public static void addTodo(String line) {
        if (line.isBlank()) {
            System.out.println(" Please tell me what todo task to add");
            return;
        }
        addTask(new ToDo(line));
    }

    /** Adds a deadline task from a complete command. */
    public static void addDeadline(String line) {
        String content = line.substring("deadline".length()).trim();
        if (content.isBlank()) {
            System.out.println("Please tell me what deadline task to add");
            return;
        }
        String[] parts = content.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank()) {
            System.out.println("Invalid syntax. Please use the following syntax: deadline {your_deadline_task}  /by "
                    + "{datetime} , where you can replace {your_deadline_task}  and  {datetime}");
            return;
        }
        if (parts[1].isBlank()) {
            System.out.println(" Please add a date for deadline");
            return;
        }
        addTask(new Deadline(parts[0], parts[1]));
    }

    /** Adds an event task from a complete command. */
    public static void addEvent(String line) {
        String content = line.substring("event".length()).trim();
        if (content.isBlank()) {
            System.out.println("Please tell me what event task to add");
            return;
        }
        if (!content.contains(" /from ") && !content.contains(" /to ")) {
            System.out.println("Invalid syntax. Please use the following syntax: event {your_event_task} /from "
                    + "{start_datetime} /to {end_datetime}, where you can replace {your_event_task}, "
                    + "{start_datetime} and {end_datetime}");
            return;
        }
        if (!content.contains(" /from ")) {
            System.out.println("please enter an start datetime value after /from");
            return;
        }
        String[] fromParts = content.split(" /from ", 2);
        if (fromParts.length < 2 || fromParts[1].isBlank()) {
            System.out.println("please enter an end datetime value after /to");
            return;
        }
        if (!fromParts[1].contains(" /to ")) {
            System.out.println("please enter an end datetime value after /to");
            return;
        }
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts[1].isBlank()) {
            System.out.println("please enter an end datetime value after /to");
            return;
        }
        addTask(new Event(fromParts[0], toParts[0], toParts[1]));
    }
    private static Task getTask(String line) {
        String[] parts = line.split(" ");
        if (parts.length < 2 || !parts[1].matches("\\d+")) {
            System.out.println("please enter a numeric value for task number");
            return null;
        }
        int taskNumber = Integer.parseInt(parts[1]);
        if (taskNumber < 1 || taskNumber > numberOfTasks) {
            System.out.println("Task does not exist");
            return null;
        }
        return tasks[taskNumber - 1];
    }

    private static void addTask(Task task) {
        tasks[numberOfTasks] = task;
        numberOfTasks++;
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + numberOfTasks + " tasks in the list.");
    }

    /** Handles one user command and updates the task list as needed. */
    public TaskHandler(String line) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            System.out.println("please fill in something");
            return;
        }
        String command = trimmedLine.split(" ", 2)[0];
        switch (command) {
            case "list":
                list();
                break;
            case "mark":
                mark(trimmedLine);
                break;
            case "unmark":
                unmark(trimmedLine);
                break;
            case "todo":
                addTodo(trimmedLine.substring("todo".length()).trim());
                break;
            case "deadline":
                addDeadline(trimmedLine);
                break;
            case "event":
                addEvent(trimmedLine);
                break;
            default:
                System.out.println("I dont understand what you want me to do, please start with deadline, todo, "
                        + "event, mark, unmark, list or bye");
                break;
        }
    }
}
