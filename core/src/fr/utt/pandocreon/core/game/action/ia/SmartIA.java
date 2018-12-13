/*
 * 
 */
package fr.utt.pandocreon.core.game.action.ia;

import java.util.List;
import java.util.Random;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.ActionDeliverer;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.DiscardCardAction;
import fr.utt.pandocreon.core.game.action.impl.PlayCardAction;
import fr.utt.pandocreon.core.game.action.impl.TakeCroyantsCardAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardType;
import fr.utt.pandocreon.core.game.effect.GivePointEffect;

/**
 * The Class SmartIA.
 */
public class SmartIA implements ActionDeliverer {
	
	/** The random. */
	private final Random random;

	/**
	 * Instantiates a new smart IA.
	 */
	public SmartIA() {
		random = new Random();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.ActionDeliverer#getActionToPerform(fr.utt.pandocreon.core.game.Player, java.util.List)
	 */
	@Override
	public int getActionToPerform(Player player, List<GameAction> actions) {
		if (!player.isPlaying() && random.nextBoolean())
			return -1;
		int bestScore = Integer.MIN_VALUE;
		int bestAction = -1;
		for (int i = 0; i < actions.size(); i++) {
			int score = getScore(player, actions.get(i));
			if (bestAction == -1 || bestScore < score) {
				bestScore = score;
				bestAction = i;
			}
		}
		return bestAction;
	}

	/**
	 * Gets the score.
	 *
	 * @param player
	 *            the player
	 * @param ga
	 *            the ga
	 * @return the score
	 */
	public int getScore(Player player, GameAction ga) {
		if (ga instanceof TakeCroyantsCardAction) {
			return getCroyantToTakeScore(player, (TakeCroyantsCardAction) ga);
		}
		if (ga instanceof PlayCardAction)
			return getCardToPlayScore(player, (PlayCardAction) ga);

		if (ga instanceof DiscardCardAction) {
			return getDiscardAction(player, (DiscardCardAction) ga);
		}

		if (ga instanceof PlayCardAction) {
			return getCardToUnplay(player, (PlayCardAction) ga);
		}

		return 0;
	}

	/**
	 * Gets the croyant to take score.
	 *
	 * @param player
	 *            the player
	 * @param ga
	 *            the ga
	 * @return the croyant to take score
	 */
	private int getCroyantToTakeScore(Player player, TakeCroyantsCardAction ga) {
		return ga.getCroyantsCard().getCroyantsCount() * 10;
	}

	/**
	 * Gets the card to play score.
	 *
	 * @param player
	 *            the player
	 * @param ga
	 *            the ga
	 * @return the card to play score
	 */
	private int getCardToPlayScore(Player player, PlayCardAction ga) {
		ActionCard c = ga.getCard();
		if (c.getType() == CardType.APOCALYPSE) {
			boolean isSuperior = true;
			for (Player p : ga.getGame().getAlivePlayers()) {
				if (player != p) {
					if (player.getPrayerCount() <= p.getPrayerCount()) {
						isSuperior = false;
					}
				}
			}
			if (isSuperior) {
				return 100;
			}
			return getCroyantToPut(player, ga);
		}
		return getCroyantToPut(player, ga);
	}

	/**
	 * Gets the discard action.
	 *
	 * @param player
	 *            the player
	 * @param ga
	 *            the ga
	 * @return the discard action
	 */
	private int getDiscardAction(Player player, DiscardCardAction ga) {
		ActionCard c = ga.getCard();
		if (c.getEffect() instanceof GivePointEffect && player.getAllPoints() <= 1) {
			return 60;
		}
		return 0;
	}

	/**
	 * Gets the croyant to put.
	 *
	 * @param player
	 *            the player
	 * @param ga
	 *            the ga
	 * @return the croyant to put
	 */
	private int getCroyantToPut(Player player, PlayCardAction ga) {
		int count = 0;
		for (Card ca : player.getCards().asList()) {
			if (ca.getType() == CardType.CROYANTS) {
				count++;
			}
		}
		return count*5;
	}

	/**
	 * Gets the card to unplay.
	 *
	 * @param player
	 *            the player
	 * @param ga
	 *            the ga
	 * @return the card to unplay
	 */
	private int getCardToUnplay(Player player, PlayCardAction ga) {
		ActionCard c = ga.getCard();
		if (c.getType() == CardType.APOCALYPSE) {
			boolean isMinor = false;
			for (Player p : ga.getGame().getAlivePlayers()) {
				if (player != p) {
					if (player.getPrayerCount() < p.getPrayerCount()) {
						isMinor = true;
					}
				}
			}
			if (isMinor) {
				return -10000;
			}
		}
		return 0;
	}

}
