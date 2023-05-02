package net.luis.data.xml.exception;

import net.luis.data.xml.io.XmlReader;

/**
 * Exception thrown by {@link XmlReader} when the reader is at the end of the xml file
 *
 * @see IndexOutOfBoundsException
 *
 * @author Luis-St
 */

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
