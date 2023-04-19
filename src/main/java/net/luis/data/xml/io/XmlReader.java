package net.luis.data.xml.io;

import com.google.common.collect.Lists;
import net.luis.data.common.io.AbstractReader;
import net.luis.data.common.util.Utils;
import net.luis.data.xml.attributes.XmlAttribute;
import net.luis.data.xml.elements.XmlElement;
import net.luis.utils.util.Range;
import net.luis.utils.util.SimpleEntry;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class XmlReader extends AbstractReader<XmlElement> {
	
	private static final Logger LOGGER = LogManager.getLogger(XmlReader.class);
	
	private XmlElement root;
	
	public XmlReader(File file) {
		super(file);
	}
	
	public XmlReader(String value) {
		super(value);
	}
	
	@Override
	protected void validate(String value) {
		//region Validation
		Objects.requireNonNull(value, "Xml string must not be null");
		if (Utils.countNoneQuoted(value, "<?xml") > 1) {
			throw new IllegalArgumentException("Xml string must not contain more than one xml declaration");
		}
		if (Utils.countNoneQuoted("?>", value) > 1) {
			throw new IllegalArgumentException("Xml string must not contain more than one xml declaration end");
		}
		int xmlOpen = Utils.countNoneQuoted("<", value);
		int xmlClose = Utils.countNoneQuoted(">", value);
		if (xmlOpen > xmlClose) {
			throw new IllegalArgumentException("Xml string must not contain more opening tags (" + xmlOpen + ") than closing tags (" + xmlClose + ")");
		} else if (xmlClose > xmlOpen) {
			throw new IllegalArgumentException("Xml string must not contain more closing tags (" + xmlClose + ") than opening tags (" + xmlOpen + ")");
		}
		//endregion
	}
	
	@Override
	protected String modify(String original) {
		//region Modification
		Objects.requireNonNull(original, "Xml string must not be null");
		String result = String.join("", original.split("(?=<!--)([\\s\\S]*?)-->"));
		String declaration = StringUtils.substringBetween(result, "<?", "?>");
		if (declaration == null) {
			Charset charset = Charset.defaultCharset();
			LOGGER.warn("No xml declaration found, using default charset {}", charset);
			return new String(result.getBytes(charset), charset);
		}
		if (!declaration.toLowerCase().startsWith("xml")) {
			throw new IllegalArgumentException("Xml string declaration must start with 'xml'");
		}
		String encoding = StringUtils.substringBetween(declaration, "encoding=\"", "\"");
		Charset charset;
		if (Charset.isSupported(encoding)) {
			charset = Charset.forName(encoding);
		} else {
			charset = Charset.defaultCharset();
			LOGGER.warn("Charset {} is not supported, using default charset instead {}", encoding, charset);
		}
		return new String(this.deleteWhitespace(result.replace("<?" + declaration + "?>", "")).getBytes(charset), charset);
		//endregion
	}
	
	@Override
	public boolean hasNext() {
		if (this.index == 0 || this.root == null) {
			return super.hasNext();
		} else if (super.hasNext()) {
			String value = this.remaining();
			if (value.startsWith("</")) {
				value = value.substring(2);
			}
			if (value.charAt(value.length() - 1) == '>') {
				value = value.substring(0, value.length() - 1);
			}
			return !value.strip().equals(this.root.getName());
		}
		return false;
	}
	
	@Override
	public XmlElement next() {
		if (this.index == 0) {
			int index = this.value().indexOf('>');
			if (index == -1) {
				throw new IllegalArgumentException("Xml string must contain a root tag");
			}
			if (this.value().charAt(index - 1) == '/') {
				XmlElement element = this.getRootTag(null);
				this.close();
				return element;
			}
			if (this.value().charAt(index + 1) != '<') {
				String rootValue = StringUtils.substringBetween(this.value(), ">", "<");
				if (rootValue == null) {
					throw new IllegalArgumentException("Xml root tag is not valid");
				}
				XmlElement element = this.getRootTag(rootValue);
				this.close();
				return element;
			}
		}
		XmlElement root = this.getRootTag(null);
		Map.Entry<Range, Range> entry = this.getNextElementRange(this.value(), this.index);
		root.addElement(parseElement(this.value(), entry.getKey(), entry.getValue()));
		this.index = entry.getValue().getMax();
		return root;
	}
	
	public XmlElement toXml() {
		XmlElement root = this.getRootTag(null);
		while (this.hasNext()) {
			XmlElement element = this.next();
			for (XmlElement child : element.getElements()) {
				root.addElement(child);
			}
		}
		return root;
	}
	
	//region Helper methods
	private @NotNull String deleteWhitespace(String value) {
		Objects.requireNonNull(value, "Xml string must not be null");
		StringBuilder result = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		boolean inTag = false;
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			boolean changed = false;
			if (c == '<' && !inTag) {
				inTag = true;
				changed = true;
			} else if (c == '>' && inTag) {
				inTag = false;
				changed = true;
			}
			if (changed && temp.length() > 0) {
				String string = temp.toString();
				if (!string.isBlank()) {
					result.append(string);
				} else {
					for (int j = i; j < value.length(); j++) {
						char d = value.charAt(j);
						if (d == '<') {
							if (j + 1 < value.length() && value.charAt(j + 1) == '/' && this.isInValueTag(value, i)) {
								result.append(string);
							}
							break;
						}
					}
				}
				temp = new StringBuilder();
			}
			if (inTag || changed) {
				result.append(c);
			} else {
				temp.append(c);
			}
		}
		return result.toString();
	}
	
	private boolean isInValueTag(String xml, int index) {
		Objects.requireNonNull(xml, "Xml string must not be null");
		String before = xml.substring(0, index);
		String nextTag = StringUtils.substringBetween(xml.substring(index), "<", ">").substring(1).strip();
		String lastTag = before.substring(before.lastIndexOf('<', index) + 1, before.lastIndexOf('>', index));
		if (lastTag.contains(" ")) {
			return Objects.equals(StringUtils.substringBefore(lastTag, " "), nextTag);
		} else {
			return Objects.equals(lastTag, nextTag);
		}
	}
	
	private @NotNull XmlElement getRootTag(String rootValue) {
		if (this.root != null && rootValue == null) {
			return this.root.copy();
		}
		if (this.value().isBlank()) {
			throw new IllegalArgumentException("Xml string must contain a root tag");
		}
		String root = StringUtils.stripToNull(StringUtils.substringBetween(this.value(), "<", ">"));
		if (root == null) {
			throw new IllegalArgumentException("Xml string must contain a root tag");
		}
		if (root.charAt(root.length() - 1) == '/') {
			root = root.substring(0, root.length() - 1).strip();
		}
		String name = this.parseStartTagValue(root);
		XmlElement element = rootValue == null ? new XmlElement(name) : new XmlElement(name, rootValue);
		if (root.contains(" ")) {
			this.getAttributes(StringUtils.substringAfter(root, " ")).forEach(element::addAttribute);
		}
		this.root = Objects.requireNonNull(element, "Xml root element must not be null");
		this.index = this.value().indexOf('>') + 1;
		return this.root.copy();
	}
	
	private @NotNull Map.Entry<Range, Range> getNextElementRange(String xml, int startIndex) {
		String value = Objects.requireNonNull(xml, "Xml string must not be null").substring(startIndex);
		String startTag = StringUtils.substringBetween(value, "<", ">");
		Range startRange = Range.of(startIndex, startIndex + startTag.length() + 2);
		if (startTag.charAt(startTag.length() - 1) == '/') {
			return new SimpleEntry<>(startRange, startRange);
		}
		return new SimpleEntry<>(startRange, getEndTagRange(xml, startRange, startRange.getMax()));
	}
	
	private @NotNull Range getEndTagRange(String xml, Range startRange, int startIndex) {
		String value = Objects.requireNonNull(xml, "Xml string must not be null").substring(startIndex);
		String nextEndTag = StringUtils.substringBetween(value, "</", ">");
		int start = startIndex + value.indexOf("</");
		if (this.parseStartTagName(xml, startRange).equals(nextEndTag.strip())) {
			return Range.of(start, start + nextEndTag.length() + 3);
		} else {
			return getEndTagRange(xml, startRange, start + nextEndTag.length() + 3);
		}
	}
	
	private @NotNull XmlElement parseElement(String xml, Range start, Range end) {
		Objects.requireNonNull(start, "Start range of element must not be null");
		Objects.requireNonNull(end, "End range of element must not be null");
		String name = this.parseStartTagName(xml, start);
		String value = start.equals(end) ? "" : StringUtils.substring(xml, start.getMax(), end.getMin());
		List<XmlAttribute> attributes = parseAttributes(xml, name, start);
		if (start.equals(end)) {
			XmlElement element = new XmlElement(name);
			attributes.forEach(element::addAttribute);
			return element;
		}
		if (value.isEmpty() || value.charAt(0) != '<') {
			XmlElement element = new XmlElement(name, value);
			attributes.forEach(element::addAttribute);
			return element;
		}
		XmlElement element = new XmlElement(name);
		attributes.forEach(element::addAttribute);
		int index = 0;
		while (index < value.length()) {
			Map.Entry<Range, Range> entry = this.getNextElementRange(value, index);
			element.addElement(parseElement(value, entry.getKey(), entry.getValue()));
			index = entry.getValue().getMax();
		}
		return element;
	}
	
	private @NotNull String parseStartTagName(String xml, Range range) {
		Objects.requireNonNull(range, "Range of start tag must not be null");
		return parseStartTagValue(StringUtils.substring(xml, range.getMin() + 1, range.getMax() - 1));
	}
	
	private @NotNull String parseStartTagValue(String startTag) {
		if (!StringUtils.contains(startTag, " ")) {
			return Objects.requireNonNull(startTag, "Xml start tag must not be null");
		}
		return StringUtils.substringBefore(startTag, " ");
	}
	
	private @NotNull List<XmlAttribute> parseAttributes(String xml, String name, Range range) {
		Objects.requireNonNull(range, "Range of start tag must not be null");
		String value = StringUtils.stripToEmpty(StringUtils.substring(xml, range.getMin() + 1 + StringUtils.stripToEmpty(name).length(), range.getMax() - 1));
		if (value.isBlank()) {
			return Lists.newArrayList();
		}
		if (value.charAt(value.length() - 1) == '/') {
			value = value.substring(0, value.length() - 1).strip();
		}
		return getAttributes(value);
	}
	
	private @NotNull List<XmlAttribute> getAttributes(String value) {
		List<XmlAttribute> attributes = Lists.newArrayList();
		for (String part : Utils.splitNoneQuoted(StringUtils.stripToEmpty(value), " ")) {
			if (part.isBlank()) {
				continue;
			}
			String[] split = part.split("=");
			if (split.length != 2) {
				throw new IllegalArgumentException("Xml attribute '" + part + "' is not valid");
			}
			String attributeValue = split[1];
			if (attributeValue.charAt(0) != '"' && attributeValue.charAt(attributeValue.length() - 1) != '"') {
				if (attributeValue.charAt(0) != '\'' && attributeValue.charAt(attributeValue.length() - 1) != '\'') {
					throw new IllegalArgumentException("Xml attribute '" + part + "' is not valid, value must be surrounded by quotes");
				}
			}
			attributes.add(new XmlAttribute(split[0], attributeValue.substring(1, attributeValue.length() - 1)));
		}
		return attributes;
	}
	//endregion
}
