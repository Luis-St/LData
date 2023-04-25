package net.luis.data.properties.io;

import net.luis.data.properties.Property;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface PropertySerializable<T extends Property> {
	
	@NotNull T toProperty();
}
