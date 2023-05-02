package net.luis.data.xml;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Helper class for xml
 *
 * @author Luis-St
 */

@ApiStatus.Internal
class XmlHelper {
	
	/**
	 * Validate the xml string for invalid xml characters
	 * @param xml The xml string to validate
	 * @return The xml string
	 * @throws NullPointerException If the xml string is null
	 * @throws IllegalArgumentException If the xml string contains an invalid xml character (&, <, >, ", ')
	 */
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
