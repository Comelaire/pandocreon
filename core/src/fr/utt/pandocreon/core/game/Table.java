/*
 * 
 */
package fr.utt.pandocreon.core.game;

import fr.utt.pandocreon.core.game.card.TypedCardSet;

/**
 * The Class Table.
 */
public class Table extends TypedCardSet {
	
	/** The game. */
	private final Game game;

	/**
	 * Instantiates a new table.
	 *
	 * @param game
	 *            the game
	 */
	public Table(Game game) {
		this.game = game;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.TypedCardSet#toString()
	 */
	@Override
	public String toString() {
		return "Table";
	}

}
