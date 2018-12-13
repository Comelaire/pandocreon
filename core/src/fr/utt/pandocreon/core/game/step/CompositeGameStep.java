/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player;

/**
 * The Class CompositeGameStep.
 */
public class CompositeGameStep extends GameStep {
	
	/** The steps. */
	private List<GameStep> steps;
	
	/** The skipped. */
	private int index, lastIndex, skipped;
	
	/** The can play cache. */
	private Boolean canPlayCache;
	
	/** The last. */
	private GameStep last;

	/**
	 * Instantiates a new composite game step.
	 */
	public CompositeGameStep() {
		steps = new ArrayList<>();
		index = -1;
	}

	/**
	 * Instantiates a new composite game step.
	 *
	 * @param steps
	 *            the steps
	 */
	public CompositeGameStep(GameStep... steps) {
		this();
		Arrays.stream(steps).forEach(this::add);
	}

	/**
	 * Adds the.
	 *
	 * @param step
	 *            the step
	 */
	public void add(GameStep step) {
		steps.add(step);
		step.setGame(getGame());
	}

	/**
	 * Adds the.
	 *
	 * @param steps
	 *            the steps
	 */
	public void add(GameStep... steps) {
		for (final GameStep step : steps)
			add(step);
	}

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public GameStep getStep() {
		return steps.get(index);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getFinalPlayer()
	 */
	@Override
	public Player getFinalPlayer() {
		return getStep().getFinalPlayer();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#setPlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void setPlayer(Player player) {
		super.setPlayer(player);
		for (final GameStep step : steps)
			step.setPlayer(player);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#end()
	 */
	@Override
	public void end() {
		if (lastIndex > -1 && lastIndex < steps.size())
			steps.get(lastIndex).end();
		super.end();
		restore();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#setGame(fr.utt.pandocreon.core.game.Game)
	 */
	@Override
	public void setGame(Game game) {
		super.setGame(game);
		steps.forEach(step -> step.setGame(game));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#restore()
	 */
	@Override
	public void restore() {
		super.restore();
		for (final GameStep step : steps)
			step.restore();
		index = -1;
		canPlayCache = null;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#canPlay()
	 */
	@Override
	public boolean canPlay() {
		if (canPlayCache != null)
			return canPlayCache;
		lastIndex = index;
		skipped = 0;
		if (index < 0) {
			index = 0;
			skipped = Math.abs(lastIndex);
		}
		while (index < steps.size() && !steps.get(index).canPlay()) {
			index++;
			skipped++;
		}
		return canPlayCache = index < steps.size() && super.canPlay();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getStepAction()
	 */
	@Override
	public StepAction getStepAction() {
		canPlayCache = null;
		GameStep step = getStep();
		if (skipped > 0) {
			skipped = 0;
			if (last != null) {
				last.end();
				last = null;
			}
			step.start();
		}
		last = step;
		return step.getStepAction();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Plusieurs actions à effectuer";
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#fullString()
	 */
	@Override
	public String fullString() {
		return String.format("<html><b>%s</b><hr>%s<br><b>Tour %d sur %d</b></html>",
				toString(), getPlayer() == null ? "" : "Joueur : " + getPlayer(), index + 1, steps.size());
	}

}
