package net.luis.data.json.exception;

import net.luis.data.json.io.JsonReader;

/**
 * Exception thrown by {@link JsonReader} when the reader is at the end of the json file
 *
 * @see IndexOutOfBoundsException
 *
 * @author Luis-St
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
