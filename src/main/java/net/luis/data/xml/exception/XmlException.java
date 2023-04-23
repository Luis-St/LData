package net.luis.data.xml.exception;

/**
 *
 * @author Luis-St
 *
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
