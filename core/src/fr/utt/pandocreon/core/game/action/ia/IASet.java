/*
 * 
 */
package fr.utt.pandocreon.core.game.action.ia;

import java.util.HashMap;
import java.util.Map;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.ActionDeliverer;
import fr.utt.pandocreon.core.game.action.ActionDelivererManager;

/**
 * The Class IASet.
 */
public class IASet implements ActionDelivererManager {
	
	/** The ias. */
	private final Map<Player, ActionDeliverer> ias;
	
	/** The default ia. */
	private ActionDeliverer defaultIa;

	/**
	 * Instantiates a new IA set.
	 */
	public IASet() {
		ias = new HashMap<>();
		defaultIa = new SmartIA();
	}

	/**
	 * Sets the default.
	 *
	 * @param defaultIa
	 *            the new default
	 */
	public void setDefault(ActionDeliverer defaultIa) {
		this.defaultIa = defaultIa;
	}

	/**
	 * Sets the IA.
	 *
	 * @param player
	 *            the player
	 * @param ia
	 *            the ia
	 */
	public void setIA(Player player, ActionDeliverer ia) {
		ias.put(player, ia);
	}


	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.ActionDelivererManager#getDeliverer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public ActionDeliverer getDeliverer(Player player) {
		ActionDeliverer ia = ias.get(player);
		if (ia == null)
			ia = defaultIa;
		return ia;
	}

}
