/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.Collection;

/**
 * The Interface ResourceResolver.
 */
public interface ResourceResolver {

	/**
	 * Gets the cards.
	 *
	 * @param type
	 *            the type
	 * @return the cards
	 */
	Collection<Card> getCards(CardType type);

}
