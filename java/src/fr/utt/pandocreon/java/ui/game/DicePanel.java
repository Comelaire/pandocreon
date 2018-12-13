/*
 * 
 */
package fr.utt.pandocreon.java.ui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.impl.LaunchDiceAction;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;
import fr.utt.pandocreon.java.ui.Images;

/**
 * The Class DicePanel.
 */
public class DicePanel extends JPanel implements GameStepListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant IMAGES. */
	private static final BufferedImage[] IMAGES;
	
	/** The origine. */
	private Origine origine;
	
	/** The progress. */
	private int progress;

	static {
		IMAGES = new BufferedImage[Origine.ACTIONS.length];
		for (int i = 0; i < IMAGES.length; i++)
			IMAGES[i] = Images.getInstance().getImage(
					Origine.ACTIONS[i].toString().toLowerCase() + ".png");
	}

	/**
	 * Instantiates a new dice panel.
	 */
	public DicePanel() {
		setOpaque(false);
	}

	/**
	 * Sets the origine.
	 *
	 * @param origine
	 *            the new origine
	 */
	public void setOrigine(Origine origine) {
		this.origine = origine;
		setToolTipText(String.format("<html>Résultat du<br>lancer de dé :<h2>%s</h2></html>",
				origine.readable()));
		progress = 15;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (origine != null) {
			int image;
			if (progress > 0) {
				progress--;
				image = progress % IMAGES.length;
				g.setClip(null);
				g.setColor(new Color(255, 255, 255, 50));
				for (int i = 1; i < progress; i++)
					g.fillOval(-getWidth() * i/2 + getWidth()/4, -getHeight() * i/2 + getHeight()/4,
							getWidth() * i, getHeight() * i);
			} else {
				image = origine.getIndex();
			}
			g.drawImage(IMAGES[image], 0, 0, getWidth(), getHeight(), null);
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {
		if (descriptor.getType() == ActionType.LAUNCH_DICE)
			setOrigine(((LaunchDiceAction) descriptor.getAction()).getLaunch());
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
	}

}
