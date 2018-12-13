/*
 * 
 */
package fr.utt.pandocreon.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardType;
import fr.utt.pandocreon.core.game.card.ResourceResolver;
import fr.utt.pandocreon.core.game.card.impl.ApocalypseCard;
import fr.utt.pandocreon.core.game.card.impl.CroyantsCard;
import fr.utt.pandocreon.core.game.card.impl.DeusExCard;
import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;
import fr.utt.pandocreon.core.game.card.impl.GuideSpirituelCard;

/**
 * The Class XMLResourceResolver.
 */
public class XMLResourceResolver implements ResourceResolver {
	
	/** The Constant SUPPLIERS. */
	private static final Map<CardType, Function<Map<String, String>, Card>> SUPPLIERS = new HashMap<>();
	
	/** The cards. */
	private Map<CardType, List<Card>> cards;

	static {
		SUPPLIERS.put(CardType.APOCALYPSE, ApocalypseCard::new);
		SUPPLIERS.put(CardType.CROYANTS, CroyantsCard::new);
		SUPPLIERS.put(CardType.DEUS_EX, DeusExCard::new);
		SUPPLIERS.put(CardType.DIVINITE, DiviniteCard::new);
		SUPPLIERS.put(CardType.GUIDE_SPIRITUEL, GuideSpirituelCard::new);
	}

	/**
	 * Gets the cards.
	 *
	 * @return the cards
	 */
	public Map<CardType, List<Card>> getCards() {
		if (cards == null) {
			cards = new HashMap<>();
			Element doc = readXML("cards").getDocumentElement();
			doc.normalize();
			eachChild(doc, node -> {
				CardType type = CardType.valueOf(node.getNodeName().toUpperCase());
				List<Card> cardList = cards.get(type);
				if (cardList == null)
					cards.put(type, cardList = new ArrayList<>());
				cardList.add(getCard(type, node));
			});
		}
		return cards;
	}

	/**
	 * Read XML.
	 *
	 * @param fileName
	 *            the file name
	 * @return the document
	 */
	public Document readXML(String fileName) {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					getClass().getResourceAsStream("/" + fileName + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ResourceResolver#getCards(fr.utt.pandocreon.core.game.card.CardType)
	 */
	@Override
	public Collection<Card> getCards(CardType type) {
		return getCards().get(type);
	}

	/**
	 * Each child.
	 *
	 * @param parent
	 *            the parent
	 * @param consumer
	 *            the consumer
	 */
	public static void eachChild(Node parent, Consumer<Node> consumer) {
		NodeList childNodes = parent.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
				consumer.accept(node);
		}
	}

	/**
	 * Gets the card.
	 *
	 * @param type
	 *            the type
	 * @param parent
	 *            the parent
	 * @return the card
	 */
	public static Card getCard(CardType type, Node parent) {
		Map<String, String> attributes = new HashMap<>();
		eachChild(parent, node -> attributes.put(node.getNodeName(), node.getTextContent()));
		return SUPPLIERS.get(type).apply(attributes);
	}

}
