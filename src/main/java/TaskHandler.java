/** Handles user commands and stores tasks created during the session. */
public class TaskHandler {
    private static final int MAX_NUM_OF_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_NUM_OF_TASKS];
    private static int numberOfTasks = 0;

    /** Marks the selected task as done. */
    public static void mark(String line) {
        Task task = getTask(line);
        task.setDone(true);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /** Marks the selected task as not done. */
    public static void unmark(String line) {
        Task task = getTask(line);
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
        addTask(new ToDo(line));
    }

    /** Adds a deadline task from a complete command. */
    public static void addDeadline(String line) {
        String[] parts = line.substring("deadline ".length()).split(" /by ", 2);
        addTask(new Deadline(parts[0], parts[1]));
    }

    /** Adds an event task from a complete command. */
    public static void addEvent(String line) {
        String content = line.substring("event ".length());
        String[] fromParts = content.split(" /from ", 2);
        String[] toParts = fromParts[1].split(" /to ", 2);
        addTask(new Event(fromParts[0], toParts[0], toParts[1]));
    }
    public static void addDefault(String line){
        addTask(new Task(line, TaskType.DEFAULT));
    }

    private static Task getTask(String line) {
        int taskNumber = Integer.parseInt(line.split(" ")[1]);
        return tasks[taskNumber - 1];
    }

    private static void addTask(Task task) {
        tasks[numberOfTasks] = task;
        numberOfTasks++;
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + numberOfTasks + " tasks in the list.");
    }

    public TaskHandler(String line) {
        String trimmedLine = line.trim();
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
            addTodo(trimmedLine.substring("todo ".length()));
            break;
        case "deadline":
            addDeadline(trimmedLine);
            break;
        case "event":
            addEvent(trimmedLine);
            break;
        default:
            addDefault(trimmedLine);
            break;
        }
    }


}
