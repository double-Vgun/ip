import java.util.Scanner;

/** Provides the command-line entry point for the ATHENA task manager. */
public class ATHENA {
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

    /** Starts the command-line task manager. */
    public static void main(String[] args) {

        greet();
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String line = input.nextLine();

            if ((line.trim()).equals("bye")) {
                exit();
                break;
            }
            printHorizontalLines();
            new TaskHandler(line);
            printHorizontalLines();
        }
        input.close();
    }
}
