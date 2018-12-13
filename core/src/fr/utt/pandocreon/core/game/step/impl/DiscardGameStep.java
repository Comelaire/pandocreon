/*
 * 
 */
package fr.utt.pandocreon.core.game.step.impl;

import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.impl.DiscardCardAction;
import fr.utt.pandocreon.core.game.action.impl.EndTurnAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardSet;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.StepAction;

/**
 * The Class DiscardGameStep.
 */
public class DiscardGameStep extends GameStep {

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getStepAction()
	 */
	@Override
	protected StepAction getStepAction() {
		StepAction actions = new StepAction(this);
		actions.add(new EndTurnAction());
		for (final ActionCard card : getPlayer().getCards().asList())
			actions.add(new DiscardCardAction(card));
		return actions;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#stop()
	 */
	@Override
	public void stop() {
		CardSet cards = getPlayer().getCards();
		while (cards.asList().size() < GameStartStep.CARDS_TO_DISTRIBUTE)
			getGame().getCards().drawActionCard(cards, getPlayer().getType() == PlayerType.HUMAN);
		super.stop();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Défausse de cartes";
	}

}
