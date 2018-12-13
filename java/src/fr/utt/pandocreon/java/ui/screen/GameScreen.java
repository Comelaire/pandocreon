/*
 * 
 */
package fr.utt.pandocreon.java.ui.screen;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.GameListener;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.core.game.action.ActionDescriptor;
import fr.utt.pandocreon.core.game.step.GameStep;
import fr.utt.pandocreon.core.game.step.GameStepListener;
import fr.utt.pandocreon.core.game.step.StepAction;
import fr.utt.pandocreon.java.GameLogger;
import fr.utt.pandocreon.java.UIGameManager;
import fr.utt.pandocreon.java.ui.Images;
import fr.utt.pandocreon.java.ui.Screen;
import fr.utt.pandocreon.java.ui.game.PlayerGamePanel;
import fr.utt.pandocreon.java.ui.game.StepProgressComponent;
import fr.utt.pandocreon.java.ui.game.TablePanel;
import fr.utt.pandocreon.java.ui.layout.PlayersLayout;

/**
 * The Class GameScreen.
 */
public class GameScreen extends Screen implements GameStepListener, GameListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant LOG_ENABLED. */
	private static final boolean LOG_ENABLED = false;
	
	/** The Constant START_DELAY. */
	private static final int START_DELAY = 1000;
	
	/** The players. */
	private final Map<Player, PlayerGamePanel> players;
	
	/** The progress. */
	private final StepProgressComponent progress;
	
	/** The table. */
	private final TablePanel table;
	
	/** The manager. */
	private final UIGameManager manager;
	
	/** The game. */
	private final Game game;
	
	/** The player. */
	private PlayerGamePanel player;

	/**
	 * Instantiates a new game screen.
	 *
	 * @param game
	 *            the game
	 */
	public GameScreen(Game game) {
		setLayout(new BorderLayout(10, 10));
		setImage(Images.getInstance().getImage("sky.jpg"));

		JPanel gamePanel = new JPanel(new PlayersLayout(game));
		gamePanel.setOpaque(false);

		players = new HashMap<>();
		progress = new StepProgressComponent();
		manager = new UIGameManager();
		table = new TablePanel(game.getTable());

		this.game = game;
		setOpaque(false);
		gamePanel.add(table);
		for (final Player p : game.getAllPlayers())
			if (p.getType() == PlayerType.HUMAN)
				add(register(manager.setPlayer(table, p)), BorderLayout.SOUTH);
			else gamePanel.add(register(new PlayerGamePanel(p)));

		add(gamePanel, BorderLayout.CENTER);
		add(progress, BorderLayout.NORTH);

		if (LOG_ENABLED)
			new GameLogger().registerTo(game);

		game.addGameListener(this);
		game.addGameStepListener(progress);
		game.addGameStepListener(manager);
		game.addGameStepListener(this);
		game.getTable().addCardListener(table);

		game.postAction(game::next, START_DELAY);
	}

	/**
	 * Gets the manager.
	 *
	 * @return the manager
	 */
	public UIGameManager getManager() {
		return manager;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Register.
	 *
	 * @param panel
	 *            the panel
	 * @return the player game panel
	 */
	public PlayerGamePanel register(PlayerGamePanel panel) {
		players.put(panel.getPlayer(), panel);
		return panel;
	}

	/**
	 * Gets the player panel.
	 *
	 * @param player
	 *            the player
	 * @return the player panel
	 */
	public PlayerGamePanel getPlayerPanel(Player player) {
		return players.get(player);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepAction(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.step.StepAction)
	 */
	@Override
	public void onStepAction(GameStep step, StepAction action) {
		Player p = step.getFinalPlayer();
		if (player != null) {
			player.unselect();
			player = null;
		}
		if (p != null) {
			player = getPlayerPanel(p);
			player.select();
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepStart(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepStart(GameStep step) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onStepEnd(fr.utt.pandocreon.core.game.step.GameStep)
	 */
	@Override
	public void onStepEnd(GameStep step) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.step.GameStepListener#onActionPerformed(fr.utt.pandocreon.core.game.step.GameStep, fr.utt.pandocreon.core.game.action.ActionDescriptor)
	 */
	@Override
	public void onActionPerformed(GameStep step, ActionDescriptor descriptor) {
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameStart(fr.utt.pandocreon.core.game.Game)
	 */
	@Override
	public void onGameStart(Game game) {

	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onGameEnd(fr.utt.pandocreon.core.game.Game, fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onGameEnd(Game game, Player winner) {
		JOptionPane.showMessageDialog(this, winner + " gagne la partie",
				"Partie terminée", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Images.getInstance().getImage("icon_16.png")));
		game.removeAllListeners();
		game.clean();
		setScreen(new HomeScreen());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onNewPlayer(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onNewPlayer(Player player) {

	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.GameListener#onPlayerLeave(fr.utt.pandocreon.core.game.Player)
	 */
	@Override
	public void onPlayerLeave(Player player) {

	}

}
