/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.impl.CroyantsCard;
import fr.utt.pandocreon.core.game.card.impl.GuideSpirituelCard;

/**
 * The Class TakeCroyantsCardAction.
 */
public class TakeCroyantsCardAction extends CardAction {
	
	/** The croyant. */
	private CroyantsCard croyant;

	/**
	 * Instantiates a new take croyants card action.
	 *
	 * @param guideSpirituel
	 *            the guide spirituel
	 * @param croyant
	 *            the croyant
	 */
	public TakeCroyantsCardAction(GuideSpirituelCard guideSpirituel, CroyantsCard croyant) {
		super(guideSpirituel);
		this.croyant = croyant;
	}

	/**
	 * Gets the croyants card.
	 *
	 * @return the croyants card
	 */
	public CroyantsCard getCroyantsCard() {
		return croyant;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#getCard()
	 */
	@Override
	public GuideSpirituelCard getCard() {
		return (GuideSpirituelCard) super.getCard();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#onCardUsed()
	 */
	@Override
	public void onCardUsed() {
		super.onCardUsed();
		getCard().add(croyant);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#getTarget()
	 */
	@Override
	public CardContainer getTarget() {
		return card.getOwner().getCards();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.TAKE_CROYANTS;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#performAction()
	 */
	@Override
	public void performAction() {
		if (getGame().getTable().draw(croyant, getTarget(), isHumanPlayer()) != null)
			croyant.onCardUsed(this);
	}

}
