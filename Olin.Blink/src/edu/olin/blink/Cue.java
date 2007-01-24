package edu.olin.blink;

public class Cue {
	
	private int time;
	private int level;
	
	/**
	 *	Creates a <tt>Cue</tt>.
	 *
	 *	@param time the time at which this cue must be executed (milliseconds).
	 *	@param level the level that the output must be set to.
	 */
	public Cue(int time, int level) {
		this.time = time;
		this.level = level;
	}
	
	/**
	 *	Retrieves the time that this cue is scheduled to go off, relative to the some beginning.
	 *
	 *	@return a time value in milliseconds.
	 */
	public int getTime() {
		return this.time;
	}
	
	/**
	 * Retrieves the level that the associated channel must set it's output to.
	 *
	 * @return a level value between 0 and 255.
	 */
	public int getLevel() {
		return this.level;
	}
	
}
