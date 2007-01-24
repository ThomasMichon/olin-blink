package edu.olin.blink.client;

import edu.olin.blink.Cue;
import edu.olin.blink.Light;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientPlayer {
	
	private ScheduledExecutorService events = Executors.newSingleThreadScheduledExecutor();
	private ExecutorService actions = Executors.newSingleThreadExecutor();
	
	private Queue<Cue> queue = new PriorityQueue<Cue>();
	private Light light;
	
	private long startTime;
	
	public ClientPlayer() {
	}
	
	public void setCues(Collection<Cue> cues) {
		queue.addAll(cues);
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
		nextCue();
	}
	
	private void nextCue() {
		final Cue cue = queue.poll();
		if (cue == null) return;
		
		Runnable act = new CueLoader(cue);
		
		events.schedule(act, cue.getTime() + startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
	
	private class CueLoader implements Runnable {
		private Cue cue;
		
		public CueLoader(Cue cue) {
			this.cue = cue;
		}
		
		public void run() {
			actions.submit(cue);

			nextCue();
		}
	}
	
}
