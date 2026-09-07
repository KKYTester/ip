/**
 * Represents a task without an attached date or time.
 */
public class ToDo extends Task {
    private static final String TODO_SYMBOL = "[T]";

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the to-do description with its type and completion icons.
     *
     * @return Display-ready task text.
     */
    @Override
    public String toString() {
        return TODO_SYMBOL + "[" + getStatusIcon() + "] " + description;
    }
}
