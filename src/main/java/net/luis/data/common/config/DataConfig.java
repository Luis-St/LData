package net.luis.data.common.config;

/**
 *
 * @author Luis-St
 *
 */

public interface DataConfig {
	
	boolean prettyPrint();
	
	boolean allowAppend();
	
	boolean allowCustomExtensions();
	
	interface Builder<T extends DataConfig> {
		
		T build();
	}
}
