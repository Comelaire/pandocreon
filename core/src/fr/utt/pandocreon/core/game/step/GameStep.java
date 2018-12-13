/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

import java.util.List;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;

/**
 * The Class GameStep.
 */
public abstract class GameStep {
	
	/** The player. */
	private Player player;
	
	/** The game. */
	private Game game;
	
	/** The action. */
	private StepAction action;
	
	/** The stopped. */
	private boolean stopped;

	/**
	 * Gets the step action.
	 *
	 * @return the step action
	 */
	protected abstract StepAction getStepAction();

	/**
	 * Can play.
	 *
	 * @return true, if successful
	 */
	public boolean canPlay() {
		return !stopped;
	}

	/**
	 * Restore.
	 */
	public void restore() {
		stopped = false;
	}

	/**
	 * Stop.
	 */
	public void stop() {
		stopped = true;
	}

	/**
	 * Sets the player.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the final player.
	 *
	 * @return the final player
	 */
	public Player getFinalPlayer() {
		return player;
	}

	/**
	 * Gets the player index.
	 *
	 * @return the player index
	 */
	public int getPlayerIndex() {
		return game.getAllPlayers().indexOf(getPlayer());
	}

	/**
	 * Start.
	 */
	public void start() {
		game.notifyStartStep(this);
	}

	/**
	 * End.
	 */
	public void end() {
		stopped = false;
		game.notifyEndStep(this);
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public final StepAction getAction() {
		if (action == null)
			action = getStepAction();
		return action;
	}

	/**
	 * Checks if is still valid.
	 *
	 * @param caller
	 *            the caller
	 * @return true, if is still valid
	 */
	public boolean isStillValid(StepAction caller) {
		return action == caller;
	}

	/**
	 * Perform action.
	 *
	 * @param actionCode
	 *            the action code
	 * @param caller
	 *            the caller
	 * @param player
	 *            the player
	 */
	public void performAction(int actionCode, StepAction caller, Player player) {
		if (action == null)
			throw new IllegalAccessError("No action can be performed for now");
		if (!isStillValid(caller))
			throw new IllegalArgumentException(caller + " can't perform action on " + action);
		List<GameAction> actions = action.getPossibleActions(player);
		if (actionCode < 0 || actionCode >= actions.size())
			throw new IllegalArgumentException(String.format("Action code out of range: %d", actionCode));
		action = null;
		getGame().postAction(actions.get(actionCode));
	}

	/**
	 * Sets the game.
	 *
	 * @param game
	 *            the new game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Full string.
	 *
	 * @return the string
	 */
	public String fullString() {
		return String.format("<html><b>%s</b><hr>%s</html>",
				toString(), player == null ? "" : "Joueur : " + player);
	}

}