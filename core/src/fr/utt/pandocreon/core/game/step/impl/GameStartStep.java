/*
 * 
 */
package fr.utt.pandocreon.core.game.step.impl;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.impl.DrawCardAction;
import fr.utt.pandocreon.core.game.card.CardType;
import fr.utt.pandocreon.core.game.step.EachPlayerGameStep;
import fr.utt.pandocreon.core.game.step.StepAction;

/**
 * The Class GameStartStep.
 */
public class GameStartStep extends EachPlayerGameStep {
	
	/** The Constant CARDS_TO_DISTRIBUTE. */
	public static final int CARDS_TO_DISTRIBUTE = 7;

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#start()
	 */
	@Override
	public void start() {
		super.start();
		Game game = getGame();
		for (final Player p : game.getAlivePlayers())
			for (int i = 0; i < CARDS_TO_DISTRIBUTE; i++)
				game.getCards().drawActionCard(p.getCards(), p.getType() == PlayerType.HUMAN);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.EachPlayerGameStep#playerStepAction()
	 */
	@Override
	protected StepAction playerStepAction() {
		return new StepAction(this, new DrawCardAction(CardType.DIVINITE));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.EachPlayerGameStep#toString()
	 */
	@Override
	public String toString() {
		return "Début de partie";
	}

}
