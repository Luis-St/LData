package net.luis.data.json.io;

import net.luis.data.json.Json;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface JsonSerializable<T extends Json> {
	
	@NotNull T toJson();
}
