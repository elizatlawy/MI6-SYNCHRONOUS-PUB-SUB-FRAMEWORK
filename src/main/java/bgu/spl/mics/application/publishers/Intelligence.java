package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Publisher {
	private List<MissionInfo> missions = new CopyOnWriteArrayList<>();

	public Intelligence() {
		super("Intelligence");
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

	public void load(List<MissionInfo> missionsToLoad){
		this.missions.addAll(missionsToLoad);
	}

}
