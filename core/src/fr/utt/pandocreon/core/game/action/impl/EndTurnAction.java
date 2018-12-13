/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.GameAction;

/**
 * The Class EndTurnAction.
 */
public class EndTurnAction extends GameAction {

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#performAction()
	 */
	@Override
	public void performAction() {
		getStep().stop();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.END_TURN;
	}

}
