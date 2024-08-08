package bankingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bankingsystem.helper.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNoteFoundException.class)
	public ResponseEntity<ApiResponseMessage> handleResourceNotFoundException(ResourceNoteFoundException ex) {
		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage(ex.getMessage());
		message.setSuccess(false);
		message.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(RuntimeException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}


	// handle bad api exception
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handlerBadApiRequest(BadApiRequestException ex) {
		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage(ex.getMessage());
		message.setSuccess(false);
		message.setStatus(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
}
