import java.util.List;
import java.util.Scanner;

/**
 * Handles command input and displays responses to the user.
 */
public class Command {
    private static final String SEPARATOR = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a command input/output handler.
     *
     * @param scanner Scanner connected to the input source.
     */
    public Command(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reports whether another command is available.
     *
     * @return {@code true} if another command can be read.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads and returns the next command exactly as entered.
     *
     * @return The next user command.
     */
    public String getCommand() {
        return scanner.nextLine();
    }

    /**
     * Echoes a command between separators.
     *
     * @param command Command to echo.
     */
    public void echoCommand(String command) {
        System.out.println(SEPARATOR);
        System.out.println(command);
        System.out.println(SEPARATOR);
    }

    /**
     * Displays all stored tasks with one-based numbering.
     *
     * @param tasks Tasks to display in input order.
     */
    public void showTaskList(List<String> tasks) {
        System.out.println(SEPARATOR);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("(%d) %s%n", i + 1, tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Displays the application greeting.
     *
     * @param banner Application banner.
     */
    public void showGreeting(String banner) {
        String greeting = SEPARATOR + "\n"
                + banner
                + "Hello! I'm Potato.\n"
                + "What can I do for you?\n"
                + SEPARATOR + "\n";
        System.out.print(greeting);
    }

    /**
     * Displays the application farewell.
     */
    public void showFarewell() {
        System.out.println(SEPARATOR);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }
}
