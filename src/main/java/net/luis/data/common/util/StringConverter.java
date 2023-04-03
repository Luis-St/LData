package net.luis.data.common.util;

/**
 *
 * @author Luis-St
 *
 */

public interface StringConverter<T> {
	
	T fromString(String string);
	
	String toString(T object);
	
}
