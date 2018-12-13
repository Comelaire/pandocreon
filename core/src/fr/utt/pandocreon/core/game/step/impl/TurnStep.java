/*
 * 
 */
package fr.utt.pandocreon.core.game.step.impl;

import fr.utt.pandocreon.core.game.step.CompositeGameStep;
import fr.utt.pandocreon.core.game.step.EachPlayerCompositeStep;

/**
 * The Class TurnStep.
 */
public class TurnStep extends CompositeGameStep {
	
	/** The turns. */
	private EachPlayerCompositeStep turns;
	
	/** The turn. */
	private int player, turn;

	/**
	 * Instantiates a new turn step.
	 */
	public TurnStep() {
		player = -1;
		add(new DiceGameStep());
		add(turns = new EachPlayerCompositeStep(
				new DiscardGameStep(),
				new PlayCardsGameStep()
				));
	}

	/**
	 * Gets the no apocalypse turn.
	 *
	 * @return the no apocalypse turn
	 */
	public int getNoApocalypseTurn() {
		return turn;
	}

	/**
	 * On apocalypse.
	 */
	public void onApocalypse() {
		turn = 0;
	}

	/**
	 * Next player.
	 */
	public void nextPlayer() {
		if (++player >= getGame().getAllPlayers().size())
			player = 0;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#start()
	 */
	@Override
	public void start() {
		do nextPlayer();
		while (getGame().getAllPlayers().get(player).isDead());
		setPlayer(getGame().getAllPlayers().get(player));
		turns.setFirstPlayer(player);
		super.start();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.CompositeGameStep#end()
	 */
	@Override
	public void end() {
		super.end();
		turn++;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.CompositeGameStep#toString()
	 */
	@Override
	public String toString() {
		return "Tour de jeu";
	}

}
