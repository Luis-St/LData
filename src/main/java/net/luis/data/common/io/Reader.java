package net.luis.data.common.io;

import net.luis.data.json.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface Reader<T> extends Iterator<T>, Iterable<T> {
	
	@Override
	default @NotNull Iterator<T> iterator() {
		return this;
	}
}