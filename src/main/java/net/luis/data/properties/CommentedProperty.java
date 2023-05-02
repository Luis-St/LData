package net.luis.data.properties;

import net.luis.data.json.JsonObject;
import net.luis.data.properties.config.PropertyConfig;
import net.luis.data.properties.exception.PropertyException;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Property that represents a commented property
 *
 * @author Luis-St
 */

public final class CommentedProperty implements Property {
	
	private final Property actual;
	private final List<String> comments;
	
	/**
	 * Constructs a new {@link CommentedProperty}
	 * @param actual The actual property
	 * @param comment The comment of the property
	 * @throws NullPointerException If the comment is null
	 * @throws IllegalArgumentException If a commented property is nested inside another commented property
	 */
	public CommentedProperty(Property actual, String comment) {
		this(actual, Objects.requireNonNull(comment, "Comment must not be null").split("\n"));
	}
	
	/**
	 * Constructs a new {@link CommentedProperty}
	 * @param actual The actual property
	 * @param comments The comments of the property
	 * @throws NullPointerException If the comment is null
	 * @throws IllegalArgumentException If a commented property is nested inside another commented property
	 */
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
	
	/**
	 * @return True if the actual property is a {@link PropertyArray}
	 */
	@Override
	public boolean isArray() {
		return this.actual.isArray();
	}
	
	/**
	 * @return The actual property as an {@link PropertyArray}
	 * @throws PropertyException If the actual property is not a {@link PropertyArray}
	 */
	@Override
	public PropertyArray getAsArray() {
		return this.actual.getAsArray();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyPrimitive}
	 */
	@Override
	public boolean isPrimitive() {
		return this.actual.isPrimitive();
	}
	
	/**
	 * @return The actual property as an {@link PropertyPrimitive}
	 * @throws PropertyException If the actual property is not a {@link PropertyPrimitive}
	 */
	@Override
	public PropertyPrimitive getAsPrimitive() {
		return this.actual.getAsPrimitive();
	}
	
	/**
	 * @return True if the actual property is an {@link PropertyBoolean}
	 */
	@Override
	public boolean isBoolean() {
		return this.actual.isBoolean();
	}
	
	/**
	 * @return The actual property as a {@link Boolean}
	 * @throws PropertyException If the actual property is not a {@link PropertyBoolean}
	 */
	@Override
	public boolean getAsBoolean() {
		return this.actual.getAsBoolean();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyNumber}
	 */
	@Override
	public boolean isNumber() {
		return this.actual.isNumber();
	}
	
	/**
	 * @return The actual property as a {@link Number}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public Number getAsNumber() {
		return this.actual.getAsNumber();
	}
	
	/**
	 * @return The actual property as an {@link Integer}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public int getAsInt() {
		return this.actual.getAsInt();
	}
	
	/**
	 * @return The actual property as a {@link Long}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public long getAsLong() {
		return this.actual.getAsLong();
	}
	
	/**
	 * @return The actual property as a {@link Double}
	 * @throws PropertyException If the actual property is not a {@link PropertyNumber}
	 */
	@Override
	public double getAsDouble() {
		return this.actual.getAsDouble();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyString}
	 */
	@Override
	public boolean isString() {
		return this.actual.isString();
	}
	
	/**
	 * @return The actual property as a {@link String}
	 * @throws PropertyException If the actual property is not a {@link PropertyString}
	 */
	@Override
	public String getAsString() {
		return this.actual.getAsString();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyJson}
	 */
	@Override
	public boolean isJson() {
		return this.actual.isJson();
	}
	
	/**
	 * @return The actual property as a {@link JsonObject}
	 * @throws PropertyException If the actual property is not a {@link PropertyJson}
	 */
	@Override
	public JsonObject getAsJson() {
		return this.actual.getAsJson();
	}
	
	/**
	 * @return True if the actual property is a {@link PropertyNull}
	 */
	@Override
	public boolean isNull() {
		return this.actual.isNull();
	}
	//endregion
	
	/**
	 * Converts the comments to a string
	 * @param config The config to use
	 * @return The comments as a string
	 */
	public @NotNull String getComment(PropertyConfig config) {
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
