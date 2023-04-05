package net.luis.data.json.exception;

/**
 *
 * @author Luis-St
 *
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
