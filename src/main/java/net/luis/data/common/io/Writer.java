package net.luis.data.common.io;

/**
 *
 * @author Luis-St
 *
 */

public interface Writer {
	
	void flush();
	
	void close();
	
	default void flushAndClose() {
		this.flush();
		this.close();
	}
}
