/*
 * 
 */
package fr.utt.pandocreon.core.game;

import java.util.EventListener;

import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;

/**
 * The listener interface for receiving player events. The class that is
 * interested in processing a player event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addPlayerListener<code> method. When the player event
 * occurs, that object's appropriate method is invoked.
 *
 * @see PlayerEvent
 */
public interface PlayerListener extends EventListener {
	
	/**
	 * On divinite set.
	 *
	 * @param player
	 *            the player
	 * @param divinite
	 *            the divinite
	 */
	void onDiviniteSet(Player player, DiviniteCard divinite);
	
	/**
	 * On points change.
	 *
	 * @param player
	 *            the player
	 * @param origine
	 *            the origine
	 * @param oldPoints
	 *            the old points
	 * @param newPoints
	 *            the new points
	 */
	void onPointsChange(Player player, Origine origine, int oldPoints, int newPoints);
}
