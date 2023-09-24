package G3.jio.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import G3.jio.exceptions.ErrorModel;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Ensure all inputs are valid and follows set constraints
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String defaultErrorMsg = ex.getAllErrors().get(0).getDefaultMessage();

        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Validation Error: " + defaultErrorMsg,
                ex.getBindingResult().toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorModel> handleAuthentication(AuthenticationException ex) {
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Invalid username or password.", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorModel> handleUserNotFound(UserNotFoundException ex) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "User Not Found.", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<ErrorModel> handleEventNotFound(EventNotFoundException ex) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "Event Not Found.", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
