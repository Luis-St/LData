package net.luis.data.common.io;

import java.io.File;

public interface Writable<T extends Writer> {
	
	void write(File file);
	
	void write(T writer);
}
