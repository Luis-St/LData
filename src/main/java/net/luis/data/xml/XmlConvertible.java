package net.luis.data.xml;

import org.jetbrains.annotations.NotNull;

public interface XmlConvertible {
	
	@NotNull XmlElement toXml();
}
