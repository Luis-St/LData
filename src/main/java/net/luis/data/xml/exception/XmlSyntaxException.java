package net.luis.data.xml.exception;

/**
 * Exception thrown when an error occurs which is related to xml syntax
 *
 * @see XmlException
 *
 * @author Luis-St
 */

public class XmlSyntaxException extends XmlException {
	
	public XmlSyntaxException() {
		super();
	}
	
	public XmlSyntaxException(String message) {
		super(message);
	}
	
	public XmlSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public XmlSyntaxException(Throwable cause) {
		super(cause);
	}
}
