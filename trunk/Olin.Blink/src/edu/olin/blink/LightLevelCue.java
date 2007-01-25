package edu.olin.blink;

public class LightLevelCue extends Cue {
	
	private Light light;
	private int level;
	
	public LightLevelCue(int time, Light light, int level) {
		super(time);
		
		this.light = light;
		this.level = level;
	}
	
	public Light getLight() {
		return light;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void run() {
		light.setLevel(level);
	}
	
}
