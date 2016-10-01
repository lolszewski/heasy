package com.oli.heasy.data.types;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
public enum HeasyDataTypes {
	CHARACTER("Character"),
	STRING("String"),
	BOOLEAN("Boolean"),
	BYTE("Byte"),
	SHORT("Short"),
	INTEGER("Integer"),
	LONG("Long"),
	FLOAT("Float"),
	DOUBLE("Double"),
	OBJECT("Object"),
	MAP("Map"),
	ITERABLE("Iterable"),
	COLLECTION("Collection"),
	LIST("List");

	private final String text;

	HeasyDataTypes(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}