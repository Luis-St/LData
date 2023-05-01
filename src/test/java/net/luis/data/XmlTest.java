package net.luis.data;

import net.luis.data.xml.XmlAttribute;
import net.luis.data.xml.XmlElement;
import net.luis.data.xml.config.XmlConfig;
import net.luis.data.xml.io.XmlReader;
import net.luis.data.xml.io.XmlWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class XmlTest {
	
	public static void main(String[] args) throws IOException {
		XmlReader reader = new XmlReader(new File("src/main/resources/test.xml"));
		XmlElement element = reader.toXml();
		log(element, 0);
		File file = new File("test.xml");
		if (!Files.exists(file.toPath())) {
			Files.createFile(file.toPath());
		}
		element.getElement("employees").addElement(buildEmployee());
		XmlWriter writer = new XmlWriter(file, XmlConfig.DEFAULT);
		writer.write(element);
		writer.flushAndClose();
	}
	
	private static void log(XmlElement element, int depth) {
		System.out.println("\t".repeat(depth) + element);
		for (XmlAttribute attribute : element.getAttributes()) {
			System.out.println("\t".repeat(depth + 1) + attribute);
		}
		for (XmlElement child : element.getElements()) {
			log(child, depth + 1);
		}
	}
	
	private static XmlElement buildEmployee() {
		XmlElement element = new XmlElement("employee");
		element.addAttribute("head", "true");
		element.addAttribute("divisions", "Marketing, Sales");
		element.addElement("name", "Luis");
		element.addElement("age", "20");
		return element;
	}
	
}
