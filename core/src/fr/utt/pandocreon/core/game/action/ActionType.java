/*
 * 
 */
package fr.utt.pandocreon.core.game.action;

/**
 * The Enum ActionType.
 */
public enum ActionType {
	
	/** The end turn. */
	END_TURN, 
 /** The draw card. */
 DRAW_CARD, 
 /** The discard card. */
 DISCARD_CARD, 
 /** The play card. */
 PLAY_CARD,
	
	/** The launch dice. */
	LAUNCH_DICE, 
 /** The sacrifice card. */
 SACRIFICE_CARD, 
 /** The take croyants. */
 TAKE_CROYANTS, 
 /** The use effect. */
 USE_EFFECT;

	/**
	 * Readable.
	 *
	 * @return the string
	 */
	public String readable() {
		return Character.toUpperCase(toString().charAt(0)) + toString().substring(1).replace('_', ' ');
	}

}
