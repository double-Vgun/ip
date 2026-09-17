import java.util.Arrays;
import java.util.List;

/** Handles user commands and stores tasks created during the session. */
public class TaskHandler {
    private static final int MAX_NUM_OF_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_NUM_OF_TASKS];
    private static int numberOfTasks = 0;

    /** Loads saved tasks from disk when a data file exists. */
    public static void loadTasks() {
        List<Task> loadedTasks = Storage.loadTasks();
        if (loadedTasks.size() > MAX_NUM_OF_TASKS) {
            throw new AthenaException("Unable to load tasks: the saved list exceeds "
                    + MAX_NUM_OF_TASKS + " tasks");
        }

        Arrays.fill(tasks, null);
        numberOfTasks = loadedTasks.size();
        for (int i = 0; i < numberOfTasks; i++) {
            tasks[i] = loadedTasks.get(i);
        }
    }

    /** Marks the selected task as done. */
    public static void mark(String line) {
        Task task = getTask(line);
        boolean wasDone = task.isDone();
        task.setDone(true);
        try {
            saveTasks();
        } catch (AthenaException exception) {
            task.setDone(wasDone);
            throw exception;
        }
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /** Marks the selected task as not done. */
    public static void unmark(String line) {
        Task task = getTask(line);
        boolean wasDone = task.isDone();
        task.setDone(false);
        try {
            saveTasks();
        } catch (AthenaException exception) {
            task.setDone(wasDone);
            throw exception;
        }
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
            throw new AthenaException(" Please tell me what todo task to add");
        }
        addTask(new ToDo(line));
    }

    /** Adds a deadline task from a complete command. */
    public static void addDeadline(String line) {
        String content = line.substring("deadline".length()).trim();
        if (content.isBlank()) {
            throw new AthenaException("Please tell me what deadline task to add");
        }
        int byPosition = findMarker(content, "/by", 0);
        if (byPosition < 0) {
            throw new AthenaException("Invalid syntax. Use: deadline DESCRIPTION /by DATE");
        }
        String description = content.substring(0, byPosition).trim();
        String by = content.substring(byPosition + "/by".length()).trim();
        if (description.isBlank()) {
            throw new AthenaException("Please tell me what deadline task to add");
        }
        if (by.isBlank()) {
            throw new AthenaException("Please add a date for the deadline");
        }
        addTask(new Deadline(description, by));
    }

    /** Adds an event task from a complete command. */
    public static void addEvent(String line) {
        String content = line.substring("event".length()).trim();
        if (content.isBlank()) {
            throw new AthenaException("Please tell me what event task to add");
        }
        int fromPosition = findMarker(content, "/from", 0);
        if (fromPosition < 0) {
            throw new AthenaException("Invalid syntax. Use: event DESCRIPTION /from START /to END");
        }
        int toPosition = findMarker(content, "/to", fromPosition + "/from".length());
        if (toPosition < 0) {
            throw new AthenaException("Invalid syntax. Use: event DESCRIPTION /from START /to END");
        }
        String description = content.substring(0, fromPosition).trim();
        String from = content.substring(fromPosition + "/from".length(), toPosition).trim();
        String to = content.substring(toPosition + "/to".length()).trim();
        if (description.isBlank()) {
            throw new AthenaException("Please tell me what event task to add");
        }
        if (from.isBlank()) {
            throw new AthenaException("Please add a start time for the event");
        }
        if (to.isBlank()) {
            throw new AthenaException("Please add an end time for the event");
        }
        addTask(new Event(description, from, to));
    }

    private static Task getTask(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2 || !parts[1].matches("\\d+")) {
            throw new AthenaException("Please enter exactly one numeric task number");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException exception) {
            throw new AthenaException("Task number is too large", exception);
        }
        if (taskNumber < 1 || taskNumber > numberOfTasks) {
            throw new AthenaException("Task does not exist");
        }
        return tasks[taskNumber - 1];
    }

    private static int findMarker(String text, String marker, int fromIndex) {
        int position = text.indexOf(marker, fromIndex);
        while (position >= 0) {
            int endPosition = position + marker.length();
            boolean hasStartBoundary = position == 0 || Character.isWhitespace(text.charAt(position - 1));
            boolean hasEndBoundary = endPosition == text.length()
                    || Character.isWhitespace(text.charAt(endPosition));
            if (hasStartBoundary && hasEndBoundary) {
                return position;
            }
            position = text.indexOf(marker, position + 1);
        }
        return -1;
    }

    private static void addTask(Task task) {
        if (numberOfTasks >= MAX_NUM_OF_TASKS) {
            throw new AthenaException("Task list is full. No more tasks can be added");
        }
        tasks[numberOfTasks] = task;
        numberOfTasks++;
        try {
            saveTasks();
        } catch (AthenaException exception) {
            numberOfTasks--;
            tasks[numberOfTasks] = null;
            throw exception;
        }
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + numberOfTasks + " tasks in the list.");
    }

    private static void saveTasks() {
        Storage.saveTasks(tasks, numberOfTasks);
    }

    /** Handles one user command and updates the task list as needed. */
    public TaskHandler(String line) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            throw new AthenaException("please fill in something");
        }
        String command = trimmedLine.split(" ", 2)[0];
        switch (command) {
            case "list":
                if (!trimmedLine.equals("list")) {
                    throw new AthenaException("The list command does not take additional arguments");
                }
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
                throw new AthenaException("I dont understand what you want me to do, please start with deadline, "
                        + "todo, event, mark, unmark, list or bye");
        }
    }
}
