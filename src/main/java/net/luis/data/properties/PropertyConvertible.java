package net.luis.data.properties;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface PropertyConvertible<T extends Property> {
	
	@NotNull T toProperty();
}
