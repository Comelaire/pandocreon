/*
 * 
 */
package fr.utt.pandocreon.core.game.effect;

import fr.utt.pandocreon.core.game.card.Card;

/**
 * The Class ApocalypseEffect.
 */
public class ApocalypseEffect extends Effect {

	/**
	 * Instantiates a new apocalypse effect.
	 *
	 * @param args
	 *            the args
	 */
	public ApocalypseEffect(String[] args) {
		super(args);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#doEffect(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void doEffect(Card card) {
		if (card.getOwner().getGame().getTurnStep().getNoApocalypseTurn() > 0)
			card.getOwner().getGame().doApocalypse();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getText()
	 */
	@Override
	public String getText() {
		return "Déclanche une apocalypse";
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getName()
	 */
	@Override
	public String getName() {
		return "apocalypse";
	}

}
