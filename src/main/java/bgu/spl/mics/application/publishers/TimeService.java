package bgu.spl.mics.application.publishers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;


/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
	public static class TimeServiceHolder {
		private static TimeService instance = new TimeService();
	}

	private int speed;
	private int duration;
	private int tick;

	private TimeService() {
		super("TimeService");
		this.speed = 100;
		tick = 1;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public static TimeService getInstance() {
		return TimeService.TimeServiceHolder.instance;
	}

	@Override
	protected void initialize() {

	}

	@Override
	public void run() {
		while (tick <= duration){
			getSimplePublisher().sendBroadcast(new TickBroadcast(tick, duration));
			System.out.println(tick);
			try {
				Thread.sleep(speed);
				tick++;
			} catch (InterruptedException e) {
				break;
			}

		}
		getSimplePublisher().sendBroadcast(new TerminateBroadcast());
	}
}

