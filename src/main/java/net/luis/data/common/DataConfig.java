package net.luis.data.common;

import net.luis.utils.annotation.IndicationInterface;

/**
 *
 * @author Luis-St
 *
 */

@IndicationInterface
public interface DataConfig {
	
	public interface Builder<T extends DataConfig> {
		
		public T build();
		
	}
	
}
