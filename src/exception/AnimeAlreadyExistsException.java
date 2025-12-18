package exception;

// Custom exception for Domain Logic rules
public class AnimeAlreadyExistsException extends RuntimeException {
    public AnimeAlreadyExistsException(String message) {
        super(message);
    }
}