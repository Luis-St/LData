package net.luis.data.xml.io;

import net.luis.data.common.io.AbstractWriter;
import net.luis.data.xml.config.XmlConfig;
import net.luis.data.xml.elements.XmlElement;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class XmlWriter extends AbstractWriter<XmlConfig> {
	
	public XmlWriter(String file) {
		super(file, XmlConfig.DEFAULT);
	}
	
	public XmlWriter(File file) {
		super(file, XmlConfig.DEFAULT);
	}
	
	public XmlWriter(String file, XmlConfig config) {
		super(file, config);
	}
	
	public XmlWriter(File file, XmlConfig config) {
		super(file, config);
	}
	
	@Override
	protected boolean isExtensionNotAllowed(String extension) {
		return extension == null || !extension.equals("xml");
	}
	
	public void write(@NotNull XmlElement element) {
		String xml = element.toString(this.config);
		try {
			this.writer.write(xml + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}