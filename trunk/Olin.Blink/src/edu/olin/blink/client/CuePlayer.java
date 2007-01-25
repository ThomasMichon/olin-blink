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
 * 		 In order to initialize and play a sequence of <code>Cue</code>s, the following actions must be performed:
 * </p>
 * <ol>
 * 		 <li>The {@link #setCues} method is called with a <code>List</code> of <code>Cue</code> objects.</li>
 * 		<li>The {@link #start} method is called at the desired start time.</li>
 * </ol>
 */
public class CuePlayer {
	// this thread will handle the timing. Although we could theoretically schedule all cues
	// using the schedule() method alone, it's not possible to schedule stuff at once without
	// the thread starting execution. So we schedule cues one at a time on a relative basis.
	private ScheduledExecutorService events;
	
	// this is the thread on which all cues will execute, unless they create their own threads.
	// we do this to ensure that cues are fired as quickly as possible, so we don't run out of time.
	private ExecutorService actions;
	
	// Since we execute cues in sequence, all we need is a heap sorted by time of execution.
	private Queue<Cue> queue = new PriorityQueue<Cue>();
	
	// We need to store the last start-time of the player in order to ensure accuracy.
	private long startTime = -1;
	
	// This helps with synchronization, to ensure that we don't wind up causing things
	// to happen that we don't want.
	private boolean running = false;
	
	/**
	 * Creates a new <code>CuePlayer</code>.
	 */
	public CuePlayer() {
	}
	
	/**
	 * Gets the time at which this <code>CuePlayer</code> started execution of a sequence of cues.
	 * @return The system time (according to {@link java.lang.System#currentTimeMillis})
	 * at which the <code>start</code> method was called, or -1
	 * if the <code>CuePlayer</code> is not currently executing cues.
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * Sets the sequence of cues to be those in the given <code>Collection</code>.
	 * The order of the cues in the collection is irrelevant; the <code>CuePlayer</code> will
	 * play cues in the natural order regardless.
	 * @param cues A collection of cues that this {@link CuePlayer} must execute sequentially
	 * and at the appropriate times.
	 */
	public void setCues(Collection<Cue> cues) {
		synchronized (this) {
			if (running) return;
		}
		
		queue.clear();
		// Allow the heap to take care of ordering.
		queue.addAll(cues);
	}
	
	/**
	 * Starts execution of cues. All cues are executed relative to the time at which this method is called.
	 */
	public void start() {
		synchronized(this) {
			if (running) return;
			running = true;
		}

		// Hopefully calling these don't take too long.
		events = Executors.newSingleThreadScheduledExecutor();
		actions = Executors.newSingleThreadExecutor();

		// Store the time at which we started in order to maintain reasonable precision.
		startTime = System.currentTimeMillis();
		// And execute the next cue as soon as possible.
		nextCue();
	}
	
	/**
	 * Stops execution of cues. No more cues will execute after this method is called.
	 */
	public void stop() {
		synchronized (this) {
			if (! running) return;
			running = false;
		}
		
		events.shutdown();
		actions.shutdown();
		
		queue.clear();
		
		startTime = -1;
	}
	
	/**
	 * Determines if this <code>CuePlayer</code> is currently executing cues.
	 * A <code>CuePlayer</code> executes cues as long as it has cues to execute and has been started.
	 * @return whether or not this {@link CuePlayer} is currently executing cues.
	 */
	public boolean isRunning() {
		synchronized (this) {
			return running;
		}
	}
	
	/**
	 * Gets the elapsed time that has progressed from when this <code>CuePlayer</code> started running.
	 * @return a time value in milliseconds.
	 */
	public long getElapsedTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	private void nextCue() {
		Cue cue = queue.poll();
		if (cue == null) {
			stop();
			return;
		};
		
		// this way we don't have to delcare any final variables
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
