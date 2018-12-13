/*
 * 
 */
package fr.utt.pandocreon.core.game.step.impl;

import fr.utt.pandocreon.core.game.action.impl.LaunchDiceAction;
import fr.utt.pandocreon.core.game.step.OneActionGameStep;
import fr.utt.pandocreon.core.game.step.StepAction;

/**
 * The Class DiceGameStep.
 */
public class DiceGameStep extends OneActionGameStep {

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.OneActionGameStep#toString()
	 */
	@Override
	public String toString() {
		return "Lancé de dé";
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.OneActionGameStep#getOneTimeStepAction()
	 */
	@Override
	protected StepAction getOneTimeStepAction() {
		return new StepAction(this, new LaunchDiceAction());
	}

}
