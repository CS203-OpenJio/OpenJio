package G3.jio.exceptions;

public class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException() {
        super();
    }

    public InvalidUserTypeException(String msg) {
        super("Invalid User Type: " + msg);
    }
}
