package edu.olin.blink;

/**
 * A {@link Cue} is an action that will occur at a particular time.
 * {@link Cue} implements {@link Runnable}, so subclasses must override the {@link run}
 * method in order to perform actions.
 */
public abstract class Cue implements Runnable, Comparable<Cue> {
	protected int time;
	
	/**
	 * Creates a new {@link Cue} that will execute at the specified time in milliseconds.
	 * Although this class is abstract, a {@link Cue} must have a valid time.
	 * @param time the time at which this {@link Cue} will execute.
	 */
	public Cue(int time) {
		this.time = time;
	}
	
	/**
	 * Gets the time at which this cue will execute. The time value is relative.
	 * @return the time at which this cue will execute, in milliseconds.
	 */
	public int getTime() {
		return this.time;
	}
	
	/**
	 * Executes this {@link Cue}. Subclasses must override this method in order to perform their actions.
	 * Since <tt>Cue</tt> implements {@link Runnable}, cues can be executed by most classes in the Java runtime.
	 */
	public abstract void run();
	
	/**
	 * <p>
	 * 		Compares this {@link Cue} to another by comparing time values.
	 * 		This allows cues to be quickly sorted for running. However, this implementation
	 * 		currently does not allow multiple cues to execute at once.
	 * </p>
	 * 
	 * <p>
	 * 		<b>Note:</b> if you intend to fire multiple cues at the same time,
	 * 		you should probably stack them into a single "group" cue instead of
	 * 		intersting them all into the same timeline.
	 * </p>
	 * @param other the other {@link Cue} to which this {@link Cue} will be compared.
	 */
	public int compareTo(Cue other) {
		return getTime() - other.getTime();
	}
}
