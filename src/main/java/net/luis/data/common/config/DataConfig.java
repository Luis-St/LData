package net.luis.data.common.config;

import net.luis.utils.annotation.IndicationInterface;

/**
 *
 * @author Luis-St
 *
 */

@IndicationInterface
public interface DataConfig {
	
	interface Builder<T extends DataConfig> {
		
		T build();
		
	}
	
}
