/**
 * Represents a task that occurs between specified start and end times.
 */
public class Event extends Task {
    static final int DATA_FIELD_COUNT = 5;

    private static final String EVENT_SYMBOL = "[E]";
    private static final String EVENT_START_SEPARATOR = "\\s+/from\\s+";
    private static final String EVENT_END_SEPARATOR = "\\s+/to\\s+";
    private static final int EXPECTED_EVENT_PART_COUNT = 3;
    private static final int EXPECTED_SEPARATOR_PART_COUNT = 2;

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
     * Checks whether input contains a description and non-blank {@code /from} and {@code /to} values.
     *
     * @param taskInput Raw input following the event command word.
     * @return {@code true} if the input can be used to create an event.
     */
    public static boolean isValidInput(String taskInput) {
        String[] eventParts = splitInput(taskInput);
        return hasNonBlankParts(eventParts, EXPECTED_EVENT_PART_COUNT);
    }

    /**
     * Creates an event from input that has already been validated.
     *
     * @param taskInput Raw input following the event command word.
     * @return Event represented by the input.
     * @throws IllegalArgumentException If the input is not valid for an event.
     */
    static Event createFromInput(String taskInput) {
        String[] eventParts = splitInput(taskInput);
        if (!hasNonBlankParts(eventParts, EXPECTED_EVENT_PART_COUNT)) {
            throw new IllegalArgumentException(
                "Invalid event input. Format: event [description] /from [date] /to [date]");
        }
        return new Event(eventParts[0], eventParts[1], eventParts[2]);
    }

    /**
     * Returns this event in the format used for persistent storage.
     *
     * @return Save-file representation of this event.
     */
    @Override
    public String toDataString() {
        return "E | " + getDataStatus() + " | " + description + " | " + from + " | " + to;
    }

    /**
     * Splits raw event input into its description, start, and end values.
     *
     * @param taskInput Raw input following the event command word.
     * @return Event parts, or an empty array if the input is malformed.
     */
    private static String[] splitInput(String taskInput) {
        if (taskInput == null) {
            return new String[0];
        }

        String[] eventStartParts = taskInput.split(EVENT_START_SEPARATOR, EXPECTED_SEPARATOR_PART_COUNT);
        if (eventStartParts.length != EXPECTED_SEPARATOR_PART_COUNT) {
            return new String[0];
        }

        String[] eventEndParts = eventStartParts[1].split(EVENT_END_SEPARATOR, EXPECTED_SEPARATOR_PART_COUNT);
        if (eventEndParts.length != EXPECTED_SEPARATOR_PART_COUNT) {
            return new String[0];
        }

        return new String[]{eventStartParts[0], eventEndParts[0], eventEndParts[1]};
    }

    /**
     * Reports whether an array has the expected number of non-blank parts.
     *
     * @param parts Text parts to check.
     * @param expectedPartCount Required number of parts.
     * @return {@code true} if every expected part contains text.
     */
    private static boolean hasNonBlankParts(String[] parts, int expectedPartCount) {
        if (parts.length != expectedPartCount) {
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
