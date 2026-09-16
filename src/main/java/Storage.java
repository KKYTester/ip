import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Saves tasks to a file on the hard disk.
 */
public class Storage {
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
}
