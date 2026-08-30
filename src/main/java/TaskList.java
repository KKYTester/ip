import java.util.ArrayList;
import java.util.List;

/**
 * Stores tasks entered during the current application session.
 */
public class TaskList {
    private static final int EXPECTED_MAXIMUM_SIZE = 100;

    private final List<String> tasks = new ArrayList<>(EXPECTED_MAXIMUM_SIZE);
    private final List<Boolean> completionStatuses = new ArrayList<>(EXPECTED_MAXIMUM_SIZE);

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task text to store.
     */
    public void add(String task) {
        tasks.add(task);
        completionStatuses.add(false);
    }

    /**
     * Marks the task at the given one-based position as done.
     *
     * @param taskNumber One-based task number shown by the {@code list} command.
     * @return Text of the task that was marked as done.
     * @throws IndexOutOfBoundsException If the task number is not in the list.
     */
    public String markAsDone(int taskNumber) {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number is outside the list");
        }
        completionStatuses.set(taskIndex, true);
        return tasks.get(taskIndex);
    }

    /**
     * Returns an unmodifiable snapshot of the stored tasks.
     *
     * @return Tasks in input order.
     */
    public List<String> getTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Returns an unmodifiable snapshot of the completion state for each task.
     *
     * @return Completion states in the same order as {@link #getTasks()}.
     */
    public List<Boolean> getCompletionStatuses() {
        return List.copyOf(completionStatuses);
    }
}
