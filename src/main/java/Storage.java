import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves tasks to a file on the hard disk.
 */
public class Storage {
    private static final String FIELD_SEPARATOR = "\\s*\\|\\s*";
    private static final int TODO_FIELD_COUNT = 3;
    private static final int DEADLINE_FIELD_COUNT = 4;
    private static final int EVENT_FIELD_COUNT = 5;

    private final Path filePath;

    /**
     * Creates storage that writes to the given file path.
     *
     * @param filePath Path of the save file.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file, or returns an empty list when the file does not exist.
     *
     * @return Tasks stored in the save file, in file order.
     * @throws IOException If the save file cannot be read or contains invalid task data.
     */
    public List<Task> load() throws IOException {
        if (!Files.exists(filePath)) {
            return List.of();
        }

        List<String> taskData = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < taskData.size(); i++) {
            tasks.add(parseTask(taskData.get(i), i + 1));
        }
        return tasks;
    }

    /**
     * Replaces the save file with the current tasks, creating its directory when needed.
     *
     * @param tasks Tasks to save in list order.
     * @throws IOException If the directory or save file cannot be written.
     */
    public void save(List<Task> tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        List<String> taskData = tasks.stream()
                .map(Task::toDataString)
                .toList();
        Files.write(filePath, taskData, StandardCharsets.UTF_8);
    }

    /**
     * Converts one save-file line into a task.
     *
     * @param taskData Save-file line to parse.
     * @param lineNumber One-based line number used in error messages.
     * @return Task represented by the line.
     * @throws IOException If the line does not use a supported save-file format.
     */
    private Task parseTask(String taskData, int lineNumber) throws IOException {
        String[] fields = taskData.split(FIELD_SEPARATOR, -1);

        Task task;
        switch (fields[0]) {
            case "T":
                if (fields.length != TODO_FIELD_COUNT) {
                    throw createInvalidDataException(lineNumber);
                }
                task = new ToDo(fields[2]);
                break;
            case "D":
                if (fields.length != DEADLINE_FIELD_COUNT) {
                    throw createInvalidDataException(lineNumber);
                }
                task = new Deadline(fields[2], fields[3]);
                break;
            case "E":
                if (fields.length != EVENT_FIELD_COUNT) {
                    throw createInvalidDataException(lineNumber);
                }
                task = new Event(fields[2], fields[3], fields[4]);
                break;
            default:
                throw createInvalidDataException(lineNumber);
        }

        if (fields[1].equals("1")) {
            task.markAsDone();
        } else if (!fields[1].equals("0")) {
            throw createInvalidDataException(lineNumber);
        }
        return task;
    }

    /**
     * Creates a consistent exception for malformed save-file data.
     *
     * @param lineNumber One-based number of the malformed line.
     * @return Exception describing the invalid line.
     */
    private IOException createInvalidDataException(int lineNumber) {
        return new IOException("Invalid task data on line " + lineNumber + " of " + filePath);
    }
}
