public class ATHENA {
    public static void horlines(){
        System.out.println("____________________________________________________________");
    }
    public static void Greet(){
        horlines();
        String banner = " █████╗ ████████╗██╗  ██╗███████╗███╗   ██╗ █████╗\n"
                + "██╔══██╗╚══██╔══╝██║  ██║██╔════╝████╗  ██║██╔══██╗\n"
                + "███████║   ██║   ███████║█████╗  ██╔██╗ ██║███████║\n"
                + "██╔══██║   ██║   ██╔══██║██╔══╝  ██║╚██╗██║██╔══██║\n"
                + "██║  ██║   ██║   ██║  ██║███████╗██║ ╚████║██║  ██║\n"
                + "╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝\n";
        System.out.println(banner);
        System.out.println("Hello! I'm ATHENA.");
        System.out.println("What can I do for you?");

    }
    public static void Exit(){
        horlines();
        System.out.println("Bye. Hope to see you again soon!");
        horlines();
    }
    public static void main(String[] args) {
        Greet();
        Exit();
    }
}
