import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ATHENA {
    public static void horizontal_lines(){
        System.out.println("____________________________________________________________");
    }
    public static void Greet(){
        horizontal_lines();
        String banner = " █████╗ ████████╗██╗  ██╗███████╗███╗   ██╗ █████╗\n"
                + "██╔══██╗╚══██╔══╝██║  ██║██╔════╝████╗  ██║██╔══██╗\n"
                + "███████║   ██║   ███████║█████╗  ██╔██╗ ██║███████║\n"
                + "██╔══██║   ██║   ██╔══██║██╔══╝  ██║╚██╗██║██╔══██║\n"
                + "██║  ██║   ██║   ██║  ██║███████╗██║ ╚████║██║  ██║\n"
                + "╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝\n";
        System.out.println(banner);
        System.out.println("Hello! I'm ATHENA.");
        System.out.println("What can I do for you?");
        horizontal_lines();

    }
    public static void Exit(){
        horizontal_lines();
        System.out.println("Bye. Hope to see you again soon!");
        horizontal_lines();
    }

    public static void main(String[] args) {
        String[] store_list = new String[100];
        int numberofentries =0;
        Greet();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String command = in.nextLine();

            if (command.equals("bye")) {
                Exit();
                break;
            }

            horizontal_lines();
            switch (command) {
                case "list":
                    for (int i = 0; i < numberofentries; i++) {
                        System.out.println((i + 1) + ". " + store_list[i]);
                    }
                    break;
                default:
                    store_list[numberofentries] = command;
                    numberofentries++;
                    System.out.println("added: " + command);
                    break;
            }
            horizontal_lines();
        }
        in.close();
    }
}
