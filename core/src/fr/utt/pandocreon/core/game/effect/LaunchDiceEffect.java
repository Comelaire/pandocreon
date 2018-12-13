/*
 * 
 */
package fr.utt.pandocreon.core.game.effect;

import fr.utt.pandocreon.core.game.action.impl.LaunchDiceAction;
import fr.utt.pandocreon.core.game.card.Card;

/**
 * The Class LaunchDiceEffect.
 */
public class LaunchDiceEffect extends Effect {
	
	/** The action. */
	private final LaunchDiceAction action;

	/**
	 * Instantiates a new launch dice effect.
	 *
	 * @param args
	 *            the args
	 */
	public LaunchDiceEffect(String[] args) {
		super(args);
		action = new LaunchDiceAction();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#doEffect(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void doEffect(Card card) {
		action.performAction();
		action.addPoints(card.getOwner().getGame());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getText()
	 */
	@Override
	public String getText() {
		return "Lance le dé de cosmogonie";
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.effect.Effect#getName()
	 */
	@Override
	public String getName() {
		return "launch_dice";
	}

}
