import java.util.Scanner;

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
    public static void mark(String line, Task[] tasks_list){
        String[] words = line.split(" ");
        int taskNumber = Integer.parseInt(words[1]);
        Task t = tasks_list[taskNumber - 1];
        t.setDone(true);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + t.getStatus());
    }
    public static void unmark(String line, Task[] tasks_list){
        String[] words = line.split(" ");
        int taskNumber = Integer.parseInt(words[1]);
        Task t = tasks_list[taskNumber - 1];
        t.setDone(false);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + t.getStatus());
    }

    public static void main(String[] args) {
        Task[] tasks_list = new Task[100];
        int numberoftasks =0;
        Greet();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();

            if (line.equals("bye")) {
                Exit();
                break;
            }

            horizontal_lines();
            if (line.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < numberoftasks; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks_list[i].getStatus());
                }
            }
            else if(line.startsWith("mark")) {
                mark(line,tasks_list);
            }
            else if(line.startsWith("unmark")) {
                unmark(line,tasks_list);
            }
            else{
                tasks_list[numberoftasks] = new Task();
                tasks_list[numberoftasks].setDescription(line);
                numberoftasks++;
                System.out.println("added: " + line);
            }
            horizontal_lines();
        }
        in.close();
    }
}
