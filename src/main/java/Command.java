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
     * @param completionStatuses Completion state for each corresponding task.
     */
    public void showTaskList(List<String> tasks, List<Boolean> completionStatuses) {
        System.out.println(SEPARATOR);
        System.out.println(" Here are the tasks in your list:");
        if (tasks.size() <= 0) {
            System.out.println(" [List is currently empty]");
        }
        for (int i = 0; i < tasks.size(); i++) {
            String statusIcon = completionStatuses.get(i) ? "X" : " ";
            System.out.printf(" %d.[%s] %s%n", i + 1, statusIcon, tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Confirms that a task has been marked as done.
     *
     * @param task Text of the task that was completed.
     */
    public void showTaskMarkedAsDone(String task) {
        System.out.println(SEPARATOR);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.printf("   [X] %s%n", task);
        System.out.println(SEPARATOR);
    }

    /**
     * Explains that a mark command did not identify a task in the list.
     */
    public void showInvalidTaskNumber() {
        System.out.println(SEPARATOR);
        System.out.println(" Please enter the number of a task in your list.");
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
