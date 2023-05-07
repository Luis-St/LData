package net.luis.data.xml.io;

import net.luis.data.xml.XmlElement;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for classes that can be serialized to {@link XmlElement}
 *
 * @author Luis-St
 *
 */

public interface XmlSerializable {
	
	/**
	 * @return The serialized this object into {@link XmlElement}
	 */
	@NotNull XmlElement toXml();
}
