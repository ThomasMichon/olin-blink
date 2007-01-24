package edu.olin.blink;

/**
 * A <tt>Cue</tt> is an action that will occur at a particular time.
 * <tt>Cue</tt> extends <tt>Runnable</tt>, so cues require an implementation in order
 * to perform these actions.
 */
public abstract class Cue implements Runnable, Comparable<Cue> {
	protected int time;
	
	public Cue(int time) {
		this.time = time;
	}
	
	/**
	 * Gets the time at which this cue will execute. The time value is relative.
	 */
	public int getTime() {
		return this.time;
	}
	
	public abstract void run();
	
	public int compareTo(Cue other) {
		return getTime() - other.getTime();
	}
}
