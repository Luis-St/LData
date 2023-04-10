package net.luis.data.json;

/**
 *
 * @author Luis-St
 *
 */

public class JsonNull implements JsonElement {
	
	public static final JsonNull INSTANCE = new JsonNull();
	
	private JsonNull() {
	
	}
	
	@Override
	public JsonElement copy() {
		return this;
	}
	
	@Override
	public String toJsonString() {
		return "null";
	}
}
