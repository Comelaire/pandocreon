/*
 * 
 */
package fr.utt.pandocreon.core.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum Origine.
 */
public enum Origine {
	
	/** The jour. */
	JOUR(true), 
 /** The nuit. */
 NUIT(true), 
 /** The neant. */
 NEANT(true), 
 /** The crepuscule. */
 CREPUSCULE(false), 
 /** The aube. */
 AUBE(false);

	/** The Constant ACTIONS. */
	public static final Origine[] ACTIONS;

	static {
		List<Origine> actions = new ArrayList<>();
		for (final Origine o : values())
			if (o.isAction) {
				o.index = actions.size();
				actions.add(o);
			}
		ACTIONS = actions.toArray(new Origine[actions.size()]);
	}

	/** The is action. */
	public final boolean isAction;
	
	/** The index. */
	private int index;

	/**
	 * Instantiates a new origine.
	 *
	 * @param isAction
	 *            the is action
	 */
	private Origine(boolean isAction) {
		this.isAction = isAction;
		index = -1;
	}

	/**
	 * Readable.
	 *
	 * @return the string
	 */
	public String readable() {
		return Character.toUpperCase(toString().charAt(0)) + toString().substring(1).toLowerCase();
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the points for.
	 *
	 * @param origineDivinite
	 *            the origine divinite
	 * @return the points for
	 */
	public int getPointsFor(Origine origineDivinite) {
		switch (origineDivinite) {
		case JOUR:
		case NUIT:
			return this == origineDivinite ? 2 : 0;

		case AUBE:
			return this == JOUR || this == NEANT ? 1 : 0;

		case CREPUSCULE:
			return this == NUIT || this == NEANT ? 1 : 0;

		default: throw new IllegalArgumentException("No divinite matching case: " + origineDivinite);
		}
	}

}
