package net.luis.data.json;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface JsonConvertible<T extends Json> {
	
	@NotNull T toJson();
}
