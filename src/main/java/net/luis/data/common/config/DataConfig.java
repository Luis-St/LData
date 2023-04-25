package net.luis.data.common.config;

/**
 *
 * @author Luis-St
 *
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
	 *
	 * @author Luis-St
	 *
	 */
	interface Builder<T extends DataConfig> {
		
		T build();
	}
}
