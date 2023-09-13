package G3.jio.exceptions;

public class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyExistsException() {
        super("User already exists!");
    }

    public AlreadyExistsException(String item) {
        super(item + " already exists!");
    }
}