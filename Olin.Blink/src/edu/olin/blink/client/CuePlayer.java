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

/**
 * <p>
 * 		A {@link CuePlayer} executes {@link Cue}s at their associated times relative to some start time.
 * </p>
 * <p>
 * 		In order to initialize and play a sequence of {@link Cue}s, the following actions must be performed:
 * </p>
 * <ol>
 * 		<li>The {@link setCues} method is called with a {@link List} of {@link Cue} objects.</li>
 * 		<li>The {@link start} method is called at the desired start time.</li>
 * </ol>
 */
public class CuePlayer {
	private ScheduledExecutorService events = Executors.newSingleThreadScheduledExecutor();
	private ExecutorService actions = Executors.newSingleThreadExecutor();
	
	private Queue<Cue> queue = new PriorityQueue<Cue>();
	private Light light;
	
	private long startTime = -1;
	
	/**
	 * Creates a new {@link CuePlayer}.
	 */
	public CuePlayer() {
	}
	
	/**
	 * Gets the time at which this {@link CuePlayer} started execution of a sequence of cues.
	 * @return The system time (according to {@link System.currentTimeMillis})
	 * at which the {@link start} method was called, or -1
	 * if the {@link CuePlayer} is not currently executing cues.
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * Sets the sequence of cues to be those in the given {@link Collection}.
	 * The order of the cues in the collection is irrelevant; the {@link CuePlayer} will
	 * play cues in the natural order regardless.
	 * @param cues A collection of cues that this {@link CuePlayer} must execute sequentially
	 * and at the appropriate times.
	 */
	public void setCues(Collection<Cue> cues) {
		queue.clear();
		// Allow the heap to take care of ordering.
		queue.addAll(cues);
	}
	
	/**
	 * Starts execution of cues. All cues are executed relative to the time at which this method is called.
	 */
	public void start() {
		// Store the time at which we started in order to maintain reasonable precision.
		startTime = System.currentTimeMillis();
		// And execute the next queue as soon as possible.
		nextCue();
	}
	
	/**
	 * Stops execution of cues. No more cues will execute after this method is called.
	 */
	public void stop() {
		events.shutdown();
		actions.shutdown();
		startTime = -1;
	}
	
	private void nextCue() {
		Cue cue = queue.poll();
		if (cue == null) {
			stop();
			return;
		};
		
		Runnable act = new CueLoader(cue);
		
		// Schedules the next cue to occur 
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
