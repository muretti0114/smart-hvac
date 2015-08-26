package org.kobelig.service;

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
			State s = observeState();

			// (2) State Interpretation
			Context c = interpreteState(s);

			// If the situation is not acceptable
			if (!isAcceptable(c)) {
				// (3) Action Proposal
				Action [] acts =proposeActions(c);

				//(4) Action Execution
				executeActions(acts);
			}

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
	public abstract boolean isAcceptable(Context context);

	/**
	 * Perform action proposal based on the context.
	 * @param context
	 * @return
	 */
	public abstract Action[] proposeActions(Context context);

	/**
	 * Perform action execution for a given set of actions
	 * @param context
	 * @return
	 */
	public abstract void executeActions(Action[] actions);

}
