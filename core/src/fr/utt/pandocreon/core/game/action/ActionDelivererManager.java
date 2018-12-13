/*
 * 
 */
package fr.utt.pandocreon.core.game.action;

import fr.utt.pandocreon.core.game.Player;

/**
 * The Interface ActionDelivererManager.
 */
public interface ActionDelivererManager {
	
	/**
	 * Gets the deliverer.
	 *
	 * @param player
	 *            the player
	 * @return the deliverer
	 */
	ActionDeliverer getDeliverer(Player player);
}
