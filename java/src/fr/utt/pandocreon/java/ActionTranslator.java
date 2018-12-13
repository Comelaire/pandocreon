/*
 * 
 */
package fr.utt.pandocreon.java;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.action.impl.DiscardCardAction;
import fr.utt.pandocreon.core.game.action.impl.DrawCardAction;
import fr.utt.pandocreon.core.game.action.impl.LaunchDiceAction;
import fr.utt.pandocreon.core.game.action.impl.PlayCardAction;

/**
 * The Class ActionTranslator.
 */
public class ActionTranslator {

	/**
	 * Option.
	 *
	 * @param descriptor
	 *            the descriptor
	 * @return the string
	 */
	public String option(ActionDescriptor descriptor) {
		switch (descriptor.getType()) {

		case END_TURN:
			return "Passer";

		case DRAW_CARD:
			return "Piocher une carte";

		case LAUNCH_DICE:
			return "Lancer le dé";

		case DISCARD_CARD:
			return String.format("Se défausser de %s", ((DiscardCardAction) descriptor.getAction()).getCard());

		case PLAY_CARD:
			PlayCardAction play = (PlayCardAction) descriptor.getAction();
			return String.format("Jouer %s avec pour coût : %s", play.getCard(), play.getCost());

		default:
			return descriptor.getType().readable();
		}
	}

	/**
	 * Action.
	 *
	 * @param descriptor
	 *            the descriptor
	 * @return the string
	 */
	public String action(ActionDescriptor descriptor) {
		Player player = descriptor.getAction().getPlayer();
		switch (descriptor.getType()) {

		case END_TURN:
			return String.format("%s termine son tour", player);

		case DRAW_CARD:
			return String.format("%s pioche %s", player, ((DrawCardAction) descriptor.getAction()).getCard());

		case LAUNCH_DICE:
			return String.format("Le dé tombe sur la face %s", ((LaunchDiceAction) descriptor.getAction()).getLaunch());

		case DISCARD_CARD:
			return String.format("%s se défausse de %s", player, ((DiscardCardAction) descriptor.getAction()).getCard());

		case PLAY_CARD:
			PlayCardAction play = (PlayCardAction) descriptor.getAction();
			return String.format("%s joue %s en dépensant : %s", player, play.getCard(), play.getCost());

		default:
			return descriptor.getType().readable();
		}
	}

}
