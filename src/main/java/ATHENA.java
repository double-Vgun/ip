package athena;

import java.util.Scanner;

/** Provides the command-line entry point for the ATHENA task manager. */
public class Athena {
    /** Prints the separator used by the command-line interface. */
    public static void printHorizontalLines() {
        System.out.println("____________________________________________________________");
    }
    /** Displays the greeting banner and instructions. */
    public static void greet() {
        printHorizontalLines();
        String banner = " █████╗ ████████╗██╗  ██╗███████╗███╗   ██╗ █████╗\n"
                + "██╔══██╗╚══██╔══╝██║  ██║██╔════╝████╗  ██║██╔══██╗\n"
                + "███████║   ██║   ███████║█████╗  ██╔██╗ ██║███████║\n"
                + "██╔══██║   ██║   ██╔══██║██╔══╝  ██║╚██╗██║██╔══██║\n"
                + "██║  ██║   ██║   ██║  ██║███████╗██║ ╚████║██║  ██║\n"
                + "╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝\n";
        System.out.println(banner);
        System.out.println("Hello! I'm ATHENA.");
        System.out.println("What can I do for you?");
        printHorizontalLines();

    }
    /** Displays the farewell message. */
    public static void exit() {
        printHorizontalLines();
        System.out.println("Bye. Hope to see you again soon!");
        printHorizontalLines();
    }
    /** Marks the selected task as done. */
    public static void mark(String line, Task[] tasks) {
        String[] words = line.split(" ");
        int taskNumber = Integer.parseInt(words[1]);
        Task task = tasks[taskNumber - 1];
        task.setDone(true);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task.getStatus());
    }
    /** Marks the selected task as not done. */
    public static void unmark(String line, Task[] tasks) {
        String[] words = line.split(" ");
        int taskNumber = Integer.parseInt(words[1]);
        Task task = tasks[taskNumber - 1];
        task.setDone(false);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task.getStatus());
    }

    /** Starts the command-line task manager. */
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int numberOfTasks = 0;
        greet();
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();

            if (line.equals("bye")) {
                exit();
                break;
            }

            printHorizontalLines();
            if (line.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < numberOfTasks; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i].getStatus());
                }
            } else if (line.startsWith("mark")) {
                mark(line, tasks);
            } else if (line.startsWith("unmark")) {
                unmark(line, tasks);
            } else {
                tasks[numberOfTasks] = new Task();
                tasks[numberOfTasks].setDescription(line);
                numberOfTasks++;
                System.out.println("added: " + line);
            }
            printHorizontalLines();
        }
        input.close();
    }
}
