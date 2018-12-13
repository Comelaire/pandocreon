/*
 * 
 */
package fr.utt.pandocreon.java.ui.screen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Game.GameBuilder;
import fr.utt.pandocreon.core.game.GameListener;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.card.ResourceResolver;
import fr.utt.pandocreon.java.XMLResourceResolver;
import fr.utt.pandocreon.java.ui.component.Button;
import fr.utt.pandocreon.java.ui.component.Panel;
import fr.utt.pandocreon.java.ui.component.PlayerPanel;
import fr.utt.pandocreon.java.ui.component.UIScreen;
import fr.utt.pandocreon.java.ui.layout.CenterLayout;
import fr.utt.pandocreon.java.ui.layout.LineLayout;

/**
 * The Class CreateGameScreen.
 */
public class CreateGameScreen extends UIScreen implements GameListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant RES. */
	private static final ResourceResolver RES = new XMLResourceResolver();
	
	/** The new player. */
	private final JButton start, back, newPlayer;
	
	/** The players. */
	private final JPanel players;
	
	/** The game. */
	private Game game;

	/**
	 * Instantiates a new creates the game screen.
	 */
	public CreateGameScreen() {
		game = new GameBuilder(RES).build();

		back = new Button("Retour", this::back);
		start = new Button("Commencer", game::next);
		newPlayer = new Button("Ajouter un ordinateur", this::addPlayer).smaller();

		setLayout(new CenterLayout(0, 0));

		JPanel panel = new Panel(new BorderLayout(10, 10));
		JPanel options = new JPanel(new GridLayout(1, 2));
		JPanel panelOp = new JPanel(new GridLayout(1, 2));
		players = new JPanel(new LineLayout().setGap(5, 5));

		options.setOpaque(false);
		players.setOpaque(false);

		options.add(new JLabel("Nouvelle partie", SwingConstants.CENTER));
		options.add(newPlayer);

		panelOp.add(back);
		panelOp.add(start);

		JScrollPane scroll = new JScrollPane(players);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setViewportBorder(null);
		scroll.setBorder(null);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(panelOp, BorderLayout.SOUTH);
		panel.add(options, BorderLayout.NORTH);

		add(panel);

		game.addGameListener(this);
		game.add(new Player("Moi", PlayerType.HUMAN));
		while (!game.canStart())
			addPlayer();
	}

	/**
	 * Adds the player.
	 */
	public void addPlayer() {
		game.add(new Player("Ordinateur " + game.getAllPlayers().size(), PlayerType.BOT));
	}

	/**
	 * Update status.
	 */
	public void updateStatus() {
		start.setEnabled(game.canStart());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameStart(fr.utt.pandocreon.core.game.Game)
	 */
	@Override
	public void onGameStart(Game game) {
		game.removeGameListener(this);
		setScreen(new GameScreen(game));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameEnd(fr.utt.pandocreon.core.game.Game, fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onGameEnd(Game game, Player winner) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onNewPlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onNewPlayer(Player player) {
		players.add(new PlayerPanel(player));
		updateStatus();
		validate();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onPlayerLeave(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onPlayerLeave(Player player) {
		for (final Component c : players.getComponents())
			if (c instanceof PlayerPanel && ((PlayerPanel) c).getPlayer() == player) {
				players.remove(c);
				break;
			}
		updateStatus();
		validate();
	}

}
