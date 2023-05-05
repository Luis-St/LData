package net.luis.data.internal.io;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * A reader that reads data from a file
 *
 * @see Iterator
 * @see Iterable
 *
 * @author Luis-St
 */

public interface Reader<T> extends Iterator<T>, Iterable<T> {
	
	@Override
	default @NotNull Iterator<T> iterator() {
		return this;
	}
	
	/**
	 * Resets the reader to the beginning of the file
	 */
	void reset();
	
	/**
	 * Closes the reader by setting the reader to the end of the file
	 */
	void close();
}
