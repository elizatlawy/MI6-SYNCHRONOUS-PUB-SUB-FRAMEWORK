package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {

	public TimeService() {
		super("Change_This_Name");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		
	}

	@Override
	public void run() {
		// TODO Implement this
	}

}
