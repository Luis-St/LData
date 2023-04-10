package net.luis.data.common.io;

public interface Writer {
	
	void flush();
	
	void close();
	
	default void flushAndClose() {
		this.flush();
		this.close();
	}
}
