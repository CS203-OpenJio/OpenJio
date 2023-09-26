package G3.jio.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import G3.jio.exceptions.ErrorModel;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.FailedRegistrationException;
import G3.jio.exceptions.NotExistException;
import G3.jio.exceptions.UserNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Ensure all inputs are valid and follows set constraints
    /**
	 * Customize the handling of {@link MethodArgumentNotValidException}.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception to handle
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} for the response to use, possibly
	 * {@code null} when the response is already committed
	 */
    @Override
	@Nullable
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String defaultErrorMsg = ex.getAllErrors().get(0).getDefaultMessage();

        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Validation Error: " + defaultErrorMsg,
                ex.getBindingResult().toString());
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(FailedRegistrationException.class)
    private ResponseEntity<ErrorModel> handleFailedRegistration(FailedRegistrationException ex) {
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Registration failed.", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistException.class)
    private ResponseEntity<ErrorModel> handleNotExist(NotExistException ex) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "Not Found.", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
