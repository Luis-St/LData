package net.luis.data.json;

import org.jetbrains.annotations.NotNull;

public interface JsonConvertible<T extends Json> {
	
	@NotNull T toJson();
}
