package net.luis.data.common.io;

import net.luis.data.common.util.Utils;

import java.io.File;
import java.util.Objects;

public abstract class AbstractReader<T> implements Reader<T> {
	
	protected final String value;
	protected final int length;
	protected int index;
	
	protected AbstractReader(File file) {
		this(FileHelper.read(file));
	}
	
	protected AbstractReader(String value) {
		this.validate(value);
		this.value = this.deleteWhitespace() ? Utils.deleteWhitespace(value) : value;
		this.length = this.value.length();
	}
	
	protected void validate(String value) {
		Objects.requireNonNull(value, "Value must not be null");
	}
	
	protected abstract boolean deleteWhitespace();
	
	@Override
	public boolean hasNext() {
		return this.length > this.index && this.length > 0;
	}
	
	protected String fromIndex(int index) {
		return this.value.substring(this.index, index);
	}
	
	public void reset() {
		this.index = 0;
	}
	
	public void close() {
		this.index = this.length;
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractReader<?> that)) return false;
		
		if (this.length != that.length) return false;
		if (this.index != that.index) return false;
		return this.value.equals(that.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.value, this.length, this.index);
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	//endregion
}
