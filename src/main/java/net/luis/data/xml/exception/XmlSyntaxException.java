package net.luis.data.xml.exception;

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
