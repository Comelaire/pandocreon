/*
 * 
 */
package fr.utt.pandocreon.core.game.action.ia;

import java.util.List;
import java.util.Random;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.ActionDeliverer;
import fr.utt.pandocreon.core.game.action.GameAction;

/**
 * The Class RandomIA.
 */
public class RandomIA implements ActionDeliverer {
	
	/** The random. */
	private final Random random;

	/**
	 * Instantiates a new random IA.
	 */
	public RandomIA() {
		random = new Random();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.ActionDeliverer#getActionToPerform(fr.utt.pandocreon.core.game.Player, java.util.List)
	 */
	@Override
	public int getActionToPerform(Player player, List<GameAction> actions) {
		if (!player.isPlaying() && random.nextBoolean())
			return -1;
		return random.nextInt(actions.size());
	}

}
