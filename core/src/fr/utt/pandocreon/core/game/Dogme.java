/*
 * 
 */
package fr.utt.pandocreon.core.game;

/**
 * The Enum Dogme.
 */
public enum Dogme {
	
	/** The nature. */
	NATURE, 
 /** The humain. */
 HUMAIN, 
 /** The symboles. */
 SYMBOLES, 
 /** The mystique. */
 MYSTIQUE, 
 /** The chaos. */
 CHAOS;

	/**
	 * Readable.
	 *
	 * @return the string
	 */
	public String readable() {
		return Character.toUpperCase(toString().charAt(0)) + toString().substring(1).toLowerCase();
	}
}
