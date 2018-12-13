/*
 * 
 */
package fr.utt.pandocreon.core.game.effect;

import fr.utt.pandocreon.core.game.card.Card;

/**
 * The Class NoEffect.
 */
public class NoEffect extends Effect {

	/**
	 * Instantiates a new no effect.
	 *
	 * @param args
	 *            the args
	 */
	public NoEffect(String[] args) {
		super(args);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#hasEffect()
	 */
	@Override
	public boolean hasEffect() {
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#doEffect(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void doEffect(Card card) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getText()
	 */
	@Override
	public String getText() {
		return "Aucun effet.";
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getName()
	 */
	@Override
	public String getName() {
		return "";
	}

}
