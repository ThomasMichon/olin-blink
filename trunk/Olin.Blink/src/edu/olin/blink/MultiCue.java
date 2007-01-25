package edu.olin.blink;

import java.util.Collection;

public class MultiCue extends Cue {
	
	private Cue[] cues;
	
	public MultiCue(int time, Collection<Cue> cues) {
		super(time);
		
		this.cues = cues.toArray(new Cue[cues.size()]);
	}
	
	public MultiCue(int time, Cue ... cues) {
		super(time);
		
		this.cues = new Cue[cues.length];
		System.arraycopy(cues, 0, this.cues, 0, this.cues.length);
	}
	
	public void run() {
		for (Cue cue : cues) cue.run();
	}
	
}
