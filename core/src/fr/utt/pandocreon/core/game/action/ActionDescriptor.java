/*
 * 
 */
package fr.utt.pandocreon.core.game.action;

/**
 * The Class ActionDescriptor.
 */
public class ActionDescriptor {
	
	/** The action. */
	private GameAction action;
	
	/** The type. */
	private ActionType type;

	/**
	 * Instantiates a new action descriptor.
	 *
	 * @param type
	 *            the type
	 * @param action
	 *            the action
	 */
	public ActionDescriptor(ActionType type, GameAction action) {
		this.type = type;
		this.action = action;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public GameAction getAction() {
		return action;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public ActionType getType() {
		return type;
	}

}
