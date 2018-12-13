/*
 * 
 */
package fr.utt.pandocreon.core.util;

import java.util.Arrays;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.swing.event.EventListenerList;

/**
 * The Class Listenable.
 */
public class Listenable {
	
	/** The listener list. */
	private EventListenerList listenerList;
	
	/** The actions. */
	private Queue<Runnable> actions;
	
	/** The timer. */
	private Timer timer;
	
	/** The notifying. */
	private boolean notifying;

	/**
	 * Removes the all listeners.
	 */
	public void removeAllListeners() {
		listenerList = new EventListenerList();
	}

	/**
	 * Clean.
	 */
	public void clean() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * Gets the timer.
	 *
	 * @return the timer
	 */
	public Timer getTimer() {
		if (timer == null)
			timer = new Timer();
		return timer;
	}

	/**
	 * Post action.
	 *
	 * @param action
	 *            the action
	 * @return true, if successful
	 */
	public boolean postAction(Runnable action) {
		if (notifying) {
			actions.add(action);
			return false;
		}
		action.run();
		return true;
	}

	/**
	 * Post action.
	 *
	 * @param action
	 *            the action
	 * @param delay
	 *            the delay
	 */
	public void postAction(Runnable action, long delay) {
		getTimer().schedule(new TimerTask() {
			@Override
			public void run() {
				postAction(action);
			}
		}, delay);
	}

	/**
	 * Checks if is notifying.
	 *
	 * @return true, if is notifying
	 */
	public boolean isNotifying() {
		return notifying;
	}

	/**
	 * Stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @return the stream
	 */
	protected <T extends EventListener> Stream<T> stream(Class<T> t) {
		return Arrays.stream(getListeners(t));
	}

	/**
	 * Notify.
	 *
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @param action
	 *            the action
	 */
	protected <T extends EventListener> void notify(Class<T> t, Consumer<T> action) {
		Runnable r = () -> {
			notifying = true;
			stream(t).forEach(action);
			notifying = false;
			while (!actions.isEmpty())
				actions.poll().run();
		};
		if (notifying)
			postAction(r);
		else r.run();
	}

	/**
	 * Gets the listener list.
	 *
	 * @return the listener list
	 */
	protected EventListenerList getListenerList() {
		if(listenerList == null) {
			listenerList = new EventListenerList();
			actions = new LinkedList<>();
		}
		return listenerList;
	}

	/**
	 * Adds the listener.
	 *
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @param l
	 *            the l
	 */
	protected <T extends EventListener> void addListener(Class<T> t, T l) {
		postAction(() -> getListenerList().add(t, l));
	}

	/**
	 * Removes the listener.
	 *
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @param l
	 *            the l
	 */
	protected <T extends EventListener> void removeListener(Class<T> t, T l) {
		postAction(() -> getListenerList().remove(t, l));
	}

	/**
	 * Gets the listeners.
	 *
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @return the listeners
	 */
	protected <T extends EventListener> T[] getListeners(Class<T> t) {
		return getListenerList().getListeners(t);
	}

}
