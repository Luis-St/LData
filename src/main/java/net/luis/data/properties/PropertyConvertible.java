package net.luis.data.properties;

import org.jetbrains.annotations.NotNull;

public interface PropertyConvertible<T extends Property> {
	
	@NotNull T toProperty();
}
