package net.luis.data.properties.exception;

/**
 *
 * @author Luis-St
 *
 */

public class PropertySyntaxException extends PropertyException {
	
	public PropertySyntaxException() {
		super();
	}
	
	public PropertySyntaxException(String message) {
		super(message);
	}
	
	public PropertySyntaxException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PropertySyntaxException(Throwable cause) {
		super(cause);
	}
}
