/**
 * Parses user input and executes the corresponding application action.
 */
public class CommandParser {
    private static final String UNMARK_COMMAND = "unmark";
    private static final String MARK_COMMAND = "mark";
    private static final String LIST_COMMAND = "list";
    private static final String EXIT_COMMAND = "bye";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DEADLINE_SEPARATOR = "\\s+/by\\s+";
    private static final String EVENT_START_SEPARATOR = "\\s+/from\\s+";
    private static final String EVENT_END_SEPARATOR = "\\s+/to\\s+";

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
        String trimmedInput = userInput.trim();
        String[] commandParts = trimmedInput.split("\\s+", 2);

        if (!isValidCommand(commandParts)) {
            command.showInvalidCommand();
            return false;
        }

        if (trimmedInput.equalsIgnoreCase(EXIT_COMMAND)) {
            command.showFarewell();
            return true;
        }

        if (trimmedInput.equalsIgnoreCase(LIST_COMMAND)) {
            command.showTaskList(taskList.getTasks());
            return false;
        }

        if (commandParts[0].equalsIgnoreCase(MARK_COMMAND)) {
            markTask(commandParts);
            return false;
        }

        if (commandParts[0].equalsIgnoreCase(UNMARK_COMMAND)) {
            unmarkTask(commandParts);
            return false;
        }

        Task newTask = createTask(commandParts);
        taskList.add(newTask);
        command.showTaskAdded(newTask, taskList.getTaskCount());
        return false;
    }

    /**
     * Checks whether a command has all arguments required to execute safely.
     *
     * @param commandParts Command word and its optional argument.
     * @return {@code true} if the command has a recognized and valid structure.
     */
    private boolean isValidCommand(String[] commandParts) {
        String commandWord = commandParts[0];

        if (commandWord.equalsIgnoreCase(EXIT_COMMAND)
                || commandWord.equalsIgnoreCase(LIST_COMMAND)) {
            return commandParts.length == 1;
        }

        if (commandWord.equalsIgnoreCase(MARK_COMMAND)
                || commandWord.equalsIgnoreCase(UNMARK_COMMAND)) {
            return true;
        }

        if (commandParts.length < 2) {
            return false;
        }

        String taskArguments = commandParts[1];
        if (commandWord.equalsIgnoreCase(TODO_COMMAND)) {
            return !taskArguments.isBlank();
        }

        if (commandWord.equalsIgnoreCase(DEADLINE_COMMAND)) {
            String[] deadlineParts = taskArguments.split(DEADLINE_SEPARATOR, 2);
            return hasNonBlankParts(deadlineParts, 2);
        }

        if (commandWord.equalsIgnoreCase(EVENT_COMMAND)) {
            String[] eventStartParts = taskArguments.split(EVENT_START_SEPARATOR, 2);
            if (!hasNonBlankParts(eventStartParts, 2)) {
                return false;
            }
            String[] eventEndParts = eventStartParts[1].split(EVENT_END_SEPARATOR, 2);
            return hasNonBlankParts(eventEndParts, 2);
        }

        return false;
    }

    /**
     * Reports whether an array has the expected number of non-blank parts.
     *
     * @param parts Text parts to check.
     * @param expectedLength Required number of parts.
     * @return {@code true} if every expected part contains text.
     */
    private boolean hasNonBlankParts(String[] parts, int expectedLength) {
        if (parts.length != expectedLength) {
            return false;
        }

        for (String part : parts) {
            if (part.isBlank()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a task from an already validated command.
     *
     * @param commandParts Command word and task arguments.
     * @return Task represented by the command.
     * @throws IllegalArgumentException If the command is not a task command.
     */
    private Task createTask(String[] commandParts) {
        String commandWord = commandParts[0];
        String taskArguments = commandParts[1];

        if (commandWord.equalsIgnoreCase(TODO_COMMAND)) {
            return new ToDo(taskArguments);
        }

        if (commandWord.equalsIgnoreCase(DEADLINE_COMMAND)) {
            String[] deadlineParts = taskArguments.split(DEADLINE_SEPARATOR, 2);
            return new Deadline(deadlineParts[0], deadlineParts[1]);
        }

        if (commandWord.equalsIgnoreCase(EVENT_COMMAND)) {
            String[] eventStartParts = taskArguments.split(EVENT_START_SEPARATOR, 2);
            String[] eventEndParts = eventStartParts[1].split(EVENT_END_SEPARATOR, 2);
            return new Event(eventStartParts[0], eventEndParts[0], eventEndParts[1]);
        }

        throw new IllegalArgumentException("Command does not create a task");
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
            Task markedTask = taskList.markAsDone(taskNumber);
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
            Task unmarkedTask = taskList.markAsNotDone(taskNumber);
            command.showTaskMarkedAsNotDone(unmarkedTask);
        } catch (NumberFormatException | IndexOutOfBoundsException exception) {
            command.showInvalidTaskNumber();
        }
    }
}
