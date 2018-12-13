/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import java.util.Random;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.GameAction;

/**
 * The Class LaunchDiceAction.
 */
public class LaunchDiceAction extends GameAction {
	
	/** The random. */
	private final Random random;
	
	/** The launch. */
	private Origine launch;

	/**
	 * Instantiates a new launch dice action.
	 */
	public LaunchDiceAction() {
		random = new Random();
	}

	/**
	 * Gets the launch.
	 *
	 * @return the launch
	 */
	public Origine getLaunch() {
		return launch;
	}

	/**
	 * Adds the points.
	 *
	 * @param game
	 *            the game
	 */
	public void addPoints(Game game) {
		if (launch == null)
			throw new IllegalAccessError("Dice must be launched first with a call to performAction");
		for (final Player player : game.getAlivePlayers())
			player.onDiceLaunch(launch);
	}

	/**
	 * Adds the points.
	 */
	public void addPoints() {
		addPoints(getGame());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#performAction()
	 */
	@Override
	public void performAction() {
		launch = Origine.ACTIONS[random.nextInt(Origine.ACTIONS.length)];
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.LAUNCH_DICE;
	}

}
