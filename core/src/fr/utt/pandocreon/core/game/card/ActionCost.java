/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.impl.PlayCardAction;

/**
 * The Class ActionCost.
 */
public class ActionCost {
	
	/** The possible cost. */
	private List<Map<Origine, Integer>> possibleCost;
	
	/** The player. */
	private Player player;
	
	/** The card. */
	private ActionCard card;

	/**
	 * Instantiates a new action cost.
	 *
	 * @param player
	 *            the player
	 * @param card
	 *            the card
	 */
	public ActionCost(Player player, ActionCard card) {
		possibleCost = new ArrayList<>();
		this.player = player;
		this.card = card;
		calc();
	}

	/**
	 * Checks if is possible.
	 *
	 * @return true, if is possible
	 */
	public boolean isPossible() {
		return !possibleCost.isEmpty();
	}

	/**
	 * Checks if is free.
	 *
	 * @return true, if is free
	 */
	public boolean isFree() {
		return isPossible() && possibleCost.get(0).isEmpty();
	}

	/**
	 * Adds the possible cost.
	 *
	 * @param o
	 *            the o
	 * @param cost
	 *            the cost
	 */
	private void addPossibleCost(Origine o, int cost) {
		Map<Origine, Integer> costMap = new HashMap<>();
		costMap.put(o, 1);
		possibleCost.add(costMap);
	}

	/**
	 * Calc simple cost.
	 */
	private void calcSimpleCost() {
		Origine o = card.getOrigine();
		if (player.getPoints(o) > 0)
			addPossibleCost(o, 1);
	}

	/**
	 * Calc.
	 */
	private void calc() {
		Origine o = card.getOrigine();
		if (o == null) {
			possibleCost.add(new HashMap<>());
		} else if (o == Origine.NEANT) {
			calcSimpleCost();
			int pj, pn;
			if ((pj = player.getPoints(Origine.JOUR)) > 1)
				addPossibleCost(Origine.JOUR, 2);
			if ((pn = player.getPoints(Origine.NUIT)) > 1)
				addPossibleCost(Origine.NUIT, 2);
			if (pj > 0 && pn > 0) {
				Map<Origine, Integer> costMap = new HashMap<>();
				costMap.put(Origine.JOUR, 1);
				costMap.put(Origine.NUIT, 1);
				possibleCost.add(costMap);
			}
		} else {
			calcSimpleCost();
		}
	}

	/**
	 * As actions.
	 *
	 * @return the list
	 */
	public List<PlayCardAction> asActions() {
		List<PlayCardAction> actions = new ArrayList<>();
		for (final Map<Origine, Integer> cost : possibleCost)
			actions.add(new PlayCardAction(card, cost));
		return actions;
	}

}
