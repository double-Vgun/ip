package parser;

/** Represents an error caused by an invalid command word. */
public class ParserException extends RuntimeException {
    /** Creates a parser exception with the given error message. */
    public ParserException(String message) {
        super(message);
    }
}
