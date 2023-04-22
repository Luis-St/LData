package net.luis.data.xml;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ApiStatus.Internal
class XmlHelper {
	
	static @NotNull String validateXmlEscape(String xml) {
		Objects.requireNonNull(xml, "The xml string must not be null");
		if (xml.contains("&")) {
			throw new IllegalArgumentException("The xml string contains an invalid xml character: &");
		}
		if (xml.contains("<")) {
			throw new IllegalArgumentException("The xml string contains an invalid xml character: <");
		}
		if (xml.contains(">")) {
			throw new IllegalArgumentException("The xml string contains an invalid xml character: >");
		}
		if (xml.contains("\"")) {
			throw new IllegalArgumentException("The xml string contains an invalid xml character: \"");
		}
		if (xml.contains("'")) {
			throw new IllegalArgumentException("The xml string contains an invalid xml character: '");
		}
		return xml;
	}
	
}
