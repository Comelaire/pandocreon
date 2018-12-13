/*
 * 
 */
package fr.utt.pandocreon.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The Class FlexibleObject.
 */
public class FlexibleObject {
	
	/** The attributes. */
	private final Map<String, String> attributes;

	/**
	 * Instantiates a new flexible object.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public FlexibleObject(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Instantiates a new flexible object.
	 */
	public FlexibleObject() {
		this(new HashMap<>());
	}

	/**
	 * Gets the.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	public String get(String name) {
		return attributes.get(name);
	}

	/**
	 * Gets the.
	 *
	 * @param name
	 *            the name
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 */
	public String get(String name, String defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : s;
	}

	/**
	 * Sets the.
	 *
	 * @param attribute
	 *            the attribute
	 * @param value
	 *            the value
	 * @return the flexible object
	 */
	public FlexibleObject set(String attribute, String value) {
		attributes.put(attribute, value);
		return this;
	}

	/**
	 * Unset.
	 *
	 * @param attribute
	 *            the attribute
	 * @return the flexible object
	 */
	public FlexibleObject unset(String attribute) {
		attributes.remove(attribute);
		return this;
	}

	/**
	 * Checks if is sets the.
	 *
	 * @param attribute
	 *            the attribute
	 * @return true, if is sets the
	 */
	public boolean isSet(String attribute) {
		return attributes.containsKey(attribute);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return get("name");
	}

	/**
	 * Gets the getter.
	 *
	 * @param attribute
	 *            the attribute
	 * @return the getter
	 */
	public Supplier<String> getter(String attribute) {
		return () -> get(attribute);
	}

	/**
	 * Setter.
	 *
	 * @param attribute
	 *            the attribute
	 * @return the consumer
	 */
	public Consumer<String> setter(String attribute) {
		return value -> set(attribute, value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

}
