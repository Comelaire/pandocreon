/*
 * 
 */
package fr.utt.pandocreon.java;

import java.util.List;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.ActionDelivererManager;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.ia.IASet;
import fr.utt.pandocreon.core.game.action.impl.LaunchDiceAction;
import fr.utt.pandocreon.core.game.action.impl.PlayCardAction;
import fr.utt.pandocreon.core.game.card.CardType;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;
import fr.utt.pandocreon.java.ui.Sound;
import fr.utt.pandocreon.java.ui.game.MainPlayerGamePanel;
import fr.utt.pandocreon.java.ui.game.TablePanel;

/**
 * The Class UIGameManager.
 */
public class UIGameManager implements GameStepListener {
	
	/** The Constant NEXT_ACTION_DELAY. */
	private static final int NEXT_ACTION_DELAY = 100;//TODO: 1000
	
	/** The manager. */
	private ActionDelivererManager manager;
	
	/** The panel. */
	private MainPlayerGamePanel panel;
	
	/** The player. */
	private Player player;

	/**
	 * Instantiates a new UI game manager.
	 */
	public UIGameManager() {
		manager = new IASet();
	}

	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	public MainPlayerGamePanel getPanel() {
		return panel;
	}

	/**
	 * Sets the player.
	 *
	 * @param table
	 *            the table
	 * @param player
	 *            the player
	 * @return the main player game panel
	 */
	public MainPlayerGamePanel setPlayer(TablePanel table, Player player) {
		if (this.player != null)
			throw new IllegalArgumentException("Only one player can be managed");
		this.player = player;
		return panel = new MainPlayerGamePanel(player, table);
	}

	/**
	 * Bot action.
	 *
	 * @param p
	 *            the p
	 * @param step
	 *            the step
	 * @param action
	 *            the action
	 */
	public void botAction(Player p, GameStep step, StepAction action) {
		List<GameAction> botActions = action.getPossibleActions(p);
		int actionCode = manager.getDeliverer(p).getActionToPerform(p, botActions);
		if (actionCode > -1 && step.isStillValid(action))
			step.performAction(actionCode, action, p);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepStart(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepStart(GameStep step) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepEnd(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepEnd(GameStep step) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepAction(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.step.StepAction)
	 */
	@Override
	public void onStepAction(GameStep step, StepAction action) {
		for (final Player p : step.getGame().getAlivePlayers()) {
			List<GameAction> actions = action.getPossibleActions(p);
			if (actions.size() == 1 && actions.get(0).getType() == ActionType.END_TURN) {
				step.getGame().postAction(() -> step.performAction(0, action, p));
			} else if (!actions.isEmpty()) {
				if (p == player)
					panel.setPossibleActions(actions, actionCode -> step.performAction(actionCode, action, p));
				else if (p.getType() == PlayerType.BOT)
					step.getGame().postAction(() -> botAction(p, step, action), 1500);
			}
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {
		panel.clearActions();
		int delay = NEXT_ACTION_DELAY;
		if (descriptor.getAction().getType() == ActionType.LAUNCH_DICE) {
			step.getGame().postAction(((LaunchDiceAction) descriptor.getAction())::addPoints, delay * 2);
			delay *= 3;
		}
		if (descriptor.getAction().getType() == ActionType.PLAY_CARD &&
				((PlayCardAction) descriptor.getAction()).getCard().getType() == CardType.APOCALYPSE)
			Sound.getInstance().play("jeanne.wav");
		step.getGame().postAction(step.getGame()::next, delay);
	}

}
