package edu.olin.blink;

/**
 * <p>
 * 		Abstractly represents a light (for instance, a light-string) while ignoring how it's implemented.
 * 		This allows light controllers to operate over serial port, USB, or even the network.
 * </p>
 */
public interface Light {
	/**
	 * Gets the intensity level of this <code>Light</code>.
	 * @return a value between 0 and 255 representing the intensity of the light. 0 is full off while 255 is full on.
	 */
	int getLevel();
	/**
	 * Sets the intensity level of this <code>Light</code> to the specifiec value.
	 * @param level a value between 0 and 255 representing the intensity of the light. 0 is full off while 255 is full on.
	 */
	void setLevel(int level);
}
