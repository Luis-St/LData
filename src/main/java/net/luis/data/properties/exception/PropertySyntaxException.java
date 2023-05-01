package net.luis.data.properties.exception;

/**
 * Exception thrown when an error occurs which is related to properties syntax
 *
 * @see PropertyException
 *
 * @author Luis-St
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
