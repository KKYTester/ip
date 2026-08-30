import java.util.Scanner;

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

        printGreeting(banner);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = readCommand(scanner);
            if (parseInput(command)) {
                break;
            }
        }
    }

    /**
     * Reads one command from the user.
     *
     * @param scanner Scanner connected to the input source.
     * @return The command exactly as entered by the user.
     */
    static String readCommand(Scanner scanner) {
        return scanner.nextLine();
    }

    /**
     * Echoes a command between separators.
     *
     * @param command Command to echo.
     */
    static void echoCommand(String command) {
        System.out.println(SEPARATOR);
        System.out.println(command);
        System.out.println(SEPARATOR);
    }

    /**
     * Parses a user command, prints the appropriate response, and reports whether Potato should exit.
     *
     * @param command Command entered by the user.
     * @return {@code true} when the command is "bye", ignoring case and surrounding whitespace.
     */
    static boolean parseInput(String command) {
        if (command.trim().equalsIgnoreCase("bye")) {
            printFarewell();
            return true;
        }

        echoCommand(command);
        return false;
    }

    private static void printGreeting(String banner) {
        String greeting = SEPARATOR + "\n"
                + banner
                + "Hello! I'm Potato.\n"
                + "What can I do for you?\n"
                + SEPARATOR + "\n";
        System.out.print(greeting);
    }

    private static void printFarewell() {
        System.out.println(SEPARATOR);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }
}
