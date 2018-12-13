/*
 * 
 */
package fr.utt.pandocreon.core.game.effect;

import java.util.List;

import fr.utt.pandocreon.core.game.Dogme;
import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class DestroyTableCroyantsEffect.
 */
public class DestroyTableCroyantsEffect extends Effect {

	/**
	 * Instantiates a new destroy table croyants effect.
	 *
	 * @param args
	 *            the args
	 */
	public DestroyTableCroyantsEffect(String[] args) {
		super(args);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#doEffect(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void doEffect(Card card) {
		Game g = card.getOwner().getGame();
		List<Card> croyants = g.getTable().getCards(CardType.CROYANTS);
		Dogme d = getDogme(0);
		croyants.removeIf(c -> !c.hasDogme(d));
		while (!croyants.isEmpty())
			g.getTable().draw(croyants.remove(0), g.getCards().getDiscard(), true);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getText()
	 */
	@Override
	public String getText() {
		return String.format("Défausse les croyants de dogme %s au centre de la table.",
				getDogme(0).readable());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getName()
	 */
	@Override
	public String getName() {
		return "discard_croyants";
	}

}
