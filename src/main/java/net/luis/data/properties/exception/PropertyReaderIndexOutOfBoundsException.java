package net.luis.data.properties.exception;

import net.luis.data.properties.io.PropertyReader;

/**
 * Exception thrown by {@link PropertyReader} when the reader is at the end of the property file
 *
 * @see IndexOutOfBoundsException
 *
 * @author Luis-St
 */

public class PropertyReaderIndexOutOfBoundsException extends IndexOutOfBoundsException {
	
	public PropertyReaderIndexOutOfBoundsException() {
		super();
	}
	
	public PropertyReaderIndexOutOfBoundsException(String message) {
		super(message);
	}
	
	public PropertyReaderIndexOutOfBoundsException(int index) {
		super(index);
	}
	
	public PropertyReaderIndexOutOfBoundsException(long index) {
		super(index);
	}
}
