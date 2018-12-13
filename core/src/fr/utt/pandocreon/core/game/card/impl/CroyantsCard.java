/*
 * 
 */
package fr.utt.pandocreon.core.game.card.impl;

import java.util.Map;
import java.util.function.Consumer;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.SacrificeCardAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class CroyantsCard.
 */
public class CroyantsCard extends ActionCard {
	
	/** The guide. */
	private GuideSpirituelCard guide;

	/**
	 * Instantiates a new croyants card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public CroyantsCard(Map<String, String> attributes) {
		super(attributes);
	}

	/**
	 * Sets the guide.
	 *
	 * @param guide
	 *            the new guide
	 */
	public void setGuide(GuideSpirituelCard guide) {
		this.guide = guide;
	}

	/**
	 * Gets the guide.
	 *
	 * @return the guide
	 */
	public GuideSpirituelCard getGuide() {
		return guide;
	}

	/**
	 * Checks if is guided.
	 *
	 * @return true, if is guided
	 */
	public boolean isGuided() {
		return guide != null;
	}

	/**
	 * Gets the croyants count.
	 *
	 * @return the croyants count
	 */
	public int getCroyantsCount() {
		return Integer.valueOf(get("count", "1"));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#onSacrifice()
	 */
	@Override
	public void onSacrifice() {
		super.onSacrifice();
		guide.remove(this);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ActionCard#addPossibleActions(fr.utt.pandocreon.core.game.Player, boolean, java.util.function.Consumer)
	 */
	@Override
	public void addPossibleActions(Player player, boolean playerTurn, Consumer<GameAction> consumer) {
		if (!isGuided())
			super.addPossibleActions(player, playerTurn, consumer);
		else if(playerTurn)
			consumer.accept(new SacrificeCardAction(this));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#getType()
	 */
	@Override
	public CardType getType() {
		return CardType.CROYANTS;
	}

}
