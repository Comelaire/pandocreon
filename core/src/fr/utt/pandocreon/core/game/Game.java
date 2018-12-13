/*
 * 
 */
package fr.utt.pandocreon.core.game;

import java.util.ArrayList;
import java.util.List;

import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.card.ResourceResolver;
import fr.utt.pandocreon.core.game.card.TypedCardSet;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;
import fr.utt.pandocreon.core.game.step.impl.GameStartStep;
import fr.utt.pandocreon.core.game.step.impl.TurnStep;
import fr.utt.pandocreon.core.util.Listenable;

/**
 * The Class Game.
 */
public class Game extends Listenable {
	
	/** The steps. */
	private final List<GameStep> steps;
	
	/** The players. */
	private final List<Player> players;
	
	/** The cards. */
	private final TypedCardSet cards;
	
	/** The table. */
	private final Table table;
	
	/** The action index. */
	private int stepIndex, turn, actionIndex;

	/**
	 * Instantiates a new game.
	 *
	 * @param res
	 *            the res
	 */
	private Game(ResourceResolver res) {
		steps = new ArrayList<>();
		players = new ArrayList<>();
		cards = new TypedCardSet(res);
		table = new Table(this);
		stepIndex = -1;
	}

	/**
	 * End.
	 *
	 * @param winner
	 *            the winner
	 */
	public void end(Player winner) {
		notify(GameListener.class, l -> l.onGameEnd(this, winner));
	}

	/**
	 * Do apocalypse.
	 */
	public void doApocalypse() {
		boolean noEquals = true;
		if (players.size() < 4) {
			Player winner = null;
			int max = Integer.MIN_VALUE;
			for (final Player p : getAlivePlayers()) {
				int prayers = p.getPrayerCount();
				if (prayers > max) {
					winner = p;
					max = prayers;
					noEquals = true;
				} else if (prayers == max) {
					noEquals = false;
				}
			}
			if (noEquals)
				end(winner);
		} else {
			Player dead = null;
			int min = Integer.MAX_VALUE;
			for (final Player p : getAlivePlayers()) {
				int prayers = p.getPrayerCount();
				if (prayers < min) {
					dead = p;
					min = prayers;
					noEquals = true;
				} else if (prayers == min) {
					noEquals = false;
				}
			}
			if (noEquals)
				dead.setDead();
			getTurnStep().onApocalypse();
		}
	}

	/**
	 * Gets the turn step.
	 *
	 * @return the turn step
	 */
	public TurnStep getTurnStep() {
		for (final GameStep step : steps)
			if (step instanceof TurnStep)
				return (TurnStep) step;
		throw new IllegalAccessError("No turn step");
	}

	/**
	 * Adds the.
	 *
	 * @param step
	 *            the step
	 * @return the game
	 */
	public Game add(GameStep step) {
		step.setGame(this);
		steps.add(step);
		return this;
	}

	/**
	 * Adds the.
	 *
	 * @param player
	 *            the player
	 * @return the game
	 */
	public Game add(Player player) {
		players.add(player);
		player.setGame(this);
		notify(GameListener.class, l -> l.onNewPlayer(player));
		return this;
	}

	/**
	 * Removes the.
	 *
	 * @param player
	 *            the player
	 * @return the game
	 */
	public Game remove(Player player) {
		players.remove(player);
		notify(GameListener.class, l -> l.onPlayerLeave(player));
		return this;
	}

	/**
	 * Adds the.
	 *
	 * @param steps
	 *            the steps
	 * @return the game
	 */
	public Game add(GameStep... steps) {
		for (final GameStep step : steps)
			add(step);
		return this;
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Gets the cards.
	 *
	 * @return the cards
	 */
	public TypedCardSet getCards() {
		return cards;
	}

	/**
	 * Can start.
	 *
	 * @return true, if successful
	 */
	public boolean canStart() {
		return players.size() > 1;
	}

	/**
	 * Quit.
	 */
	public void quit() {
		clean();
	}

	/**
	 * Gets the action index.
	 *
	 * @return the action index
	 */
	public int getActionIndex() {
		return actionIndex;
	}

	/**
	 * Next.
	 */
	public void next() {
		if (isStarted()) {
			GameStep last = getStep();
			int stepsSkiped = 0;
			while(!getStep().canPlay() && stepsSkiped < steps.size()) {
				stepsSkiped++;
				if (++stepIndex >= steps.size()) {
					stepIndex = 0;
					turn++;
				}
			}
			if (actionIndex == 0) {
				getStep().start();
			} else if (stepsSkiped > 0) {
				last.end();
				if (getStep().canPlay())
					getStep().start();
			}
			if (getStep().canPlay())
				stepAction();
			else System.out.println("TODO: cannot play anymore");
		} else {
			notify(GameListener.class, l -> l.onGameStart(this));
			stepIndex = 0;
		}
	}

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public GameStep getStep() {
		return steps.get(stepIndex);
	}

	/**
	 * Checks if is started.
	 *
	 * @return true, if is started
	 */
	public boolean isStarted() {
		return stepIndex > -1;
	}

	/**
	 * Gets the turn.
	 *
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	public List<Player> getAllPlayers() {
		return players;
	}

	/**
	 * Gets the alive players.
	 *
	 * @return the alive players
	 */
	public List<Player> getAlivePlayers() {
		List<Player> pl = new ArrayList<>();
		for (final Player p : players)
			if (!p.isDead())
				pl.add(p);
		return pl;
	}

	/**
	 * Gets the players.
	 *
	 * @param alives
	 *            the alives
	 * @return the players
	 */
	public List<Player> getPlayers(boolean alives) {
		return alives ? getAlivePlayers() : getAllPlayers();
	}

	/**
	 * Notify start step.
	 *
	 * @param step
	 *            the step
	 */
	public void notifyStartStep(GameStep step) {
		notify(GameStepListener.class, l -> l.onStepStart(step));
	}

	/**
	 * Notify end step.
	 *
	 * @param step
	 *            the step
	 */
	public void notifyEndStep(GameStep step) {
		notify(GameStepListener.class, l -> l.onStepEnd(step));
	}

	/**
	 * Step action.
	 */
	public void stepAction() {
		actionIndex++;
		GameStep step = getStep();
		StepAction action = step.getAction();
		notify(GameStepListener.class, l -> l.onStepAction(step, action));
	}

	/**
	 * Notify action performed.
	 *
	 * @param descriptor
	 *            the descriptor
	 */
	public void notifyActionPerformed(ActionDescriptor descriptor) {
		GameStep step = getStep();
		notify(GameStepListener.class, l -> l.onActionPerformed(step, descriptor));
	}

	/**
	 * Adds the game step listener.
	 *
	 * @param l
	 *            the l
	 */
	public void addGameStepListener(GameStepListener l) {
		addListener(GameStepListener.class, l);
	}

	/**
	 * Removes the game step listener.
	 *
	 * @param l
	 *            the l
	 */
	public void removeGameStepListener(GameStepListener l) {
		removeListener(GameStepListener.class, l);
	}

	/**
	 * Adds the game listener.
	 *
	 * @param l
	 *            the l
	 */
	public void addGameListener(GameListener l) {
		addListener(GameListener.class, l);
	}

	/**
	 * Removes the game listener.
	 *
	 * @param l
	 *            the l
	 */
	public void removeGameListener(GameListener l) {
		removeListener(GameListener.class, l);
	}


	/**
	 * The Class GameBuilder.
	 */
	public static class GameBuilder {
		
		/** The res. */
		private ResourceResolver res;

		/**
		 * Instantiates a new game builder.
		 *
		 * @param res
		 *            the res
		 */
		public GameBuilder(ResourceResolver res) {
			this.res = res;
		}

		/**
		 * Builds the.
		 *
		 * @return the game
		 */
		public Game build() {
			return new Game(res).add(
					new GameStartStep(),
					new TurnStep()
					);
		}
	}


}
