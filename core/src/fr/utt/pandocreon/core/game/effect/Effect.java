/*
 * 
 */
package fr.utt.pandocreon.core.game.effect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.utt.pandocreon.core.game.Dogme;
import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.card.Card;

/**
 * The Class Effect.
 */
public abstract class Effect {
	
	/** The Constant EFFECTS. */
	private static final Map<String, Function<String[], Effect>> EFFECTS = new HashMap<>();
	
	/** The args. */
	private final String[] args;

	static {
		add(
				NoEffect::new,
				ApocalypseEffect::new,
				GivePointEffect::new,
				LaunchDiceEffect::new,
				DestroyTableCroyantsEffect::new
				);
	}

	/**
	 * Adds the.
	 *
	 * @param effects
	 *            the effects
	 */
	@SafeVarargs
	public static void add(Function<String[], Effect>... effects) {
		for (final Function<String[], Effect> effect : effects)
			EFFECTS.put(effect.apply(null).getName(), effect);
	}

	/**
	 * Gets the effect.
	 *
	 * @param args
	 *            the args
	 * @return the effect
	 */
	public static Effect getEffect(String[] args) {
		Function<String[], Effect> effectBuilder = EFFECTS.get(args[0]);
		if (effectBuilder == null)
			throw new IllegalArgumentException("No effect with name " + args[0]);
		return effectBuilder.apply(Arrays.copyOfRange(args, 1, args.length));
	}

	/**
	 * Instantiates a new effect.
	 *
	 * @param args
	 *            the args
	 */
	public Effect(String[] args) {
		this.args = args;
	}

	/**
	 * Do effect.
	 *
	 * @param card
	 *            the card
	 */
	public abstract void doEffect(Card card);
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public abstract String getName();
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public abstract String getText();

	/**
	 * Checks for effect.
	 *
	 * @return true, if successful
	 */
	public boolean hasEffect() {
		return true;
	}

	/**
	 * Gets the arg.
	 *
	 * @param index
	 *            the index
	 * @return the arg
	 */
	public String getArg(int index) {
		return args[index];
	}

	/**
	 * Gets the int arg.
	 *
	 * @param index
	 *            the index
	 * @return the int arg
	 */
	public int getIntArg(int index) {
		return Integer.valueOf(getArg(index));
	}

	/**
	 * Gets the origine.
	 *
	 * @param index
	 *            the index
	 * @return the origine
	 */
	public Origine getOrigine(int index) {
		return Origine.valueOf(getArg(index).toUpperCase());
	}

	/**
	 * Gets the dogme.
	 *
	 * @param index
	 *            the index
	 * @return the dogme
	 */
	public Dogme getDogme(int index) {
		return Dogme.valueOf(getArg(index).toUpperCase());
	}

	/**
	 * Gets the args after.
	 *
	 * @param index
	 *            the index
	 * @return the args after
	 */
	public String[] getArgsAfter(int index) {
		return Arrays.copyOfRange(args, index + 1, args.length);
	}

	/**
	 * Gets the args count.
	 *
	 * @return the args count
	 */
	public int getArgsCount() {
		return args.length;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getText();
	}

}
