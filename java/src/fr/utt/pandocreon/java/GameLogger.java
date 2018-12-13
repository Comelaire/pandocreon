/*
 * 
 */
package fr.utt.pandocreon.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.GameListener;
import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.PlayerListener;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.CardListener;
import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;

/**
 * The Class GameLogger.
 */
public class GameLogger implements GameStepListener, GameListener, CardListener, PlayerListener {
	
	/** The out. */
	private BufferedWriter out;
	
	/** The turn level. */
	private int turnLevel;

	/**
	 * Register to.
	 *
	 * @param game
	 *            the game
	 */
	public void registerTo(Game game) {
		game.addGameListener(this);
		game.addGameStepListener(this);
		game.getCards().addCardListener(this);
		game.getCards().getDiscard().addCardListener(this);
		for (final Player p : game.getAllPlayers()) {
			p.addPlayerListener(this);
			p.getCards().addCardListener(this);
		}
	}

	/**
	 * Gets the out.
	 *
	 * @return the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public BufferedWriter getOut() throws IOException {
		if (out == null) {
			File f = new File("../game.log");
			System.out.println("Log path: " + f.getCanonicalPath());
			out = new BufferedWriter(new FileWriter(f));
		}
		return out;
	}

	/**
	 * Log.
	 *
	 * @param text
	 *            the text
	 * @param newLine
	 *            the new line
	 * @return the string
	 */
	public String log(String text, boolean newLine) {
		try {
			getOut().write(text);
			if (newLine)
				out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * Close.
	 */
	public void close() {
		if (out != null) try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display.
	 *
	 * @param text
	 *            the text
	 */
	public void display(String text) {
		text = n("  ") + text;
		System.out.println(text);
		log(text, true);
	}

	/**
	 * Format.
	 *
	 * @param text
	 *            the text
	 * @param maxWidth
	 *            the max width
	 * @param internSpace
	 *            the intern space
	 * @param leftSpace
	 *            the left space
	 * @param spaceChar
	 *            the space char
	 * @param boxChar
	 *            the box char
	 */
	public void format(String text, int maxWidth, int internSpace, int leftSpace, char spaceChar, char boxChar) {
		String s = text;
		for (int i = 0; i < internSpace + leftSpace; i++)
			s = (i > internSpace ? ' ' : (i == internSpace ? boxChar : spaceChar)) + s;
		for (int i = 0; i < internSpace + maxWidth - text.length(); i++)
			s += spaceChar;
		if (maxWidth % 2 == 0)
			s += spaceChar;
		display(s + boxChar);
	}

	/**
	 * Inline.
	 *
	 * @param text
	 *            the text
	 */
	public void inline(String text) {
		text = n("  ") + text;
		System.out.print(text);
		log(text, false);
	}

	/**
	 * N.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	public String n(String string) {
		String s = "";
		for (int i = 0; i < turnLevel; i++)
			s += string;
		return s;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepStart(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepStart(GameStep step) {
		turnLevel++;
		display(">> Tour de jeu : " + step);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepEnd(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepEnd(GameStep step) {
		display("<< Fin du tour de jeu : " + step);
		turnLevel--;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepAction(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.step.StepAction)
	 */
	@Override
	public void onStepAction(GameStep step, StepAction action) {
		Player p = action.getStep().getPlayer();
		String s = "    | " + (p == null ?
				"Tout le monde peut jouer" : "Au tour de " + p + " de jouer") + " |";
		display("");
		format("", s.length() - 6, 0, 5, '-', 'o');
		display(s);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameStart(fr.utt.pandocreon.core.game.Game)
	 */
	@Override
	public void onGameStart(Game game) {
		display("");
		display("La partie commence");
		display("");
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameEnd(fr.utt.pandocreon.core.game.Game, fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onGameEnd(Game game, Player winner) {
		display("");
		display("Partie terminée. " + winner + " gagne la partie");
		display("");
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onNewPlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onNewPlayer(Player player) {
		display(player + " rejoint la partie");
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onPlayerLeave(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onPlayerLeave(Player player) {
		display(player + " quitte la partie");
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardListener#onCardMovement(fr.utt.pandocreon.core.game.card.Card, fr.utt.pandocreon.core.game.card.CardContainer, fr.utt.pandocreon.core.game.card.CardContainer, boolean)
	 */
	@Override
	public void onCardMovement(Card card, CardContainer source, CardContainer target, boolean visible) {
		display(String.format("%s --> %s --> %s", source, visible ? card : "?", target));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardListener#onCardAdded(fr.utt.pandocreon.core.game.card.Card, fr.utt.pandocreon.core.game.card.CardContainer)
	 */
	@Override
	public void onCardAdded(Card card, CardContainer target) {
		display(card + " --> " + target);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.PlayerListener#onDiviniteSet(fr.utt.pandocreon.core.game.Player, fr.utt.pandocreon.core.game.card.impl.DiviniteCard)
	 */
	@Override
	public void onDiviniteSet(Player player, DiviniteCard divinite) {
		display(player + " incarne " + divinite);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.PlayerListener#onPointsChange(fr.utt.pandocreon.core.game.Player, fr.utt.pandocreon.core.game.Origine, int, int)
	 */
	@Override
	public void onPointsChange(Player player, Origine origine, int oldPoints, int newPoints) {
		int change = newPoints - oldPoints, abs = Math.abs(change);
		display(String.format("%s %s %d point%s %s", player, change > 0 ? "gagne" : "perd", abs, abs > 1 ? "s" : "", origine));
	}

}
