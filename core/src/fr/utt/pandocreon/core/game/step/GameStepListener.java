/*
 * 
 */
package fr.utt.pandocreon.core.game.step;

import java.util.EventListener;

import fr.utt.pandocreon.core.game.action.ActionDescriptor;

/**
 * The listener interface for receiving gameStep events. The class that is
 * interested in processing a gameStep event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addGameStepListener<code> method. When the gameStep event
 * occurs, that object's appropriate method is invoked.
 *
 * @see GameStepEvent
 */
public interface GameStepListener extends EventListener {

	/**
	 * On step start.
	 *
	 * @param step
	 *            the step
	 */
	void onStepStart(GameStep step);
	
	/**
	 * On step end.
	 *
	 * @param step
	 *            the step
	 */
	void onStepEnd(GameStep step);
	
	/**
	 * On step action.
	 *
	 * @param step
	 *            the step
	 * @param action
	 *            the action
	 */
	void onStepAction(GameStep step, StepAction action);
	
	/**
	 * On action performed.
	 *
	 * @param step
	 *            the step
	 * @param descriptor
	 *            the descriptor
	 */
	void onActionPerformed(GameStep step, ActionDescriptor descriptor);

}
