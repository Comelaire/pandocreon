/*
 * 
 */
package fr.utt.pandocreon.java.ui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;
import fr.utt.pandocreon.java.ui.component.Panel;

/**
 * The Class StepProgressComponent.
 */
public class StepProgressComponent extends Panel implements GameStepListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The steps. */
	private final List<GameStep> steps;

	/**
	 * Instantiates a new step progress component.
	 */
	public StepProgressComponent() {
		super(new FlowLayout(FlowLayout.LEFT));
		steps = new ArrayList<>();
		setPreferredSize(new Dimension(0, 30));
	}

	/**
	 * Adds the arrow.
	 */
	public void addArrow() {
		JLabel arrow = new JLabel(" > ");
		arrow.setForeground(Color.GRAY);
		add(arrow);
	}

	/**
	 * Update.
	 */
	public void update() {
		removeAll();
		Player lastPlayer = null;
		for (final GameStep step : steps) {
			addArrow();
			JLabel l;
			add(l = new JLabel(step.toString()));
			l.setToolTipText(step.fullString());
			Player p = step.getPlayer();
			if (p != null && p != lastPlayer) {
				l = new JLabel(" " + p.toString() + " ");
				l.setOpaque(true);
				l.setBackground(Color.DARK_GRAY);
				l.setForeground(Color.WHITE);
				addArrow();
				add(l);
				lastPlayer = p;
			}
		}
		validate();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepStart(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepStart(GameStep step) {
		steps.add(step);
		update();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepEnd(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepEnd(GameStep step) {
		steps.remove(step);
		update();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepAction(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.step.StepAction)
	 */
	@Override
	public void onStepAction(GameStep step, StepAction action) {

	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {

	}

}
