package net.luis.data.json.exception;

/**
 * Exception thrown when an error occurs which is related to json
 *
 * @see RuntimeException
 *
 * @author Luis-St
 */

public class JsonException extends RuntimeException {
	
	public JsonException() {
		super();
	}
	
	public JsonException(String message) {
		super(message);
	}
	
	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public JsonException(Throwable cause) {
		super(cause);
	}
}
