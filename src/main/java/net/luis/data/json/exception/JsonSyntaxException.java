package net.luis.data.json.exception;

/**
 * Exception thrown when an error occurs which is related to json syntax
 *
 * @author Luis-St
 *
 * @see JsonException
 */

public class JsonSyntaxException extends JsonException {
	
	public JsonSyntaxException() {
		super();
	}
	
	public JsonSyntaxException(String message) {
		super(message);
	}
	
	public JsonSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public JsonSyntaxException(Throwable cause) {
		super(cause);
	}
}
