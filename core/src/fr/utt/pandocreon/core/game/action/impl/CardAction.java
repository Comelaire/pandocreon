/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardContainer;

/**
 * The Class CardAction.
 */
public abstract class CardAction extends GameAction {
	
	/** The card. */
	protected ActionCard card;

	/**
	 * Instantiates a new card action.
	 *
	 * @param card
	 *            the card
	 */
	public CardAction(ActionCard card) {
		this.card = card;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public abstract CardContainer getTarget();

	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public ActionCard getCard() {
		return card;
	}

	/**
	 * On card used.
	 */
	public void onCardUsed() {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#accept(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public boolean accept(Player player) {
		return card.getOwner() == null || card.getOwner() == player;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#performAction()
	 */
	@Override
	public void performAction() {
		Player p = card.getOwner();
		if ((p == null ? getPlayer() : p).getCards().draw(card,
				getTarget(), isHumanPlayer()) != null)
			card.onCardUsed(this);
	}

}
