package net.luis.data.json.exception;

/**
 *
 * @author Luis-St
 *
 */

public class JsonReaderIndexOutOfBoundsException extends IndexOutOfBoundsException {
	
	public JsonReaderIndexOutOfBoundsException() {
		super();
	}
	
	public JsonReaderIndexOutOfBoundsException(String message) {
		super(message);
	}
	
	public JsonReaderIndexOutOfBoundsException(int index) {
		super(index);
	}
	
	public JsonReaderIndexOutOfBoundsException(long index) {
		super(index);
	}
}
