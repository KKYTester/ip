import java.util.ArrayList;
import java.util.List;

/**
 * Stores tasks entered during the current application session.
 */
public class TaskList {
    private static final int INITIAL_CAPACITY = 100;

    private final List<Task> tasks = new ArrayList<>(INITIAL_CAPACITY);

    /**
     * Adds a task to the end of the list.
     *
     * @param newTask Task to store.
     */
    public void add(Task newTask) {
        tasks.add(newTask);
    }

    /**
     * Returns the number of stored tasks.
     *
     * @return Number of tasks in the list.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Marks the task at the given one-based position as done.
     *
     * @param taskNumber One-based task number shown by the {@code list} command.
     * @return Task that was marked as done.
     * @throws IndexOutOfBoundsException If the task number is not in the list.
     */
    public Task markAsDone(int taskNumber) {
        Task task = getTask(taskNumber);
        task.markAsDone();
        return task;
    }

    /**
     * Marks the task at the given one-based position as not done.
     *
     * @param taskNumber One-based task number shown by the {@code list} command.
     * @return Task that was marked as not done.
     * @throws IndexOutOfBoundsException If the task number is not in the list.
     */
    public Task markAsNotDone(int taskNumber) {
        Task task = getTask(taskNumber);
        task.markAsNotDone();
        return task;
    }

    /**
     * Returns an unmodifiable snapshot of the stored tasks.
     *
     * @return Tasks in input order.
     */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Returns the task at the given one-based position.
     *
     * @param taskNumber One-based task number shown by the {@code list} command.
     * @return Task at the requested position.
     * @throws IndexOutOfBoundsException If the task number is not in the list.
     */
    private Task getTask(int taskNumber) {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number is outside the list");
        }
        return tasks.get(taskIndex);
    }
}
