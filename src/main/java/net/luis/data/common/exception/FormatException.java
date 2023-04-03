package net.luis.data.common.exception;

/**
 *
 * @author Luis-St
 *
 */

public class FormatException extends RuntimeException {
	
	public FormatException() {
		super();
	}
	
	public FormatException(String message) {
		super(message);
	}
	
	public FormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FormatException(Throwable cause) {
		super(cause);
	}
}
