package net.luis.data.properties.exception;

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
