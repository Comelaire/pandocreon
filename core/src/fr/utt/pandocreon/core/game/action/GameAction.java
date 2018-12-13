/*
 * 
 */
package fr.utt.pandocreon.core.game.action;

import java.lang.ref.WeakReference;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.StepAction;

/**
 * The Class GameAction.
 */
public abstract class GameAction implements Runnable {
	
	/** The desc. */
	private WeakReference<ActionDescriptor> desc;
	
	/** The step action. */
	private StepAction stepAction;

	/**
	 * Perform action.
	 */
	public abstract void performAction();
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public abstract ActionType getType();

	/**
	 * Checks if is multiple players.
	 *
	 * @return true, if is multiple players
	 */
	public boolean isMultiplePlayers() {
		return false;
	}

	/**
	 * Accept.
	 *
	 * @param player
	 *            the player
	 * @return true, if successful
	 */
	public boolean accept(Player player) {
		Player playing = stepAction.getStep().getPlayer();
		return playing == null || playing == player;
	}

	/**
	 * Gets the descriptor.
	 *
	 * @return the descriptor
	 */
	public ActionDescriptor getDescriptor() {
		ActionDescriptor action = null;
		if (desc != null)
			action = desc.get();
		if (action == null) {
			action = new ActionDescriptor(getType(), this);
			desc = new WeakReference<ActionDescriptor>(action);
		}
		return action;
	}

	/**
	 * Sets the step action.
	 *
	 * @param stepAction
	 *            the new step action
	 */
	public void setStepAction(StepAction stepAction) {
		this.stepAction = stepAction;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return stepAction.getGame();
	}

	/**
	 * Gets the step action.
	 *
	 * @return the step action
	 */
	public StepAction getStepAction() {
		return stepAction;
	}

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public GameStep getStep() {
		return getStepAction().getStep();
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return getStep().getPlayer();
	}

	/**
	 * Checks if is human player.
	 *
	 * @return true, if is human player
	 */
	public boolean isHumanPlayer() {
		return getPlayer().getType() == PlayerType.HUMAN;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			performAction();
		} catch (Exception err) {
			err.printStackTrace();
		}
		getGame().notifyActionPerformed(getDescriptor());
	}

}
