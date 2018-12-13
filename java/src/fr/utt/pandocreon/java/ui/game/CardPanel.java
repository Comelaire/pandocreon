/*
 * 
 */
package fr.utt.pandocreon.java.ui.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import fr.utt.pandocreon.core.game.Dogme;
import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.impl.ApocalypseCard;
import fr.utt.pandocreon.core.game.card.impl.CroyantsCard;
import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;
import fr.utt.pandocreon.core.game.card.impl.GuideSpirituelCard;
import fr.utt.pandocreon.java.ui.Images;

/**
 * The Class CardPanel.
 */
public class CardPanel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant TITLE. */
	private static final Font TITLE = new Font(Font.SERIF, Font.BOLD, 12);
	
	/** The Constant CARD_COLORS. */
	private static final Color[] CARD_COLORS = new Color[] {
			new Color(200, 200, 250),
			new Color(200, 250, 200),
			new Color(250, 200, 250),
			new Color(250, 240, 175),
			new Color(185, 185, 185)
	};
	
	/** The visible. */
	private boolean visible;
	
	/** The progress. */
	private int progress;
	
	/** The card. */
	private Card card;

	/**
	 * Instantiates a new card panel.
	 */
	public CardPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(75, 100));
	}

	/**
	 * Instantiates a new card panel.
	 *
	 * @param card
	 *            the card
	 */
	public CardPanel(Card card) {
		this();
		setCard(card);
	}

	/**
	 * Sets the card visible.
	 *
	 * @param visible
	 *            the visible
	 * @return the card panel
	 */
	public CardPanel setCardVisible(boolean visible) {
		this.visible = visible;
		if (!visible)
			setToolTipText(null);
		else if (card != null)
			setToolTipText(card.fullString());
		return this;
	}

	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * Sets the card.
	 *
	 * @param card
	 *            the new card
	 */
	public void setCard(Card card) {
		this.card = card;
		progress = 0;
		if (card == null || !visible) {
			setToolTipText(null);
		} else {
			setToolTipText(card.fullString());
		}
	}

	/**
	 * Draw invisible.
	 *
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void drawInvisible(Graphics2D g, int x, int y, int w, int h) {
		int c = w/10;
		g.setStroke(new BasicStroke(3f));
		g.setColor(Color.GRAY);
		g.fillRoundRect(x + 2, y + 2, w - 5, h - 5, c, c);
		g.setColor(new Color(110, 110, 110));
		g.fill(new Polygon(new int[] {x + 3, x - 3 + w, x + 3}, new int[] {y + 3, y + 3, y - 3 + h}, 3));
		g.setColor(Color.WHITE);
		g.drawRoundRect(x + 2, y + 2, w - 5, h - 5, c, c);
		g.setColor(Color.WHITE);
		String s = "PANDOCRÉON";
		g.setFont(TITLE.deriveFont(w/8f));
		g.drawString(s, x + (w - g.getFontMetrics().stringWidth(s))/2, y + h/2 - w/20);
		s = "DIVINÆ";
		g.setFont(TITLE.deriveFont(Font.PLAIN, w/6f));
		g.drawString(s, x + (w - g.getFontMetrics().stringWidth(s))/2, y + h/2 + w/8);
		g.drawString("~", x + (w - g.getFontMetrics().stringWidth("~"))/2, y + h/2 + h/5);
		g.setStroke(new BasicStroke(1f));
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(x, y, w - 1, h - 1, (int) (c * 1.2f), (int) (c * 1.2f));
	}

	/**
	 * Draw card.
	 *
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void drawCard(Graphics2D g, int x, int y, int w, int h) {
		int c = w/10;
		g.setStroke(new BasicStroke(4f));
		g.setColor(CARD_COLORS[card.getType().ordinal()]);
		g.fillRoundRect(x, y, w, h, c * 2, c * 2);
		g.setColor(Color.WHITE);
		g.drawRoundRect(x + 4, y + 4, w - 8, h - 8, c, c);
		g.setStroke(new BasicStroke(1f));
		g.setColor(Color.GRAY);
		g.drawRoundRect(x + 1, y + 1, w - 3, h - 3, c, c);
		g.setColor(Color.BLACK);
		String name = card.toString().toUpperCase();
		g.setFont(TITLE.deriveFont(w/12f));
		g.drawString(name, x + (w - g.getFontMetrics().stringWidth(name))/2, y + h/3);
	}

	/**
	 * Draw top.
	 *
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void drawTop(Graphics2D g, int x, int y, int w, int h) {
		Dogme[] dogmes = card.getDogmes();
		int ic = w/6;
		for (int i = 0; i < dogmes.length; i++) {
			BufferedImage img = Images.getInstance().getImage(dogmes[i].toString().toLowerCase() + ".png");
			switch(i) {
			case 0:
				g.drawImage(img, x + ic/3, y + ic/3, ic, ic, null);
				break;
			case 1:
				g.drawImage(img, (int) (x + ic/3 + ic * 1.2f), y + ic/3, ic, ic, null);
				break;
			case 2:
				g.drawImage(img, (int) (x + ic/1.15f), (int) (y + ic * 1.4f), ic, ic, null);
				break;
			default:
				System.err.println("Too many dogmes, cannot draw");
			}
		}
		Origine o = card.getOrigine();
		if (o != null) {
			ic = w/4;
			BufferedImage img = Images.getInstance().getImage(o.toString().toLowerCase() + ".png");
			g.drawImage(img, (int) (x + w - ic * 1.15f), y + ic/5, ic, ic, null);
		}
	}

	/**
	 * Draw effect.
	 *
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void drawEffect(Graphics2D g, int x, int y, int w, int h) {
		Rectangle r = new Rectangle(1 + x + w/10, (int) (y + h/2.5f), w - w/5, (int) (h/2.5f));
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRoundRect(r.x - w/40, r.y - w/40, r.width + w/20, r.height + w/20, w/5, w/5);
		g.setColor(new Color(255, 255, 255, 175));
		g.fillRoundRect(r.x, r.y, r.width, r.height, w/10, w/10);
		String[] text = card.getEffect().getText().split(" ");
		g.setColor(Color.DARK_GRAY);
		g.setFont(g.getFont().deriveFont(Font.PLAIN, Math.max(1, w/12f)));
		int tx = r.x + w/20, ty = r.y + w/10;
		for (final String word : text) {
			if (tx + g.getFontMetrics().stringWidth(word) > r.width) {
				tx = r.x + w/20;
				ty += w/10;
			}
			g.drawString(word, tx, ty);
			tx += g.getFontMetrics().stringWidth(word) + w/50;
		}
	}

	/**
	 * Draw.
	 *
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void draw(Graphics2D g, int x, int y, int w, int h) {
		drawCard(g, x, y, w, h);
		drawTop(g, x, y, w, h);
		if (card.getEffect().hasEffect() && !(card instanceof ApocalypseCard))
			drawEffect(g, x, y, w, h);
		if (card instanceof CroyantsCard)
			draw((CroyantsCard) card, g, x, y, w, h);
		else if (card instanceof GuideSpirituelCard)
			draw((GuideSpirituelCard) card, g, x, y, w, h);
		else if (card instanceof DiviniteCard)
			draw((DiviniteCard) card, g, x, y, w, h);
	}

	/**
	 * Draw number.
	 *
	 * @param number
	 *            the number
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void drawNumber(int number, Graphics2D g, int x, int y, int w, int h) {
		g.setFont(getFont().deriveFont(Font.BOLD, w/10f));
		g.fillOval((int) (x + w - w/5.5f), y + h - h/7, w/8, h/9);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(number), x + w - w/7 - 1, y + h - h/15);
	}

	/**
	 * Draw.
	 *
	 * @param card
	 *            the card
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void draw(CroyantsCard card, Graphics2D g, int x, int y, int w, int h) {
		if (card.isGuided()) {
			g.setColor(Color.WHITE);
			g.fillRoundRect(x + 2, (int) (y + h/1.2f), w - 4, h/6 - 2, w/10, w/10);
			g.setFont(g.getFont().deriveFont(Font.PLAIN, w/15f));
			g.setColor(Color.GRAY);
			g.drawString(String.format("Guidé%s par :",
					card.getCroyantsCount() > 1 ? "s" : ""), x + w/20, y + h - h/8.5f);
			g.setFont(g.getFont().deriveFont(Font.BOLD, w/10f));
			g.setColor(Color.BLUE.darker());
			g.drawString(card.getGuide().toString(), x + w/10, y + h - h/25);
		}
		g.setColor(Color.RED.darker());
		drawNumber(card.getCroyantsCount(), g, x, y, w, h);
	}

	/**
	 * Draw.
	 *
	 * @param card
	 *            the card
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void draw(GuideSpirituelCard card, Graphics2D g, int x, int y, int w, int h) {
		g.setColor(Color.BLUE.darker());
		drawNumber(card.getCroyantsCapacity(), g, x, y, w, h);
	}

	/**
	 * Draw.
	 *
	 * @param card
	 *            the card
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void draw(DiviniteCard card, Graphics2D g, int x, int y, int w, int h) {
		g.setColor(Color.BLUE.brighter());
		drawNumber(card.getOwner().getPrayerCount(), g, x, y, w, h);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g1d) {
		super.paintComponent(g1d);
		if (card != null) {
			Graphics2D g = (Graphics2D) g1d;
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int w = getWidth();
			int h = (int) (getWidth() * 1.5);
			if (h > getHeight()) {
				h = getHeight();
				w = (int) (getHeight()/1.5);
			}
			int x = (getWidth() - w)/2;
			int y = (getHeight() - h)/2;
			if (progress < 20) {
				g.setClip(null);
				y -= 500/(1 + progress * 20) - 1;
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f + progress/40f));
			}
			if (visible)
				draw(g, x, y, w, h);
			else drawInvisible(g, x, y, w, h);
		}
		if (progress < 20)
			progress++;
	}

}
