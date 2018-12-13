/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;

/**
 * The Class StepAction.
 */
public class StepAction {
	
	/** The possible actions. */
	private final List<GameAction> possibleActions;
	
	/** The actions cache. */
	private final Map<Player, List<GameAction>> actionsCache;
	
	/** The step. */
	private final GameStep step;


	/**
	 * Instantiates a new step action.
	 *
	 * @param step
	 *            the step
	 */
	public StepAction(GameStep step) {
		this.step = step;
		possibleActions = new ArrayList<>();
		actionsCache = new HashMap<>();
	}

	/**
	 * Instantiates a new step action.
	 *
	 * @param step
	 *            the step
	 * @param actions
	 *            the actions
	 */
	public StepAction(GameStep step, GameAction... actions) {
		this(step);
		Arrays.stream(actions).forEach(this::add);
	}

	/**
	 * Adds the.
	 *
	 * @param action
	 *            the action
	 * @return the step action
	 */
	public StepAction add(GameAction action) {
		possibleActions.add(action);
		action.setStepAction(this);
		return this;
	}

	/**
	 * Adds the.
	 *
	 * @param actions
	 *            the actions
	 * @return the step action
	 */
	public StepAction add(List<GameAction> actions) {
		actions.forEach(this::add);
		return this;
	}

	/**
	 * Gets the possible actions.
	 *
	 * @param player
	 *            the player
	 * @return the possible actions
	 */
	public List<GameAction> getPossibleActions(Player player) {
		List<GameAction> actions = actionsCache.get(player);
		if (actions == null) {
			actions = new ArrayList<>();
			possibleActions.stream().filter(action -> action.accept(player)).forEach(actions::add);
			actionsCache.put(player, actions);
		}
		return actions;
	}

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public GameStep getStep() {
		return step;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return step.getGame();
	}

}
