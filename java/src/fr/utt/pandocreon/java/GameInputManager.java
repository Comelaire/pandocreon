/*
 * 
 */
package fr.utt.pandocreon.java;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Game.GameBuilder;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.impl.LaunchDiceAction;
import fr.utt.pandocreon.core.game.step.GameStep;

/**
 * The Class GameInputManager.
 */
public class GameInputManager extends AbstractGameManager {
	
	/** The in. */
	private final Scanner in;
	
	/** The log. */
	private GameLogger log;
	
	/** The hide actions. */
	private boolean hideActions;

	/**
	 * Instantiates a new game input manager.
	 *
	 * @param builder
	 *            the builder
	 */
	public GameInputManager(GameBuilder builder) {
		super(builder, new ActionTranslator());
		in = new Scanner(System.in);
		log = new GameLogger();
	}

	/**
	 * Quick start.
	 */
	public void quickStart() {
		hideActions = true;
		addPlayer(PlayerType.HUMAN, "Joueur humain");
		Game game = getGame();
		for (int i = 1; i < 4; i++)
			addPlayer(PlayerType.BOT, "Bot " + i);
		hideActions = false;
		game.postAction(game::next, 500);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#computeManagerActions()
	 */
	@Override
	public void computeManagerActions() {
		addPossibleAction(this::quickStart, "Démarrer une partie rapide");
		super.computeManagerActions();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#clean()
	 */
	@Override
	public void clean() {
		Game game = getGame();
		if (game != null) {
			game.removeGameStepListener(log);
			game.removeGameListener(log);
			game.getCards().removeCardListener(log);
			game.getCards().getDiscard().removeCardListener(log);
		}
		super.clean();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#onStart()
	 */
	@Override
	public void onStart() {
		Game game = getGame();
		game.addGameStepListener(log);
		game.addGameListener(log);
		game.getCards().addCardListener(log);
		game.getCards().getDiscard().addCardListener(log);
		log.display("~ Pandocréon Divinae ~");
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#onError(java.lang.String)
	 */
	@Override
	protected void onError(String error) {
		log.display(error);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#addPlayer(fr.utt.pandocreon.core.game.Player.PlayerType)
	 */
	@Override
	public void addPlayer(PlayerType type) {
		log.inline("Nom du joueur : ");
		addPlayer(type, log.log(in.next(), true));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#addPlayer(fr.utt.pandocreon.core.game.Player.PlayerType, java.lang.String)
	 */
	@Override
	public Player addPlayer(PlayerType type, String name) {
		Player player = super.addPlayer(type, name);
		player.addPlayerListener(log);
		player.getCards().addCardListener(log);
		return player;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#removePlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void removePlayer(Player player) {
		super.removePlayer(player);
		player.removePlayerListener(log);
		player.getCards().removeCardListener(log);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#action()
	 */
	@Override
	public boolean action() {
		try {
			int action = Integer.valueOf(in.next());
			log.log(String.valueOf(action), true);
			return action(action);
		} catch (NumberFormatException err) {
			onError("Saisie invalide");
			displayActions();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#displayActions()
	 */
	@Override
	public void displayActions() {
		if (!hideActions) {
			List<PossibleAction> actions = getActions();
			String[] str = new String[actions.size()];
			int maxWidth = 0;
			for (int i = 0; i < actions.size(); i++) {
				str[i] = String.format("%d) %s", i, actions.get(i).text);
				maxWidth = Math.max(str[i].length(), maxWidth);
			}
			final int w = maxWidth;
			log.format("", maxWidth, 3, 5, '-', 'o');
			Arrays.stream(str).forEach(s -> log.format(s, w, 3, 5, ' ', '|'));
			log.format("", maxWidth, 3, 5, '-', 'o');
			log.inline("    '--> Votre choix : ");
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#run()
	 */
	@Override
	public void run() {
		super.run();
		log.display("A bientôt !");
		log.close();
		in.close();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.AbstractGameManager#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {
		if (descriptor.getAction().getType() == ActionType.LAUNCH_DICE)
			((LaunchDiceAction) descriptor.getAction()).addPoints();
		log.display(getTranslator().action(descriptor));
		super.onActionPerformed(step, descriptor);
	}


}
