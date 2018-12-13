/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

/**
 * The Class OneActionGameStep.
 */
public abstract class OneActionGameStep extends GameStep {
	
	/** The has run. */
	private boolean hasRun;

	/**
	 * Gets the one time step action.
	 *
	 * @return the one time step action
	 */
	protected abstract StepAction getOneTimeStepAction();

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#getStepAction()
	 */
	@Override
	protected StepAction getStepAction() {
		hasRun = true;
		return getOneTimeStepAction();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#end()
	 */
	@Override
	public void end() {
		hasRun = false;
		super.end();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStep#canPlay()
	 */
	@Override
	public boolean canPlay() {
		return !hasRun;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Action réalisable une fois par tour";
	}

}
