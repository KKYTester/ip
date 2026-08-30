import java.util.Scanner;

/**
 * Starts the Potato chatbot application.
 */
public class Potato {
    /**
     * Starts the application and processes commands until the user exits.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        String banner = " ____   ___    _____     _     _____   ___\n"
                + "|  _ \\ / _ \\  |_   _|   / \\   |_   _| / _ \\\n"
                + "| |_) | | | |   | |    / _ \\    | |  | | | |\n"
                + "|  __/| |_| |   | |   / ___ \\   | |  | |_| |\n"
                + "|_|    \\___/    |_|  /_/   \\_\\  |_|   \\___/\n";

        Command command = new Command(new Scanner(System.in));
        TaskList taskList = new TaskList();
        CommandParser commandParser = new CommandParser(command, taskList);

        command.showGreeting(banner);
        while (command.hasNextCommand()) {
            String userInput = command.getCommand();
            if (commandParser.execute(userInput)) {
                break;
            }
        }
    }
}
