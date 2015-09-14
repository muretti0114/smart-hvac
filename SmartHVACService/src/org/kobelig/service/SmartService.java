package org.kobelig.service;

import org.kobelig.state.State;

/**
 * Class for general smart service. Any concrete service should extend it.
 *
 * @author masa-n
 */
public abstract class SmartService {

	/** wait interval in msec */
	private int waitTime;
	/** flag for the service status */
	private boolean running = false;


	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Start service. Implemented as Template Method for any smart services.
	 * @throws InterruptedException
	 */
	public void startService() throws InterruptedException {
		running = true;

		//Repeat until explicitly stopped.
		while (running) {
			// (1) State Observation
			System.out.println("(Step 1) Observing State...");
			State s = observeState();

			// (2) State Interpretation
			System.out.println("(Step 2) Interpreting State...");
			Context c = interpreteState(s);

			// If the situation is not acceptable
			if (!isAcceptable(s, c)) {
				System.out.println("(Step 3) Proposing Actions...");
				// (3) Action Proposal
				Action [] acts =proposeActions(s, c);

				//(4) Action Execution
				System.out.println("(Step 4) Executing Actions...");
				executeActions(s, c, acts);
			} else {
				System.out.println("The current state is acceptable. No actions performed.");
			}

			System.out.println("Waiting " + waitTime + " msec before re-activating Step 1. ");
			System.out.println("===============================================================");
			// Sleep for a while
			Thread.sleep(waitTime);
		}

		System.out.println("Terminating service.");

	}

	/**
	 * Stop service.
	 *
	 */
	public void stopService() {
		running = false;
	}

	//public abstract void pause();
	//public abstract void resume();

	/**
	 * Perform state observation to obtain a state.
	 * @return The current state of a space.
	 */
	public abstract State observeState();

	/**
	 * Perform state interpretation based on a given state.
	 * @return Context reasoned from the state.
	 */
	public abstract Context interpreteState(State state);

	/**
	 *  Returns true if the current context is acceptable.
	 * @param context Context to be evaluated.
	 * @return true if acceptable.
	 */
	public abstract boolean isAcceptable(State state, Context context);

	/**
	 * Perform action proposal based on the context.
	 * @param context
	 * @return
	 */
	public abstract Action[] proposeActions(State state, Context context);

	/**
	 * Perform action execution for a given set of actions
	 * @param context
	 * @return
	 */
	public abstract void executeActions(State state, Context context, Action[] actions);

}
