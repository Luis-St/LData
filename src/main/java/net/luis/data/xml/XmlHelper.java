package net.luis.data.xml;

import java.util.Objects;

public class XmlHelper {
	
	public static String validateXmlEscape(String xml) {
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
