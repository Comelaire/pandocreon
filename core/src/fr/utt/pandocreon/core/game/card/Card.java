/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.Arrays;
import java.util.Map;

import fr.utt.pandocreon.core.game.Dogme;
import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.effect.Effect;
import fr.utt.pandocreon.core.util.FlexibleObject;

/**
 * The Class Card.
 */
public abstract class Card extends FlexibleObject {
	
	/** The effect. */
	private Effect effect;
	
	/** The owner. */
	private Player owner;

	/**
	 * Instantiates a new card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public Card(Map<String, String> attributes) {
		super(attributes);
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public abstract CardType getType();

	/**
	 * Checks for dogme.
	 *
	 * @param d2
	 *            the d 2
	 * @return true, if successful
	 */
	public boolean hasDogme(Dogme d2) {
		for (final Dogme d1 : getDogmes())
			if (d1 == d2)
				return true;
		return false;
	}

	/**
	 * Checks for common dogme.
	 *
	 * @param card
	 *            the card
	 * @return true, if successful
	 */
	public boolean hasCommonDogme(Card card) {
		for (final Dogme d1 : card.getDogmes())
			if (hasDogme(d1))
				return true;
		return false;
	}

	/**
	 * Gets the dogmes.
	 *
	 * @return the dogmes
	 */
	public Dogme[] getDogmes() {
		String str = get("dogmes");
		return str == null ? new Dogme[0] :
			Arrays.stream(str.toUpperCase().split(",")).map(Dogme::valueOf).toArray(Dogme[]::new);
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * Gets the origine.
	 *
	 * @return the origine
	 */
	public Origine getOrigine() {
		String origine = get("origine");
		return origine == null ? null : Origine.valueOf(origine.toUpperCase());
	}

	/**
	 * Gets the string dogmes.
	 *
	 * @return the string dogmes
	 */
	public String getStringDogmes() {
		Dogme[] dogmes = getDogmes();
		String sD = "";
		for (int i = 0; i < dogmes.length; i++)
			sD += (i == 0 ? "Dogme" + (dogmes.length == 1 ? " : " : "s : ")  : ", ") + dogmes[i].readable();
		return sD;
	}

	/**
	 * Gets the effect.
	 *
	 * @return the effect
	 */
	public Effect getEffect() {
		if (effect == null)
			effect = Effect.getEffect(getEffectArgs());
		return effect;
	}

	/**
	 * On sacrifice.
	 */
	public void onSacrifice() {
		doEffect();
	}

	/**
	 * Do effect.
	 */
	public void doEffect() {
		getEffect().doEffect(this);
	}

	/**
	 * Gets the effect args.
	 *
	 * @return the effect args
	 */
	public String[] getEffectArgs() {
		return get("effect", "").split(",");
	}

	/**
	 * Gets the string origine.
	 *
	 * @return the string origine
	 */
	public String getStringOrigine() {
		Origine o = getOrigine();
		return o == null ? "Sans origine" : "Origine " + o.readable();
	}

	/**
	 * Full string.
	 *
	 * @return the string
	 */
	public String fullString() {
		return String.format("<html><body style='padding:5px;background-color:#ffbbbbbb;'>"
				+ "<b>Carte %s</b><hr>%s<br>%s<br>%s<br><i>%s</i></body></html>",
				getType().readable(), getName(), getStringOrigine(), getStringDogmes(), getEffect());
	}

}
