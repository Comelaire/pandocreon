/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;

/**
 * The Class EachPlayerCompositeStep.
 */
public class EachPlayerCompositeStep extends EachPlayerGameStep {
	
	/** The steps. */
	private CompositeGameStep steps;
	
	/** The has played. */
	private int hasPlayed;

	/**
	 * Instantiates a new each player composite step.
	 *
	 * @param steps
	 *            the steps
	 */
	public EachPlayerCompositeStep(GameStep... steps) {
		this.steps = new CompositeGameStep(steps);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#setGame(fr.utt.pandocreon.core.game.Game)
	 */
	@Override
	public void setGame(Game game) {
		super.setGame(game);
		steps.setGame(game);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#restore()
	 */
	@Override
	public void restore() {
		super.restore();
		steps.restore();
		hasPlayed = 0;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#end()
	 */
	@Override
	public void end() {
		super.end();
		restore();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#setPlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void setPlayer(Player player) {
		super.setPlayer(player);
		steps.setPlayer(player);
		hasPlayed++;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getFinalPlayer()
	 */
	@Override
	public Player getFinalPlayer() {
		return steps.getFinalPlayer();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.EachPlayerGameStep#canPlay()
	 */
	@Override
	public boolean canPlay() {
		if (hasPlayed >= getGame().getAllPlayers().size())
			return false;
		if (steps.canPlay())
			return true;
		do {
			steps.restore();
			do nextPlayer();
			while (getPlayer().isDead());
			if (steps.canPlay())
				return true;
		} while(super.canPlay());
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.EachPlayerGameStep#getStepAction()
	 */
	@Override
	protected StepAction getStepAction() {
		return playerStepAction();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.EachPlayerGameStep#playerStepAction()
	 */
	@Override
	protected StepAction playerStepAction() {
		return steps.getStepAction();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.EachPlayerGameStep#toString()
	 */
	@Override
	public String toString() {
		return "Tour de jeu des joueurs";
	}

}
