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
            command.showTaskList(taskList.getTasks(), taskList.getCompletionStatuses());
            return false;
        }

        String[] commandParts = commandWord.split("\\s+", 2);
        if (commandParts[0].equalsIgnoreCase("mark")) {
            markTask(commandParts);
            return false;
        }

        if (commandParts[0].equalsIgnoreCase("unmark")) {
            unmarkTask(commandParts);
            return false;
        }

        taskList.add(userInput);
        command.echoCommand(userInput);
        return false;
    }

    /**
     * Marks the task selected by a {@code mark TASK_NUMBER} command.
     *
     * @param commandParts Command word and its optional argument.
     */
    private void markTask(String[] commandParts) {
        if (commandParts.length < 2) {
            command.showInvalidTaskNumber();
            return;
        }

        try {
            int taskNumber = Integer.parseInt(commandParts[1]);
            String markedTask = taskList.markAsDone(taskNumber);
            command.showTaskMarkedAsDone(markedTask);
        } catch (NumberFormatException | IndexOutOfBoundsException exception) {
            command.showInvalidTaskNumber();
        }
    }

    /**
     * Reverses completion for the task selected by an {@code unmark TASK_NUMBER} command.
     *
     * @param commandParts Command word and its optional argument.
     */
    private void unmarkTask(String[] commandParts) {
        if (commandParts.length < 2) {
            command.showInvalidTaskNumber();
            return;
        }

        try {
            int taskNumber = Integer.parseInt(commandParts[1]);
            String unmarkedTask = taskList.markAsNotDone(taskNumber);
            command.showTaskMarkedAsNotDone(unmarkedTask);
        } catch (NumberFormatException | IndexOutOfBoundsException exception) {
            command.showInvalidTaskNumber();
        }
    }
}
