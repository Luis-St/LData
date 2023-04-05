package net.luis.data.json;

import com.google.common.collect.Lists;
import net.luis.data.json.primitive.JsonBoolean;
import net.luis.data.json.primitive.JsonNumber;
import net.luis.data.json.primitive.JsonString;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class JsonArray implements JsonElement, Iterable<JsonElement> {
	
	private final List<JsonElement> elements = Lists.newArrayList();
	
	public JsonArray() {
	
	}
	
	public JsonArray(List<JsonElement> elements) {
		this.elements.addAll(elements);
	}
	
	//region Adders
	public boolean add(JsonElement element) {
		return this.elements.add(element == null ? JsonNull.INSTANCE : element);
	}
	
	public boolean add(boolean value) {
		return this.elements.add(new JsonBoolean(value));
	}
	
	public boolean add(Number element) {
		return this.add(new JsonNumber(element));
	}
	
	public boolean add(String element) {
		return this.add(new JsonString(element));
	}
	//endregion
	
	public boolean addAll(@NotNull JsonArray array) {
		return this.elements.addAll(array.elements);
	}
	
	public JsonElement set(int index, JsonElement element) {
		return this.elements.set(index, element == null ? JsonNull.INSTANCE : element);
	}
	
	public JsonElement remove(int index) {
		return this.elements.remove(index);
	}
	
	public boolean remove(JsonElement element) {
		return this.elements.remove(element);
	}
	
	public int size() {
		return this.elements.size();
	}
	
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
	public boolean contains(JsonElement element) {
		return this.elements.contains(element);
	}
	
	public boolean containsAll(@NotNull JsonArray array) {
		return new HashSet<>(this.elements).containsAll(array.elements);
	}
	
	@Override
	public @NotNull Iterator<JsonElement> iterator() {
		return this.elements.iterator();
	}
	
	//region Getters
	public JsonElement get(int index) {
		return this.elements.get(index);
	}
	
	public JsonObject getAsObject() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsObject();
		}
		throw new IllegalStateException("JsonArray is not a single object");
	}
	
	public JsonArray getAsArray() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsArray();
		}
		throw new IllegalStateException("JsonArray is not a single array");
	}
	
	public boolean getAsBoolean() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsBoolean();
		}
		throw new IllegalStateException("JsonArray is not a single boolean");
	}
	
	public int getAsInt() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsInt();
		}
		throw new IllegalStateException("JsonArray is not a single int");
	}
	
	public long getAsLong() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsLong();
		}
		throw new IllegalStateException("JsonArray is not a single long");
	}
	
	public float getAsFloat() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsFloat();
		}
		throw new IllegalStateException("JsonArray is not a single float");
	}
	
	public double getAsDouble() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsDouble();
		}
		throw new IllegalStateException("JsonArray is not a single double");
	}
	
	public String getAsString() {
		if (this.elements.size() == 1) {
			return this.elements.get(0).getAsString();
		}
		throw new IllegalStateException("JsonArray is not a single string");
	}
	//endregion
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonArray jsonArray)) return false;
		
		return this.elements.equals(jsonArray.elements);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.elements);
	}
	
	@Override
	public String toString() {
		return this.elements.toString();
	}
	//endregion
}
