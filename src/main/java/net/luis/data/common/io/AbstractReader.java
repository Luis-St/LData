package net.luis.data.common.io;

import net.luis.utils.util.LazyLoad;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public abstract class AbstractReader<T> implements Reader<T> {
	
	private final LazyLoad<String> lazyValue;
	private final LazyLoad<Integer> lazyLength;
	protected int index;
	
	protected AbstractReader(File file) {
		this(FileHelper.read(file));
	}
	
	protected AbstractReader(String value) {
		this.lazyValue = new LazyLoad<>(() -> {
			this.validate(value);
			return this.modify(value);
		});
		this.lazyLength = new LazyLoad<>(() -> this.value().length());
	}
	
	protected void validate(String value) {
		Objects.requireNonNull(value, "Value must not be null");
	}
	
	protected abstract String modify(String original);
	
	protected final String value() {
		return this.lazyValue.get();
	}
	
	protected final int length() {
		return this.lazyLength.get();
	}
	
	@Override
	public boolean hasNext() {
		return this.index < this.length() && !this.value().isBlank();
	}
	
	protected String fromIndex(int index) {
		return this.value().substring(this.index, index);
	}
	
	protected String remaining() {
		return this.value().substring(this.index);
	}
	
	//region IO operations
	@Override
	public void reset() {
		this.index = 0;
	}
	
	@Override
	public void close() {
		this.index = this.length();
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractReader<?> that)) return false;
		
		if (this.length() != that.length()) return false;
		if (this.index != that.index) return false;
		return this.value().equals(that.value());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value(), this.length(), this.index);
	}
	
	@Override
	public String toString() {
		return this.value();
	}
	//endregion
}
