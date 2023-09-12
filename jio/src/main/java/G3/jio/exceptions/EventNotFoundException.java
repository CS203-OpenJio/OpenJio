package G3.jio.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("Event Not Found");
    }

    public EventNotFoundException(Long Id) {
        super("Event Not Found:" + Id);
    }

        public EventNotFoundException(String name) {
        super("Event Not Found:" + name);
    }
}
