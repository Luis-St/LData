package net.luis.data.json;

import com.google.common.collect.Lists;
import net.luis.data.json.config.JsonConfig;
import net.luis.utils.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

public final class JsonArray implements Json, Iterable<Json> {
	
	private final List<Json> elements = Lists.newArrayList();
	
	public JsonArray() {
		super();
	}
	
	public JsonArray(Json... elements) {
		this.elements.addAll(Arrays.asList(elements));
	}
	
	@Override
	public @NotNull String getName() {
		return "json array";
	}
	
	@Override
	public @NotNull JsonArray copy() {
		JsonArray array = new JsonArray();
		for (Json element : this.elements) {
			array.add(element.copy());
		}
		return array;
	}
	
	//region Adders
	public boolean add(Json element) {
		return this.elements.add(element == null ? JsonNull.INSTANCE : element);
	}
	
	public boolean add(boolean value) {
		return this.add(new JsonBoolean(value));
	}
	
	public boolean add(Number element) {
		return this.add(new JsonNumber(element));
	}
	
	public boolean add(String element) {
		return this.add(new JsonString(element));
	}
	//endregion
	
	//region List methods
	public boolean addAll(JsonArray array) {
		return this.elements.addAll(Objects.requireNonNull(array, "Json array must not be null").elements);
	}
	
	public Json set(int index, Json element) {
		return this.elements.set(index, element == null ? JsonNull.INSTANCE : element);
	}
	
	public Json remove(int index) {
		return this.elements.remove(index);
	}
	
	public boolean remove(Json element) {
		return this.elements.remove(element);
	}
	
	public int size() {
		return this.elements.size();
	}
	
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
	public boolean contains(Json element) {
		return this.elements.contains(element);
	}
	
	public boolean containsAll(JsonArray array) {
		return new HashSet<>(this.elements).containsAll(Objects.requireNonNull(array, "Json array must not be null").elements);
	}
	
	@Override
	public @NotNull Iterator<Json> iterator() {
		return this.elements.iterator();
	}
	//endregion
	
	//region Getters
	public Json get(int index) {
		return this.elements.get(index);
	}
	
	public JsonObject getAsObject(int index) {
		return this.elements.get(index).getAsObject();
	}
	
	public JsonArray getAsArray(int index) {
		return this.elements.get(index).getAsArray();
	}
	
	public boolean getAsBoolean(int index) {
		return this.elements.get(index).getAsBoolean();
	}
	
	public Number getAsNumber(int index) {
		return this.elements.get(index).getAsNumber();
	}
	
	public int getAsInt(int index) {
		return this.elements.get(index).getAsInt();
	}
	
	public long getAsLong(int index) {
		return this.elements.get(index).getAsLong();
	}
	
	public double getAsDouble(int index) {
		return this.elements.get(index).getAsDouble();
	}
	
	public String getAsString(int index) {
		return this.elements.get(index).getAsString();
	}
	//endregion
	
	@Override
	public @NotNull String toString(JsonConfig config) {
		if (this.elements.isEmpty()) {
			return "[]";
		}
		Objects.requireNonNull(config, "Json config must not be null");
		boolean simplify = JsonHelper.canBeSimplified(this.elements, config.simplifyPrimitiveArrays());
		List<String> values = Utils.mapList(this.elements, element -> {
			if (config.prettyPrint() && !simplify) {
				return JsonHelper.correctIndents(element, config, "");
			}
			return element.toString(config);
		});
		if (!config.prettyPrint()) {
			return "[" + String.join(",", values) + "]";
		}
		if (simplify) {
			return "[" + String.join(", ", values) + "]";
		}
		return "[" + System.lineSeparator() + String.join("," + System.lineSeparator(), values) + System.lineSeparator() + config.indent() + "]";
	}
	
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
