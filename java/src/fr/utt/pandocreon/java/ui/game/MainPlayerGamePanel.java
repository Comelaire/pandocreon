/*
 * 
 */
package fr.utt.pandocreon.java.ui.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.CardAction;
import fr.utt.pandocreon.core.game.action.impl.PlayCardAction;
import fr.utt.pandocreon.core.game.action.impl.TakeCroyantsCardAction;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.java.ActionTranslator;
import fr.utt.pandocreon.java.ui.Sound;
import fr.utt.pandocreon.java.ui.component.Button;
import fr.utt.pandocreon.java.ui.component.Panel;
import fr.utt.pandocreon.java.ui.layout.LineLayout;

/**
 * The Class MainPlayerGamePanel.
 */
public class MainPlayerGamePanel extends PlayerGamePanel implements ActionListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The translate. */
	private final ActionTranslator translate;
	
	/** The consumer. */
	private Consumer<Integer> consumer;
	
	/** The button actions. */
	private final JPanel buttonActions;
	
	/** The table. */
	private final TablePanel table;
	
	/** The dice. */
	private final DicePanel dice;

	/**
	 * Instantiates a new main player game panel.
	 *
	 * @param player
	 *            the player
	 * @param table
	 *            the table
	 */
	public MainPlayerGamePanel(Player player, TablePanel table) {
		super(player);
		this.table = table;
		setPreferredSize(new Dimension(0, 275));
		translate = new ActionTranslator();

		dice = new DicePanel();
		buttonActions = new Panel(new FlowLayout(FlowLayout.CENTER));
		player.getGame().addGameStepListener(dice);
		dice.setPreferredSize(new Dimension(50, 50));
		buttonActions.setPreferredSize(new Dimension(0, 60));

		JPanel main = new JPanel(new BorderLayout(5, 5));
		main.setOpaque(false);

		points.add(dice, 0);
		points.add(new JSeparator(JSeparator.HORIZONTAL), 1);

		main.add(buttonActions, BorderLayout.NORTH);
		main.add(cards, BorderLayout.CENTER);
		main.add(points, BorderLayout.EAST);

		add(divinite, BorderLayout.WEST);
		add(main, BorderLayout.CENTER);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.ui.game.PlayerGamePanel#addParentComponents()
	 */
	@Override
	protected void addParentComponents() {
		setLayout(new BorderLayout(10, 10));
		points.setLayout(new LineLayout().setGap(10, 10));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.ui.game.PlayerGamePanel#createCardPanel(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public CardPanel createCardPanel(Card card) {
		CardPanel c = super.createCardPanel(card);
		c.setPreferredSize(new Dimension(200, 0));
		c.setLayout(new LineLayout().setGap(2, 2).fromBottom());
		return c.setCardVisible(true);
	}

	/**
	 * Adds the possible action.
	 *
	 * @param cmd
	 *            the cmd
	 * @param action
	 *            the action
	 */
	public void addPossibleAction(String cmd, GameAction action) {
		Button b = null;
		switch (action.getType()) {

		case DISCARD_CARD:
			getCardPanel(((CardAction) action).getCard()).add(
					b = new Button("Défausser").smaller());
			break;

		case SACRIFICE_CARD:
			getCardPanel(((CardAction) action).getCard()).add(
					b = new Button("Sacrifier").smaller());
			break;

		case PLAY_CARD:
			Map<Origine, Integer> cost = ((PlayCardAction) action).getCost();
			String strCost = "";
			for (final Entry<Origine, Integer> e : cost.entrySet())
				strCost += String.format(", %d %s%s", e.getValue(), e.getKey(), e.getValue() > 1 ? "s" : "");
			getCardPanel(((PlayCardAction) action).getCard()).add(
					b = new Button("Jouer" + (strCost.length() == 0 ? "" :
						" (" + strCost.substring(2) + ")")).smaller());
			break;

		case TAKE_CROYANTS:
			TakeCroyantsCardAction take = (TakeCroyantsCardAction) action;
			table.getCardPanel(take.getCroyantsCard()).add(b = new Button(
					String.format("<html><small>Ramener avec<br><b>%s</b></small></html>",
							take.getCard())).smaller());
			break;

		case USE_EFFECT:
			divinite.add(b = new Button("Utiliser l'effet").smaller());
			break;

		default:
			buttonActions.add(b = new Button(translate.option(action.getDescriptor())));
		}
		if (b != null) {
			b.setActionCommand(cmd);
			b.addActionListener(this);
		}
	}

	/**
	 * Sets the possible actions.
	 *
	 * @param actions
	 *            the actions
	 * @param consumer
	 *            the consumer
	 */
	public void setPossibleActions(List<GameAction> actions, Consumer<Integer> consumer) {
		buttonActions.removeAll();
		this.consumer = consumer;
		buttonActions.removeAll();
		for (int i = 0; i < actions.size(); i++)
			addPossibleAction(String.valueOf(i), actions.get(i));
		revalidate();
		Sound.getInstance().play("bell.wav");
	}

	/**
	 * Clear actions.
	 */
	public void clearActions() {
		buttonActions.removeAll();
		divinite.removeAll();
		eachCardPanel(JComponent::removeAll);
		table.eachCardPanel(JComponent::removeAll);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		consumer.accept(Integer.valueOf(e.getActionCommand()));
	}

}
