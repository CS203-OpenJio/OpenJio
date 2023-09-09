package G3.jio.exceptions;

public class EventNotFoundException extends RuntimeException{
    
    public EventNotFoundException() {
        super("Event Not Found");
    }

    public EventNotFoundException(String msg) {
        super("Event Not Found:" + msg);
    }
}
