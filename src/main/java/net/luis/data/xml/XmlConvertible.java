package net.luis.data.xml;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface XmlConvertible {
	
	@NotNull XmlElement toXml();
}
