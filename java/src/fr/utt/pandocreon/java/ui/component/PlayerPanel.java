/*
 * 
 */
package fr.utt.pandocreon.java.ui.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.java.ui.Images;

/**
 * The Class PlayerPanel.
 */
public class PlayerPanel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant ICONS. */
	public static final Image[] ICONS = {
			Images.getInstance().getImage("human.png").getScaledInstance(25, 25, BufferedImage.SCALE_SMOOTH),
			Images.getInstance().getImage("bot.png").getScaledInstance(25, 25, BufferedImage.SCALE_SMOOTH),
			Images.getInstance().getImage("network.png").getScaledInstance(25, 25, BufferedImage.SCALE_SMOOTH)
	};
	
	/** The delete. */
	private final JButton delete;
	
	/** The name. */
	private final JTextField name;
	
	/** The player. */
	private final Player player;

	/**
	 * Instantiates a new player panel.
	 *
	 * @param player
	 *            the player
	 */
	public PlayerPanel(Player player) {
		super(new BorderLayout(5, 0));
		setOpaque(false);
		this.player = player;

		delete = new JButton("X");
		delete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
		name = new JTextField(player.getName());
		name.addActionListener(e -> player.setName(name.getText()));

		add(Box.createRigidArea(new Dimension(20, 20)), BorderLayout.WEST);
		add(name, BorderLayout.CENTER);

		if (player.getType() == PlayerType.BOT)
			add(delete, BorderLayout.EAST);

		delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		delete.addActionListener(e -> player.getGame().remove(player));
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ICONS[player.getType().ordinal()], 0, 0, null);
	}

}
