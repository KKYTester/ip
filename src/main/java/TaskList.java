import java.util.ArrayList;
import java.util.List;

/**
 * Stores tasks entered during the current application session.
 */
public class TaskList {
    private static final int EXPECTED_MAXIMUM_SIZE = 100;

    private final List<String> tasks = new ArrayList<>(EXPECTED_MAXIMUM_SIZE);

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task text to store.
     */
    public void add(String task) {
        tasks.add(task);
    }

    /**
     * Returns an unmodifiable snapshot of the stored tasks.
     *
     * @return Tasks in input order.
     */
    public List<String> getTasks() {
        return List.copyOf(tasks);
    }
}
