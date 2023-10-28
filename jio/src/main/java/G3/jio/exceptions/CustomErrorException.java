package G3.jio.exceptions;

public class CustomErrorException extends RuntimeException{
    
    public CustomErrorException() {
        super("Something went wrong?");
    }

    public CustomErrorException(String msg) {
        super(msg);
    }
}
