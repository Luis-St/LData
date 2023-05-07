package net.luis.data.json.io;

import net.luis.data.json.Json;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for classes that can be serialized to {@link Json}
 *
 * @author Luis-St
 */

public interface JsonSerializable<T extends Json> {
	
	/**
	 * @return The serialized this object into {@link Json}
	 */
	@NotNull T toJson();
}
