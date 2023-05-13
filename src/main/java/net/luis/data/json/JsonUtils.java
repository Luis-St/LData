package net.luis.data.json;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.data.json.io.JsonSerializable;
import net.luis.utils.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

/**
 * Json utilities
 *
 * @author Luis-St
 */

public class JsonUtils {
	
	/**
	 * Writes the given uuid to a json object
	 * @param uuid The uuid to write
	 * @return The json object
	 */
	public static @NotNull JsonObject writeUUID(UUID uuid) {
		Objects.requireNonNull(uuid, "UUID must not be null");
		JsonObject json = new JsonObject();
		json.add("mostBits", uuid.getMostSignificantBits());
		json.add("leastBits", uuid.getLeastSignificantBits());
		return json;
	}
	
	/**
	 * Reads a uuid from the given json object.<br>
	 * If the json object does not contain the uuid, {@link Utils#EMPTY_UUID} will be returned
	 * @param json The json object to read from
	 * @return The uuid
	 */
	public static @NotNull UUID readUUID(JsonObject json) {
		Objects.requireNonNull(json, "Json must not be null");
		if (json.has("mostBits") && json.has("leastBits")) {
			return new UUID(json.getAsLong("mostBits"), json.getAsLong("leastBits"));
		}
		return Utils.EMPTY_UUID;
	}
	
	/**
	 * Write the given list to a json array
	 * @param list The list to write
	 * @return The json array
	 * @param <T> The type of the list elements which must be a instance of {@link JsonSerializable}
	 * @throws NullPointerException If the list is null
	 * @see #writeList(List, Function)
	 */
	public static <T extends JsonSerializable<? extends Json>> @NotNull JsonArray writeList(List<T> list) {
		return writeList(list, JsonSerializable::toJson);
	}
	
	/**
	 * Write the given list to a json array
	 * @param list The list to write
	 * @param mapper The mapper to map the list elements to json
	 * @return The json array
	 * @param <T> The type of the list elements
	 * @throws NullPointerException If the list or the mapper is null
	 */
	public static <T> @NotNull JsonArray writeList(List<T> list, Function<T, ? extends Json> mapper) {
		JsonArray json = new JsonArray();
		for (T element : Objects.requireNonNull(list, "List must not be null")) {
			json.add(Objects.requireNonNull(mapper, "Mapper must not be null").apply(element));
		}
		return json;
	}
	
	/**
	 * Read a list from the given json array
	 * @param json The json array to read from
	 * @param mapper The mapper to map the json elements to the list elements
	 * @return The list
	 * @param <T> The type of the list elements
	 * @throws NullPointerException If the json array or the mapper is null
	 */
	public static <T> @NotNull List<T> readList(JsonArray json, Function<Json, T> mapper) {
		List<T> list = Lists.newArrayList();
		for (Json element : Objects.requireNonNull(json, "Json must not be null")) {
			list.add(Objects.requireNonNull(mapper, "Mapper must not be null").apply(element));
		}
		return list;
	}
	
	/**
	 * Writes the given map to a json object
	 * @param map The map to write
	 * @return The json object
	 * @param <T> The type of the map values which must be a instance of {@link JsonSerializable}
	 * @throws NullPointerException If the map is null
	 * @see #writeMap(Map, Function)
	 */
	public static <T extends JsonSerializable<? extends Json>> @NotNull JsonObject writeMap(Map<String, T> map) {
		return writeMap(map, JsonSerializable::toJson);
	}
	
	/**
	 * Writes the given map to a json object
	 * @param map The map to write
	 * @param mapper The mapper to map the map values to json values
	 * @return The json object
	 * @param <T> The type of the map values
	 * @throws NullPointerException If the map or the mapper is null
	 * @see #writeMap(Map, Function, Function)
	 */
	public static <T> @NotNull JsonObject writeMap(Map<String, T> map, Function<T, ? extends Json> mapper) {
		return writeMap(map, Functions.identity(), mapper);
	}
	
	/**
	 * Writes the given map to a json object
	 * @param map The map to write
	 * @param keyMapper The mapper to map the map keys to json keys
	 * @param valueMapper The mapper to map the map values to json values
	 * @return The json object
	 * @param <K> The type of the map keys
	 * @param <V> The type of the map values
	 * @throws NullPointerException If the map, the key mapper or the value mapper is null
	 */
	public static <K, V> @NotNull JsonObject writeMap(Map<K, V> map, Function<K, String> keyMapper, Function<V, ? extends Json> valueMapper) {
		JsonObject json = new JsonObject();
		for (Map.Entry<K, V> entry : Objects.requireNonNull(map, "Map must not be null").entrySet()) {
			String key = Objects.requireNonNull(keyMapper, "Key mapper must not be null").apply(entry.getKey());
			Json element = Objects.requireNonNull(valueMapper, "Value mapper must not be null").apply(entry.getValue());
			json.add(key, element);
		}
		return json;
	}
	
	/**
	 * Reads a map from the given json object
	 * @param json The json object to read from
	 * @param mapper The mapper to map the json values to the map values
	 * @return The map
	 * @param <T> The type of the map values
	 * @throws NullPointerException If the json object or the mapper is null
	 * @see #readMap(JsonObject, Function, Function)
	 */
	public static <T> @NotNull Map<String, T> readMap(JsonObject json, Function<Json, T> mapper) {
		return readMap(json, Functions.identity(), mapper);
	}
	
	/**
	 * Reads a map from the given json object
	 * @param json The json object to read from
	 * @param keyMapper The mapper to map the json keys to the map keys
	 * @param valueMapper The mapper to map the json values to the map values
	 * @return The map
	 * @param <K> The type of the map keys
	 * @param <V> The type of the map values
	 * @throws NullPointerException If the json object, the key mapper or the value mapper is null
	 */
	public static <K, V> @NotNull Map<K, V> readMap(JsonObject json, Function<String, K> keyMapper, Function<Json, V> valueMapper) {
		Map<K, V> map = Maps.newHashMap();
		for (Map.Entry<String, Json> entry : Objects.requireNonNull(json, "Json must not be null")) {
			K key = Objects.requireNonNull(keyMapper, "Key mapper must not be null").apply(entry.getKey());
			V value = Objects.requireNonNull(valueMapper, "Value mapper must not be null").apply(entry.getValue());
			map.put(key, value);
		}
		return map;
	}
}