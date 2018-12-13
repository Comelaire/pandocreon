/*
 * 
 */
package fr.utt.pandocreon.core.game.card.impl;

import java.util.Map;

import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class DiviniteCard.
 */
public class DiviniteCard extends Card {
	
	/** The effect used. */
	private boolean effectUsed;

	/**
	 * Instantiates a new divinite card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public DiviniteCard(Map<String, String> attributes) {
		super(attributes);
	}

	/**
	 * Checks if is effect used.
	 *
	 * @return true, if is effect used
	 */
	public boolean isEffectUsed() {
		return effectUsed;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#doEffect()
	 */
	@Override
	public void doEffect() {
		if (effectUsed)
			throw new IllegalStateException("Effect already used for " + this);
		effectUsed = true;
		super.doEffect();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#getType()
	 */
	@Override
	public CardType getType() {
		return CardType.DIVINITE;
	}

}
