package net.luis.data.common.config;

import net.luis.utils.annotation.IndicationInterface;

/**
 *
 * @author Luis-St
 *
 */

@IndicationInterface
public interface DataConfig {
	
	boolean prettyPrint();
	
	boolean allowAppend();
	
	boolean allowCustomExtensions();
	
	interface Builder<T extends DataConfig> {
		
		T build();
	}
}
