import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/** Reads and writes tasks using ATHENA's persistent file format. */
final class Storage {
    private static final Path DATA_FILE_PATH = Path.of("data", "athena.txt");
    private static final Path TEMPORARY_FILE_PATH = Path.of("data", "athena.txt.tmp");

    private Storage() {
    }

    static List<Task> loadTasks() {
        if (Files.notExists(DATA_FILE_PATH)) {
            return List.of();
        }

        try {
            List<Task> loadedTasks = new ArrayList<>();
            List<String> lines = Files.readAllLines(DATA_FILE_PATH, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                if (!lines.get(i).isBlank()) {
                    loadedTasks.add(parseTask(lines.get(i), i + 1));
                }
            }
            return loadedTasks;
        } catch (IOException | SecurityException exception) {
            throw new AthenaException("Unable to load tasks from " + DATA_FILE_PATH, exception);
        }
    }

    static void saveTasks(Task[] tasks, int numberOfTasks) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            lines.add(tasks[i].toFileString());
        }

        try {
            Files.createDirectories(DATA_FILE_PATH.getParent());
            Files.write(TEMPORARY_FILE_PATH, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            replaceDataFile();
        } catch (IOException | SecurityException exception) {
            deleteTemporaryFile(exception);
            throw new AthenaException("Unable to save tasks to " + DATA_FILE_PATH, exception);
        }
    }

    private static void replaceDataFile() throws IOException {
        try {
            Files.move(TEMPORARY_FILE_PATH, DATA_FILE_PATH,
                    StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(TEMPORARY_FILE_PATH, DATA_FILE_PATH, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void deleteTemporaryFile(Exception originalException) {
        try {
            Files.deleteIfExists(TEMPORARY_FILE_PATH);
        } catch (IOException | SecurityException cleanupException) {
            originalException.addSuppressed(cleanupException);
        }
    }

    private static Task parseTask(String line, int lineNumber) {
        List<String> fields = splitFields(line);
        if (fields.size() < 3) {
            throw invalidData(lineNumber, "not enough fields");
        }

        boolean isDone = parseDoneState(fields.get(1), lineNumber);
        String description = requireText(fields.get(2), lineNumber, "description");
        Task task;
        switch (fields.get(0)) {
            case "T":
                requireFieldCount(fields, 3, lineNumber);
                task = new ToDo(description);
                break;
            case "D":
                requireFieldCount(fields, 4, lineNumber);
                task = new Deadline(description, requireText(fields.get(3), lineNumber, "deadline"));
                break;
            case "E":
                requireFieldCount(fields, 5, lineNumber);
                String from = requireText(fields.get(3), lineNumber, "start time");
                String to = requireText(fields.get(4), lineNumber, "end time");
                task = new Event(description, from, to);
                break;
            default:
                throw invalidData(lineNumber, "unknown task type");
        }
        task.setDone(isDone);
        return task;
    }

    private static List<String> splitFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean isEscaping = false;
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (isEscaping) {
                if (character != '\\' && character != '|') {
                    field.append('\\');
                }
                field.append(character);
                isEscaping = false;
            } else if (character == '\\') {
                isEscaping = true;
            } else if (character == '|') {
                fields.add(field.toString().trim());
                field.setLength(0);
            } else {
                field.append(character);
            }
        }
        if (isEscaping) {
            field.append('\\');
        }
        fields.add(field.toString().trim());
        return fields;
    }

    private static boolean parseDoneState(String value, int lineNumber) {
        if (value.equals("1")) {
            return true;
        }
        if (value.equals("0")) {
            return false;
        }
        throw invalidData(lineNumber, "completion state must be 0 or 1");
    }

    private static String requireText(String value, int lineNumber, String fieldName) {
        if (value.isBlank()) {
            throw invalidData(lineNumber, fieldName + " cannot be blank");
        }
        return value;
    }

    private static void requireFieldCount(List<String> fields, int expectedCount, int lineNumber) {
        if (fields.size() != expectedCount) {
            throw invalidData(lineNumber, "incorrect number of fields");
        }
    }

    private static AthenaException invalidData(int lineNumber, String reason) {
        return new AthenaException("Invalid data in " + DATA_FILE_PATH + " at line " + lineNumber + ": " + reason);
    }
}
