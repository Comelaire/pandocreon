/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.EventListener;

/**
 * The listener interface for receiving card events. The class that is
 * interested in processing a card event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addCardListener<code> method. When the card event occurs,
 * that object's appropriate method is invoked.
 *
 * @see CardEvent
 */
public interface CardListener extends EventListener {
	
	/**
	 * On card movement.
	 *
	 * @param card
	 *            the card
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param visible
	 *            the visible
	 */
	void onCardMovement(Card card, CardContainer source, CardContainer target, boolean visible);
	
	/**
	 * On card added.
	 *
	 * @param card
	 *            the card
	 * @param target
	 *            the target
	 */
	void onCardAdded(Card card, CardContainer target);
}
