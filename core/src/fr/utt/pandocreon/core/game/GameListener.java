/*
 * 
 */
package fr.utt.pandocreon.core.game;

import java.util.EventListener;

/**
 * The listener interface for receiving game events. The class that is
 * interested in processing a game event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addGameListener<code> method. When the game event occurs,
 * that object's appropriate method is invoked.
 *
 * @see GameEvent
 */
public interface GameListener extends EventListener {

	/**
	 * On game start.
	 *
	 * @param game
	 *            the game
	 */
	void onGameStart(Game game);
	
	/**
	 * On game end.
	 *
	 * @param game
	 *            the game
	 * @param winner
	 *            the winner
	 */
	void onGameEnd(Game game, Player winner);
	
	/**
	 * On new player.
	 *
	 * @param player
	 *            the player
	 */
	void onNewPlayer(Player player);
	
	/**
	 * On player leave.
	 *
	 * @param player
	 *            the player
	 */
	void onPlayerLeave(Player player);

}
