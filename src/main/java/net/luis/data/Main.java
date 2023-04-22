package net.luis.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Luis-St
 *
 */

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
	/*
	 * TODO: global rework
	 *  - exceptions which are thrown
	 *  - logging
	 *  - method which are used -> try to shorten them -> create helper methods where possible
	 *  - add Utils class for each type (e.g. XmlUtils, JsonUtils, ...)
	 *  - rename the Utils class to something else
	 *  - try to make code in Helper classes package-private or in best case private
	 *  - sum up all the methods in the Helper classes and try to shorten them
	 */
	
	public static void main(String[] args) throws Exception {
	
	}
	
/*
	
	private static XmlElement getXmlElement(Document document, XPath xPath, String path, String nodeName) throws Exception {
		System.out.println("getXmlElement: " + path + "/" + nodeName);
		// TODO: validation
		String xml = StringUtils.isBlank(path) ? nodeName : path + "/" + nodeName;
		if (hasNoChildren(document, xPath, xml)) {
			XmlElement element = new XmlElement(nodeName, getXmlValue(document, xPath, xml));
			for (XmlAttribute attribute : getXmlAttributes(document, xPath, xml)) {
				element.addAttribute(attribute);
			}
			return element;
		}
		XPathExpression expression = xPath.compile(xml + "/*");
		XmlElement element = new XmlElement(nodeName);
		for (XmlAttribute attribute : getXmlAttributes(document, xPath, xml)) {
			element.addAttribute(attribute);
		}
		List<Node> nodes = getNodes((NodeList) expression.evaluate(document, XPathConstants.NODESET));
		if (nodes.size() == 0) {
			return element;
		}
		if (isXmlArray(nodes)) {
			getXmlArray(document, xPath, nodes, xml).forEach(element::addElement);
		} else {
			for (Node node : nodes) {
				element.addElement(getXmlElement(document, xPath, xml, node.getNodeName()));
			}
		}
		return element;
	}
	
	private static List<Node> getNodes(NodeList nodes) {
		// TODO: validation
		List<Node> list = Lists.newArrayList();
		for (int i = 0; i < nodes.getLength(); i++) {
			list.add(nodes.item(i));
		}
		return list;
	}
	
	private static boolean isXmlArray(List<Node> nodes) {
		// TODO: validation
		boolean isXmlArray = true;
		boolean hasDuplicates = new HashSet<>(Utils.mapList(nodes, Node::getNodeName)).size() != nodes.size();
		String name = nodes.get(0).getNodeName();
		for (int i = 1; i < nodes.size(); i++) {
			if (!name.equals(nodes.get(i).getNodeName())) {
				isXmlArray = false;
				break;
			}
		}
		if (!isXmlArray && hasDuplicates) {
			throw new RuntimeException("XML is not an array, but has duplicates: " + Utils.mapList(nodes, Node::getNodeName));
		}
		return isXmlArray;
	}
	
	private static List<XmlElement> getXmlArray(Document document, XPath xPath, List<Node> nodes, String path) throws Exception {
		System.out.println("getXmlArray -> " + path);
		List<XmlElement> elements = Lists.newArrayList();
		String name = nodes.get(0).getNodeName();
		for (Node node : nodes) {
			if (!node.hasChildNodes()) {
				elements.add(new XmlElement(name, node.getTextContent()));
				continue;
			}
			XmlElement element = new XmlElement(name);
			NodeList childNodes = node.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() != Node.TEXT_NODE) {
					element.addElement(getXmlElement(document, xPath, path, childNodes.item(j).getNodeName()));
				}
			}
			elements.add(element);
		}
		return elements;
	}
	
	private static boolean hasNoChildren(Document document, XPath xPath, String path) throws Exception {
		// TODO: validation
		XPathExpression expression = xPath.compile("count(" + path + "/*) > 0");
		return !((boolean) expression.evaluate(document, XPathConstants.BOOLEAN));
	}
	
	private static String getXmlValue(Document document, XPath xPath, String path) throws Exception {
		// TODO: validation
		XPathExpression expression = xPath.compile(path);
		return (String) expression.evaluate(document, XPathConstants.STRING);
	}
	
	private static List<XmlAttribute> getXmlAttributes(Document document, XPath xPath, String path) throws Exception {
		// TODO: validation
		XPathExpression expression = xPath.compile(path + "/@*");
		NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
		List<XmlAttribute> attributes = Lists.newArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			attributes.add(new XmlAttribute(nodeList.item(i).getNodeName(), nodeList.item(i).getTextContent()));
		}
		return attributes;
	}*/
}
