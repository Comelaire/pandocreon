/*
 * 
 */
package fr.utt.pandocreon.core.util;

/**
 * The Class Wrapper.
 *
 * @param <T>
 *            the generic type
 */
public class Wrapper<T> {
	
	/** The value. */
	public T value;

	/**
	 * Instantiates a new wrapper.
	 */
	public Wrapper() {
	}

	/**
	 * Instantiates a new wrapper.
	 *
	 * @param value
	 *            the value
	 */
	public Wrapper(T value) {
		this.value = value;
	}

}