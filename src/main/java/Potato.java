/**
 * Starts the Potato chatbot application.
 */
public class Potato {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        String banner = " ____   ___    _____     _     _____   ___\n"
                + "|  _ \\ / _ \\  |_   _|   / \\   |_   _| / _ \\\n"
                + "| |_) | | | |   | |    / _ \\    | |  | | | |\n"
                + "|  __/| |_| |   | |   / ___ \\   | |  | |_| |\n"
                + "|_|    \\___/    |_|  /_/   \\_\\  |_|   \\___/\n";
        printGreetingAndExit(banner);
    }

    private static void printGreetingAndExit(String banner) {
        String greeting = SEPARATOR + "\n"
                + banner
                + "Hello! I'm Potato.\n"
                + "What can I do for you?\n"
                + SEPARATOR + "\n"
                + "Bye. Hope to see you again soon!\n"
                + SEPARATOR + "\n";
        System.out.print(greeting);
    }
}
