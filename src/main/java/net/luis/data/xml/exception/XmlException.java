package net.luis.data.xml.exception;

/**
 * Exception thrown when an error occurs which is related to xml
 *
 * @see RuntimeException
 *
 * @author Luis-St
 */

public class XmlException extends RuntimeException {
	
	public XmlException() {
		super();
	}
	
	public XmlException(String message) {
		super(message);
	}
	
	public XmlException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public XmlException(Throwable cause) {
		super(cause);
	}
}
