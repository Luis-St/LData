package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public final class CommentedProperty implements Property {
	
	private final Property actual;
	private final List<String> comments;
	
	public CommentedProperty(Property actual, String comment) {
		this(actual, Objects.requireNonNull(comment, "Comment must not be null").split("\n"));
	}
	
	public CommentedProperty(Property actual, String... comments) {
		this(actual, Arrays.asList(ArrayUtils.nullToEmpty(comments)));
	}
	
	private CommentedProperty(Property actual, List<String> comments) {
		if (actual instanceof CommentedProperty property) {
			throw new IllegalArgumentException("Cannot nest a commented property inside another commented property");
		}
		this.actual = Objects.requireNonNull(actual, "Actual property must not be null");
		this.comments = comments;
	}
	
	@Override
	public @NotNull String getName() {
		return "commented property";
	}
	
	@Override
	public @NotNull String getKey() {
		return this.actual.getKey();
	}
	
	@Override
	public @NotNull Property copy() {
		return this.actual.copy();
	}
	
	//region Property delegation
	@Override
	public boolean isArray() {
		return this.actual.isArray();
	}
	
	@Override
	public PropertyArray getAsArray() {
		return this.actual.getAsArray();
	}
	
	@Override
	public boolean isPrimitive() {
		return this.actual.isPrimitive();
	}
	
	@Override
	public PropertyPrimitive getAsPrimitive() {
		return this.actual.getAsPrimitive();
	}
	
	@Override
	public boolean isBoolean() {
		return this.actual.isBoolean();
	}
	
	@Override
	public boolean getAsBoolean() {
		return this.actual.getAsBoolean();
	}
	
	@Override
	public boolean isNumber() {
		return this.actual.isNumber();
	}
	
	@Override
	public Number getAsNumber() {
		return this.actual.getAsNumber();
	}
	
	@Override
	public int getAsInt() {
		return this.actual.getAsInt();
	}
	
	@Override
	public long getAsLong() {
		return this.actual.getAsLong();
	}
	
	@Override
	public double getAsDouble() {
		return this.actual.getAsDouble();
	}
	
	@Override
	public boolean isString() {
		return this.actual.isString();
	}
	
	@Override
	public String getAsString() {
		return this.actual.getAsString();
	}
	
	@Override
	public boolean isJson() {
		return this.actual.isJson();
	}
	
	@Override
	public JsonObject getAsJson() {
		return this.actual.getAsJson();
	}
	
	@Override
	public boolean isNull() {
		return this.actual.isNull();
	}
	
	@Override
	public boolean isObject() {
		return this.actual.isObject();
	}
	
	@Override
	public ObjectProperty getAsObject() {
		return this.actual.getAsObject();
	}
	//endregion
	
	public String getComment(PropertyConfig config) {
		Objects.requireNonNull(config, "Property config must not be null");
		String comment = config.prettyPrint() ? "# " : "#";
		return comment + String.join(System.lineSeparator() + comment, this.comments);
	}
	
	@Override
	public @NotNull String toString(PropertyConfig config) {
		return this.actual.toString(config);
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CommentedProperty that)) return false;
		
		if (!this.actual.equals(that.actual)) return false;
		return this.comments.equals(that.comments);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.actual, this.comments);
	}
	
	@Override
	public String toString() {
		return this.actual.toString();
	}
	//endregion
}
