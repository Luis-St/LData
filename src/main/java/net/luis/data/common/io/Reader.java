package net.luis.data.common.io;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 *
 * @author Luis-St
 *
 */

public interface Reader<T> extends Iterator<T>, Iterable<T> {
	
	@Override
	default @NotNull Iterator<T> iterator() {
		return this;
	}
	
	void reset();
	
	void close();
}
