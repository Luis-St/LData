package net.luis.data.xml.io;

import net.luis.data.xml.XmlElement;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface XmlSerializable {
	
	@NotNull XmlElement toXml();
}
