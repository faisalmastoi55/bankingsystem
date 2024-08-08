package bankingsystem.helper;

import org.springframework.http.HttpStatus;

public class ApiResponseMessage {

	private String message;
	private boolean success;
	private HttpStatus status;
	private Object dto;

	public ApiResponseMessage() {

	}

	public ApiResponseMessage(String message, boolean success, HttpStatus status, Object dto) {

		this.message = message;
		this.success = success;
		this.status = status;
		this.dto = dto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Object getDto() {
		return dto;
	}

	public void setDto(Object dto) {
		this.dto = dto;
	}

}
