package net.luis.data.common.io;

/**
 *
 * @author Luis-St
 *
 */

public interface Writer {
	
	/**
	 * Flushes the writer
	 */
	void flush();
	
	/**
	 * Closes the writer
	 */
	void close();
	
	/**
	 * Flushes and closes the writer
	 */
	default void flushAndClose() {
		this.flush();
		this.close();
	}
}
