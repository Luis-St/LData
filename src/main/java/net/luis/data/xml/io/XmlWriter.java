package net.luis.data.xml.io;

import net.luis.data.common.io.AbstractWriter;
import net.luis.data.xml.XmlElement;
import net.luis.data.xml.config.XmlConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A writer for {@link XmlElement XmlElements} to write them to a file
 *
 * @see XmlElement
 * @see AbstractWriter
 *
 * @author Luis-St
 */

public class XmlWriter extends AbstractWriter<XmlConfig> {
	
	/**
	 * Constructs a new {@link XmlWriter} with the given file using the {@link XmlConfig#DEFAULT}
	 * @param file The file to write to as a string
	 */
	public XmlWriter(String file) {
		super(file, XmlConfig.DEFAULT);
	}
	
	/**
	 * Constructs a new {@link XmlWriter} with the given file using the {@link XmlConfig#DEFAULT}
	 * @param file The file to write to
	 */
	public XmlWriter(File file) {
		super(file, XmlConfig.DEFAULT);
	}
	
	/**
	 * Constructs a new {@link XmlWriter} with the given file and config
	 * @param file The file to write to as a string
	 * @param config The config to use
	 */
	public XmlWriter(String file, XmlConfig config) {
		super(file, config);
	}
	
	/**
	 * Constructs a new {@link XmlWriter} with the given file and config
	 * @param file The file to write to
	 * @param config The config to use
	 */
	public XmlWriter(File file, XmlConfig config) {
		super(file, config);
	}
	
	@Override
	protected boolean isExtensionNotAllowed(String extension) {
		return extension == null || !extension.equals("xml");
	}
	
	@Override
	protected void init() {
		try {
			if (this.config.prettyPrint()) {
				this.writer.write("<?xml version=\"1.0\" encoding=\"" + this.config.encoding() + "\" ?>" + System.lineSeparator());
			} else {
				this.writer.write("<?xml version=\"1.0\" encoding=\"" + this.config.encoding() + "\" ?>");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Writes the given {@link XmlElement} to the file
	 * @param element The element to write
	 * @throws RuntimeException If an error occurs while writing the element
	 */
	public void write(@NotNull XmlElement element) {
		String xml = element.toString(this.config);
		try {
			this.writer.write(xml + System.lineSeparator());
		} catch (Exception e) {
			throw new RuntimeException("Failed to write xml element to file", e);
		}
	}
}
