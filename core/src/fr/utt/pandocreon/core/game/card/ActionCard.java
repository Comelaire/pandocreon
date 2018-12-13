/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.Map;
import java.util.function.Consumer;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.CardAction;

/**
 * The Class ActionCard.
 */
public abstract class ActionCard extends Card {

	/**
	 * Instantiates a new action card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public ActionCard(Map<String, String> attributes) {
		super(attributes);
	}

	/**
	 * Gets the target.
	 *
	 * @param game
	 *            the game
	 * @return the target
	 */
	public CardContainer getTarget(Game game) {
		return game.getCards().getDiscard();
	}

	/**
	 * Adds the possible actions.
	 *
	 * @param player
	 *            the player
	 * @param playerTurn
	 *            the player turn
	 * @param consumer
	 *            the consumer
	 */
	public void addPossibleActions(Player player, boolean playerTurn, Consumer<GameAction> consumer) {
		new ActionCost(player, this).asActions().stream()
		.filter(action -> playerTurn || action.isFree()).forEach(consumer);
	}

	/**
	 * On card used.
	 *
	 * @param action
	 *            the action
	 */
	public void onCardUsed(CardAction action) {
		action.onCardUsed();
	}

}
