package net.luis.data.xml.exception;

public class XmlReaderIndexOutOfBoundsException extends IndexOutOfBoundsException {
	
	public XmlReaderIndexOutOfBoundsException() {
		super();
	}
	
	public XmlReaderIndexOutOfBoundsException(String message) {
		super(message);
	}
	
	public XmlReaderIndexOutOfBoundsException(int index) {
		super(index);
	}
	
	public XmlReaderIndexOutOfBoundsException(long index) {
		super(index);
	}
}
