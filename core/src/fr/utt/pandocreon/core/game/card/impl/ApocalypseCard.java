/*
 * 
 */
package fr.utt.pandocreon.core.game.card.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.CardAction;
import fr.utt.pandocreon.core.game.action.impl.PlayCardAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class ApocalypseCard.
 */
public class ApocalypseCard extends ActionCard {

	/**
	 * Instantiates a new apocalypse card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public ApocalypseCard(Map<String, String> attributes) {
		super(attributes);
		attributes.put("effect", "apocalypse");
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ActionCard#onCardUsed(fr.utt.pandocreon.core.game.action.impl.CardAction)
	 */
	@Override
	public void onCardUsed(CardAction action) {
		super.onCardUsed(action);
		doEffect();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.util.FlexibleObject#getName()
	 */
	@Override
	public String getName() {
		return "Apocalypse";
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#getType()
	 */
	@Override
	public CardType getType() {
		return CardType.APOCALYPSE;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ActionCard#addPossibleActions(fr.utt.pandocreon.core.game.Player, boolean, java.util.function.Consumer)
	 */
	@Override
	public void addPossibleActions(Player player, boolean playerTurn, Consumer<GameAction> consumer) {
		if (playerTurn && player.getGame().getTurnStep().getNoApocalypseTurn() > 0)
			consumer.accept(new PlayCardAction(this, new HashMap<>()));
	}

}
