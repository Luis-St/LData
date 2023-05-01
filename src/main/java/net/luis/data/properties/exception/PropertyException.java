package net.luis.data.properties.exception;

/**
 * Exception thrown when an error occurs which is related to properties
 *
 * @see RuntimeException
 *
 * @author Luis-St
 */

public class PropertyException extends RuntimeException {
	
	public PropertyException() {
		super();
	}
	
	public PropertyException(String message) {
		super(message);
	}
	
	public PropertyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PropertyException(Throwable cause) {
		super(cause);
	}
}
