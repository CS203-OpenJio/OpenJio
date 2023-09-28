package G3.jio.exceptions;

public class NotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotExistException() {
        super("Item doesn't exist!");
    }

    public NotExistException(String item) {
        super(item + " doesn't exist!");
    }

    public NotExistException(String item, String company) {
        super(item + " doesn't exist for " + company + "!");
    }

}
