package G3.jio.exceptions;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException() {
        super("User Not Found");
    }

    public UserNotFoundException(String msg) {
        super("User Not Found: " + msg);
    }
}
