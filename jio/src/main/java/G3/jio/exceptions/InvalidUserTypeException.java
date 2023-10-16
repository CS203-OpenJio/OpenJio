package G3.jio.exceptions;

public class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException() {
        super("User Type can only be 'O' or 'S'.");
    }
}
