package G3.jio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class EventNotFoundException extends RuntimeException {
    // idk what is this so i comment it out -J
    private static final long serialVersionUID = 1L;

    public EventNotFoundException(Long eventId) {
        super("Could not find Event " + eventId);
    }

}
