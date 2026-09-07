/**
 * Represents a task that occurs between specified start and end times.
 */
public class Event extends Task {
    private static final String EVENT_SYMBOL = "[E]";

    private final String from;
    private final String to;

    /**
     * Creates an incomplete event with the given description and time range.
     *
     * @param description Description of the task.
     * @param from Date or time at which the event starts.
     * @param to Date or time at which the event ends.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event with its type, completion icon, and time range.
     *
     * @return Display-ready task text.
     */
    @Override
    public String toString() {
        return EVENT_SYMBOL + "[" + getStatusIcon() + "] " + description
                + " (from: " + from + " to: " + to + ")";
    }
}
