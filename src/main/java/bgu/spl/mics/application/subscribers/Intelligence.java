package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.TickBroadcast;
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
public class Intelligence extends Subscriber {
	private List<MissionInfo> missions = new CopyOnWriteArrayList<>();
	private int currTick;

	public Intelligence() {
		super("Intelligence");
	}

	@Override
	protected void initialize() {
		subscribeEvent(TickBroadcast.class, tickBroadcast-> currTick = tickBroadcast.g );

//		for (MissionInfo currMission : missions){
//			if(currMission.getTimeIssued() == tickBroadcast.getTick()){
//
//			}

	}

	public void load(List<MissionInfo> missionsToLoad){
		this.missions.addAll(missionsToLoad);
	}

}
