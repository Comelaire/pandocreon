/*
 * 
 */
package fr.utt.pandocreon.core.game.action;

import java.util.List;

import fr.utt.pandocreon.core.game.Player;

/**
 * The Interface ActionDeliverer.
 */
public interface ActionDeliverer {
	
	/**
	 * Gets the action to perform.
	 *
	 * @param player
	 *            the player
	 * @param actions
	 *            the actions
	 * @return the action to perform
	 */
	int getActionToPerform(Player player, List<GameAction> actions);
}
