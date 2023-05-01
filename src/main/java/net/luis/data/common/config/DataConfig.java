package net.luis.data.common.config;

/**
 * Configuration which is used to write data to file
 *
 * @see Builder
 *
 * @author Luis-St
 */

public interface DataConfig {
	
	/**
	 * @return True if the data should be pretty printed, false if the data should be compact
	 */
	boolean prettyPrint();
	
	/**
	 * @return True if the data should be appended to the file, false if the file should be overwritten
	 */
	boolean allowAppend();
	
	/**
	 * @return True if custom extensions are allowed, false if only the default extension is allowed
	 */
	boolean allowCustomExtensions();
	
	/**
	 * Builder for {@link DataConfig}
	 *
	 * @author Luis-St
	 *
	 * @param <T> The type of {@link DataConfig} which is being built
	 */
	interface Builder<T extends DataConfig> {
		
		T build();
	}
}
