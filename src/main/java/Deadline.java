/**
 * Represents a task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    private static final String DEADLINE_SYMBOL = "[D]";

    private final String by;

    /**
     * Creates an incomplete deadline with the given description and due date.
     *
     * @param description Description of the task.
     * @param by Date or time by which the task must be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline with its type, completion icon, and due date.
     *
     * @return Display-ready task text.
     */
    @Override
    public String toString() {
        return DEADLINE_SYMBOL + "[" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }
}
