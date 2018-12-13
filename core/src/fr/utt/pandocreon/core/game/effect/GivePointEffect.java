/*
 * 
 */
package fr.utt.pandocreon.core.game.effect;

import fr.utt.pandocreon.core.game.card.Card;

/**
 * The Class GivePointEffect.
 */
public class GivePointEffect extends Effect {

	/**
	 * Instantiates a new give point effect.
	 *
	 * @param args
	 *            the args
	 */
	public GivePointEffect(String[] args) {
		super(args);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#doEffect(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void doEffect(Card card) {
		card.getOwner().incrPoints(getOrigine(1), getIntArg(0));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getText()
	 */
	@Override
	public String getText() {
		int count = getIntArg(0);
		return String.format("Donne %d point%s d'action d'origine %s.",
				count, count > 1 ? "s" : "", getOrigine(1).readable());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getName()
	 */
	@Override
	public String getName() {
		return "give_points";
	}

}
