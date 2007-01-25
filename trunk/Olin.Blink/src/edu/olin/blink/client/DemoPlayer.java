package edu.olin.blink.client;

import edu.olin.blink.Cue;
import java.util.LinkedList;
import java.util.List;

public class DemoPlayer {
	
	public DemoPlayer() {
	}
	
	public static void main(String[] args) {
		List<Cue> cues = new LinkedList<Cue>();
		
		for (int i = 0; i < 20; i ++)
			cues.add(new DummyCue(i * 500 + 1000, i));
		
		CuePlayer player = new CuePlayer();
		player.setCues(cues);
		
		player.start();
	}
	
	private static class DummyCue extends Cue {
		private int id;
		
		public DummyCue(int time, int id) {
			super(time);
			this.id = id;
		}
		
		public void run() {
			System.out.println("Cue " + id + " executed at " + System.currentTimeMillis());
		}
	}
	
}
