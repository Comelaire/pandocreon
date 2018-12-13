/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

/**
 * The Class EachPlayerGameStep.
 */
public abstract class EachPlayerGameStep extends GameStep {
	
	/** The has played. */
	private int playerIndex, hasPlayed;

	/**
	 * Instantiates a new each player game step.
	 */
	public EachPlayerGameStep() {
		playerIndex = -1;
	}

	/**
	 * Player step action.
	 *
	 * @return the step action
	 */
	protected abstract StepAction playerStepAction();

	/**
	 * Sets the first player.
	 *
	 * @param playerIndex
	 *            the new first player
	 */
	public void setFirstPlayer(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	/**
	 * End player.
	 */
	public void endPlayer() {
		boolean set = true;
		while(getGame().getAllPlayers().get(playerIndex).isDead()) {
			nextPlayer();
			set = false;
		}
		if (set)
			setPlayer(getGame().getAllPlayers().get(playerIndex));
		if (++playerIndex >= getGame().getAllPlayers().size())
			playerIndex = 0;
		hasPlayed++;
	}

	/**
	 * Next player.
	 */
	public void nextPlayer() {
		hasPlayed++;
		if (++playerIndex >= getGame().getAllPlayers().size())
			playerIndex = 0;
		setPlayer(getGame().getAllPlayers().get(playerIndex));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#canPlay()
	 */
	@Override
	public boolean canPlay() {
		if (playerIndex == -1)
			setFirstPlayer(Math.max(getPlayerIndex(), 0));
		return hasPlayed < getGame().getAllPlayers().size();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getStepAction()
	 */
	@Override
	protected StepAction getStepAction() {
		endPlayer();
		return playerStepAction();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Chaque joueur joue";
	}

}
