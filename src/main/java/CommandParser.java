/**
 * Parses user input and executes the corresponding application action.
 */
public class CommandParser {
    private final Command command;
    private final TaskList taskList;

    /**
     * Creates a parser connected to the user interface and task storage.
     *
     * @param command Command input/output handler.
     * @param taskList Storage for the user's tasks.
     */
    public CommandParser(Command command, TaskList taskList) {
        this.command = command;
        this.taskList = taskList;
    }

    /**
     * Parses and executes one line of user input.
     *
     * @param userInput Text entered by the user.
     * @return {@code true} if the application should exit; otherwise {@code false}.
     */
    public boolean execute(String userInput) {
        String commandWord = userInput.trim();

        if (commandWord.equalsIgnoreCase("bye")) {
            command.showFarewell();
            return true;
        }

        if (commandWord.equalsIgnoreCase("list")) {
            command.showTaskList(taskList.getTasks());
            return false;
        }

        taskList.add(userInput);
        command.echoCommand(userInput);
        return false;
    }
}
