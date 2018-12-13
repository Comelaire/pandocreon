/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

/**
 * The Enum CardType.
 */
public enum CardType {
	
	/** The divinite. */
	DIVINITE, 
 /** The croyants. */
 CROYANTS, 
 /** The guide spirituel. */
 GUIDE_SPIRITUEL, 
 /** The deus ex. */
 DEUS_EX, 
 /** The apocalypse. */
 APOCALYPSE;

	/** The Constant ACTIONS. */
	static final CardType[] ACTIONS = {
			CROYANTS,
			GUIDE_SPIRITUEL,
			DEUS_EX,
			APOCALYPSE
	};

	/**
	 * Readable.
	 *
	 * @return the string
	 */
	public String readable() {
		return Character.toUpperCase(toString().charAt(0)) + toString().substring(1).toLowerCase().replace("_", " ");
	}

}
