package net.luis.data;

import net.luis.data.xml.attributes.XmlAttribute;
import net.luis.data.xml.elements.XmlElement;
import net.luis.data.xml.io.XmlReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;

/**
 *
 * @author Luis-St
 *
 */

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
	/*
	 * TODO: xml
	 *  - implement toString(XmlConfig) in XmlElement and XmlElements
	 *  - test xml writer
	 *  - add xml test
	 *
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
		XmlReader reader = new XmlReader(new File("src/main/resources/test.xml"));
		LOGGER.info(reader);
		XmlElement element = reader.toXml();
		log(element, 0);
		File file = new File("test.xml");
		if (!Files.exists(file.toPath())) {
			Files.createFile(file.toPath());
		}
	}
	
	private static void log(XmlElement element, int depth) {
		LOGGER.info("\t".repeat(depth) + element);
		for (XmlAttribute attribute : element.getAttributes()) {
			LOGGER.info("\t".repeat(depth + 1) + attribute);
		}
		for (XmlElement child : element.getElements()) {
			log(child, depth + 1);
		}
	}
	
}
