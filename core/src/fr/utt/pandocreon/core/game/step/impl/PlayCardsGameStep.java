/*
 * 
 */
package fr.utt.pandocreon.core.game.step.impl;

import java.util.List;
import java.util.function.Consumer;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.EndTurnAction;
import fr.utt.pandocreon.core.game.action.impl.TakeCroyantsCardAction;
import fr.utt.pandocreon.core.game.action.impl.UseEffectAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardSet;
import fr.utt.pandocreon.core.game.card.CardType;
import fr.utt.pandocreon.core.game.card.impl.CroyantsCard;
import fr.utt.pandocreon.core.game.card.impl.GuideSpirituelCard;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.StepAction;

/**
 * The Class PlayCardsGameStep.
 */
public class PlayCardsGameStep extends GameStep {

	/**
	 * Discard table cards.
	 *
	 * @param type
	 *            the type
	 */
	public void discardTableCards(CardType type) {
		List<Card> cards = getGame().getTable().getCards(type);
		while (!cards.isEmpty())
			getGame().getTable().draw(cards.get(0), getGame().getCards().getDiscard(), true);
	}

	/**
	 * Adds the main player actions.
	 *
	 * @param p
	 *            the p
	 * @param consumer
	 *            the consumer
	 */
	public void addMainPlayerActions(Player p, Consumer<GameAction> consumer) {
		for (final Card card : getGame().getTable().getCards(CardType.CROYANTS))
			if (card.getOwner() != p)
				for (final Card c : p.getCards().asList())
					if (c instanceof GuideSpirituelCard) {
						CroyantsCard croyant = (CroyantsCard) card;
						GuideSpirituelCard guide = (GuideSpirituelCard) c;
						if (guide.accept(croyant))
							consumer.accept(new TakeCroyantsCardAction(guide, croyant));
					}
		if (!p.getDivinite().isEffectUsed())
			consumer.accept(new UseEffectAction(p.getDivinite()));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getStepAction()
	 */
	@Override
	protected StepAction getStepAction() {
		StepAction actions = new StepAction(this);
		actions.add(new EndTurnAction());
		Player main = getPlayer();
		for (final Player p : getGame().getAlivePlayers()) {
			boolean mainPlayer = p == main;
			for (final ActionCard card : p.getCards().asList())
				if (card.getOrigine() == null || mainPlayer)
					card.addPossibleActions(p, mainPlayer, actions::add);
		}
		addMainPlayerActions(main, actions::add);
		return actions;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#end()
	 */
	@Override
	public void end() {
		CardSet cards = getPlayer().getCards();
		while (cards.asList().size() < GameStartStep.CARDS_TO_DISTRIBUTE)
			getGame().getCards().drawActionCard(cards, getPlayer().getType() == PlayerType.HUMAN);
		for (final Card c : getGame().getCards().getCards(CardType.CROYANTS))
			c.setOwner(null);
		discardTableCards(CardType.APOCALYPSE);
		discardTableCards(CardType.DEUS_EX);
		super.end();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pose de cartes et sacrifices";
	}

}
