package net.luis.data.xml.io;

import com.google.common.collect.Lists;
import net.luis.data.common.io.Reader;
import net.luis.data.xml.XmlAttribute;
import net.luis.data.xml.XmlConvertible;
import net.luis.data.xml.XmlElement;
import net.luis.data.xml.exception.XmlException;
import net.luis.data.xml.exception.XmlReaderIndexOutOfBoundsException;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class XmlReader implements Reader<XmlElement>, XmlConvertible {
	
	private final String name;
	private final List<XmlAttribute> attributes;
	private final int type;
	private final List<Node> nodes;
	private int index;
	
	public XmlReader(String path) {
		this(new File(path));
	}
	
	public XmlReader(File file) {
		try {
			//region XPath creation
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("*").evaluate(document, XPathConstants.NODESET);
			//endregion
			//region Validation
			if (nodes.getLength() == 0) {
				throw new XmlException("The xml file is empty");
			} else if (nodes.getLength() > 1) {
				throw new XmlException("The xml file contains more than one root element");
			} else if (nodes.item(0).getNodeType() != Node.ELEMENT_NODE && nodes.item(0).getNodeType() != Node.TEXT_NODE) {
				throw new XmlException("Invalid xml node type of the root node: " + nodes.item(0).getNodeType());
			}
			Node node = nodes.item(0);
			//endregion
			this.name = node.getNodeName();
			this.attributes = this.getXmlAttributes(node);
			this.type = node.getNodeType();
			List<Node> nodesList = Lists.newArrayList();
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					nodesList.add(childNode);
				}
			}
			this.nodes = List.copyOf(nodesList);
		} catch (Exception e) {
			throw new XmlException("Error while reading the xml file", e);
		}
	}
	
	@Override
	public boolean hasNext() {
		if (this.type == Node.TEXT_NODE) {
			return this.index == 0;
		} else if (this.type == Node.ELEMENT_NODE) {
			return !this.nodes.isEmpty() && this.index < this.nodes.size();
		}
		throw new XmlException("Invalid xml node type of the root node: " + this.type);
	}
	
	@Override
	public XmlElement next() {
		//region Validation
		if (!this.hasNext()) {
			throw new XmlReaderIndexOutOfBoundsException("Xml reader is at the end of the file");
		}
		//endregion
		if (this.type == Node.TEXT_NODE) {
			this.close();
			return this.getRootElement();
		} else if (this.type == Node.ELEMENT_NODE) {
			XmlElement rootElement = this.getRootElement();
			XmlElement element = this.getXmlElement(this.nodes.get(this.index));
			this.getXmlAttributes(this.nodes.get(this.index)).forEach(element::addAttribute);
			rootElement.addElement(element);
			this.index++;
			return rootElement;
		}
		throw new XmlException("Invalid xml node type of the root node: " + this.type);
	}
	
	@Override
	public @NotNull XmlElement toXml() {
		XmlElement rootElement = this.getRootElement();
		while (this.hasNext()) {
			XmlElement element = this.next();
			for (XmlElement childElement : element.getElements()) {
				rootElement.addElement(childElement);
			}
		}
		return rootElement;
	}
	
	//region Helper methods
	private @NotNull XmlElement getRootElement() {
		XmlElement element = new XmlElement(this.name);
		this.attributes.forEach(element::addAttribute);
		return element;
	}
	
	private @NotNull XmlElement getXmlElement(Node node) {
		return this.getXmlElement(node, node.getChildNodes());
	}
	
	private @NotNull XmlElement getXmlElement(Node parentNode, NodeList childNodes) {
		Objects.requireNonNull(childNodes, "Child nodes must not be null");
		if (childNodes.getLength() == 0) {
			XmlElement element = new XmlElement(parentNode.getNodeName());
			getXmlAttributes(parentNode).forEach(element::addAttribute);
			return element;
		} else if (childNodes.getLength() == 1 && childNodes.item(0).getNodeType() == Node.TEXT_NODE) {
			XmlElement element = new XmlElement(parentNode.getNodeName(), childNodes.item(0).getTextContent());
			getXmlAttributes(childNodes.item(0)).forEach(element::addAttribute);
			return element;
		}
		XmlElement element = new XmlElement(parentNode.getNodeName());
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				XmlElement childElement = getXmlElement(node, node.getChildNodes());
				getXmlAttributes(node).forEach(childElement::addAttribute);
				element.addElement(childElement);
			}
		}
		return element;
	}
	
	private @NotNull List<XmlAttribute> getXmlAttributes(Node node) {
		Objects.requireNonNull(node, "Node must not be null");
		List<XmlAttribute> xmlAttributes = Lists.newArrayList();
		if (node.hasAttributes()) {
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				xmlAttributes.add(new XmlAttribute(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getTextContent()));
			}
		}
		return xmlAttributes;
	}
	//endregion
	
	@Override
	public void reset() {
		this.index = 0;
	}
	
	@Override
	public void close() {
		this.index = this.type == Node.TEXT_NODE ? 1 : this.nodes.size();
	}
}
