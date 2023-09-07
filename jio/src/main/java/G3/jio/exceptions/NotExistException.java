package G3.jio.exceptions;

public class NotExistException extends RuntimeException {

    public NotExistException() {
        super("doesn't exist");
    }

    public NotExistException(String item) {
        super(item + " doesn't exist");
    }
}
