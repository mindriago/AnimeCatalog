package exception;

// Wraps low-level IO errors (Low Coupling / Protected Variations)
public class AnimePersistenceException extends RuntimeException {
    public AnimePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}