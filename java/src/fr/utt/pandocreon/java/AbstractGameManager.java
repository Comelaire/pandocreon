/*
 * 
 */
package fr.utt.pandocreon.java;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Game.GameBuilder;
import fr.utt.pandocreon.core.game.GameListener;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.ActionDeliverer;
import fr.utt.pandocreon.core.game.action.ActionDelivererManager;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;
import fr.utt.pandocreon.core.util.Wrapper;

/**
 * The Class AbstractGameManager.
 */
public abstract class AbstractGameManager implements Runnable, GameListener,
GameStepListener, ActionDelivererManager, ActionDeliverer {
	
	/** The actions. */
	private final List<PossibleAction> actions;
	
	/** The translator. */
	private final ActionTranslator translator;
	
	/** The builder. */
	private final GameBuilder builder;
	
	/** The manager. */
	private ActionDelivererManager manager;
	
	/** The run. */
	private boolean run;
	
	/** The game. */
	private Game game;

	/**
	 * Instantiates a new abstract game manager.
	 *
	 * @param builder
	 *            the builder
	 * @param translator
	 *            the translator
	 */
	public AbstractGameManager(GameBuilder builder, ActionTranslator translator) {
		this.builder = builder;
		this.translator = translator;
		manager = this;
		actions = new ArrayList<>();
	}

	/**
	 * Display actions.
	 */
	public abstract void displayActions();
	
	/**
	 * On error.
	 *
	 * @param error
	 *            the error
	 */
	protected abstract void onError(String error);
	
	/**
	 * Action.
	 *
	 * @return true, if successful
	 */
	protected abstract boolean action();
	
	/**
	 * Adds the player.
	 *
	 * @param type
	 *            the type
	 */
	protected abstract void addPlayer(PlayerType type);

	/**
	 * Sets the manager.
	 *
	 * @param manager
	 *            the new manager
	 */
	public void setManager(ActionDelivererManager manager) {
		this.manager = manager;
	}

	/**
	 * Clean.
	 */
	public void clean() {
		if (game != null) {
			game.removeGameStepListener(this);
			game.removeGameListener(this);
			game.quit();
		}
	}

	/**
	 * Start.
	 *
	 * @param game
	 *            the game
	 */
	public void start(Game game) {
		clean();
		this.game = game;
		game.addGameListener(this);
		game.addGameStepListener(this);
		onStart();
	}

	/**
	 * Start.
	 */
	public void start() {
		start(builder.build());
	}

	/**
	 * On start.
	 */
	public void onStart() {
		computeActions();
	}

	/**
	 * Quit.
	 */
	public void quit() {
		run = false;
	}

	/**
	 * Player creator.
	 *
	 * @param type
	 *            the type
	 * @return the runnable
	 */
	public Runnable playerCreator(PlayerType type) {
		return () -> addPlayer(type);
	}

	/**
	 * Adds the player.
	 *
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @return the player
	 */
	public Player addPlayer(PlayerType type, String name) {
		Player player = new Player(name, type);
		game.add(player);
		computeActions();
		return player;
	}

	/**
	 * Removes the player.
	 *
	 * @param player
	 *            the player
	 */
	public void removePlayer(Player player) {
		game.remove(player);
	}

	/**
	 * Removes the player.
	 */
	public void removePlayer() {
		actions.clear();
		addPossibleAction(() -> {}, "Annuler");
		for (final Player p : game.getAllPlayers())
			addPossibleAction(() -> removePlayer(p), "Retirer " + p);
		displayActions();
		action();
		computeActions();
	}

	/**
	 * Action.
	 *
	 * @param action
	 *            the action
	 * @return true, if successful
	 */
	public boolean action(int action) {
		try {
			if (actions.isEmpty()) {
				onError("Patientez, aucune action possible pour le moment...");
				return false;
			}
			if (action < 0 || action >= actions.size()) {
				onError("Le numéro " + action + " ne correspond à aucune action");
				displayActions();
				return false;
			}
			actions.get(action).action.run();
			return true;
		} catch (NumberFormatException | InputMismatchException err) {
			onError("Entrez un nombre valide");
			return false;
		} catch (IllegalAccessError err) {
			onError("Vous ne pouvez pas jouer pour le moment");
			return false;
		}
	}

	/**
	 * Adds the possible action.
	 *
	 * @param action
	 *            the action
	 * @param text
	 *            the text
	 */
	public void addPossibleAction(Runnable action, String text) {
		actions.add(new PossibleAction(action, text));
	}

	/**
	 * Adds the possible action.
	 *
	 * @param player
	 *            the player
	 * @param action
	 *            the action
	 * @param index
	 *            the index
	 * @param text
	 *            the text
	 */
	public void addPossibleAction(Player player, StepAction action, int index, String text) {
		addPossibleAction(() -> game.getStep().performAction(index, action, player), text);
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public List<PossibleAction> getActions() {
		return actions;
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
	 * Gets the translator.
	 *
	 * @return the translator
	 */
	public ActionTranslator getTranslator() {
		return translator;
	}

	/**
	 * Compute player actions.
	 *
	 * @param startI
	 *            the start I
	 * @param i
	 *            the i
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public boolean computePlayerActions(int startI, Wrapper<Integer> i, Player p) {
		StepAction a = getGame().getStep().getAction();
		List<GameAction> actions = a.getPossibleActions(p);
		if (!actions.isEmpty()) {
			if (p.getType() == PlayerType.HUMAN) {
				for (final GameAction action : actions)
					addPossibleAction(p, a, (i.value++) - startI,
							getTranslator().option(action.getDescriptor()));
			} else if (p.getType() == PlayerType.BOT) {
				int action = manager.getDeliverer(p).getActionToPerform(p, actions);
				if (actions.stream().anyMatch(GameAction::isMultiplePlayers)) {
					addPossibleAction(p, a, action, String.format("Laisser %s jouer", p));
				} else {
					game.postAction(() -> game.getStep().performAction(action, a, p));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Compute manager actions.
	 */
	public void computeManagerActions() {
		Game game = getGame();
		addPossibleAction(playerCreator(PlayerType.HUMAN), "Ajouter un joueur contrôlé");
		addPossibleAction(playerCreator(PlayerType.BOT), "Ajouter un joueur IA");
		if (!game.getAllPlayers().isEmpty())
			addPossibleAction(this::removePlayer, "Retirer un joueur");
		if (game.canStart())
			addPossibleAction(game::next, "Commencer la partie");
	}

	/**
	 * Compute actions.
	 */
	public void computeActions() {
		boolean display = true;
		getActions().clear();
		addPossibleAction(this::quit, "Quitter le jeu");
		if (getGame().isStarted()) {
			int base = getActions().size() - 1;
			Wrapper<Integer> i = new Wrapper<>(0);
			for (final Player p : getGame().getAlivePlayers())
				display = computePlayerActions(base, i, p) && display;
		} else {
			computeManagerActions();
		}
		if (display)
			displayActions();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.ActionDelivererManager#getDeliverer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public ActionDeliverer getDeliverer(Player player) {
		return this;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.ActionDeliverer#getActionToPerform(fr.utt.pandocreon.core.game.Player, java.util.List)
	 */
	@Override
	public int getActionToPerform(Player player, List<GameAction> actions) {
		return new Random().nextInt(actions.size());
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		run = true;
		start();
		while (run) try {
			action();
		} catch (Exception err) {
			err.printStackTrace();
			onError("Error ends game: " + err.getMessage());
			run = false;
		}
		clean();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameStart(fr.utt.pandocreon.core.game.Game)
	 */
	@Override
	public void onGameStart(Game game) {
		game.postAction(game::next, 500);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameEnd(fr.utt.pandocreon.core.game.Game, fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onGameEnd(Game game, Player winner) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onNewPlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onNewPlayer(Player player) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onPlayerLeave(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onPlayerLeave(Player player) {
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
		computeActions();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {
		game.postAction(game::next, 500);
	}


	/**
	 * The Class PossibleAction.
	 */
	protected static class PossibleAction {
		
		/** The action. */
		public Runnable action;
		
		/** The text. */
		public String text;

		/**
		 * Instantiates a new possible action.
		 *
		 * @param action
		 *            the action
		 * @param text
		 *            the text
		 */
		public PossibleAction(Runnable action, String text) {
			this.action = action;
			this.text = text;
		}
	}

}
