/** Represents an error caused by invalid ATHENA input. */
public class AthenaException extends RuntimeException {
    /** Creates an exception with the given error message. */
    public AthenaException(String message) {
        super(message);
    }
}
