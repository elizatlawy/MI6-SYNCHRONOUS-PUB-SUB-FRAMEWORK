package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private int id;
	private Diary diary;
	private MissionInfo currMission;
	private int currTick;



	public M(int id) {
		super("M No: " + id );
		this.id = id;
		diary = Diary.getInstance();

	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (brod) -> currTick = brod.getTick());
		subscribeEvent(MissionReceivedEvent.class, (ev)-> { currMission = ev.getMission();
		int qtime;
		Boolean isAgentsAvailable = null;
		Boolean isGadgetAvailable  = null;
		Future<Boolean> agentsAvailable = getSimplePublisher().sendEvent(new AgentsAvailableEvent(currMission.getSerialAgentsNumbers()));
		Future<Boolean> gadgetAvailable = getSimplePublisher().sendEvent(new GadgetAvailableEvent(currMission.getGadget()));
		// TODO check if possible to send negative time
		if(currTick <= currMission.getTimeExpired()){
			isAgentsAvailable = agentsAvailable.get((currMission.getTimeExpired() - currTick)*100, TimeUnit.MILLISECONDS);
			isGadgetAvailable = gadgetAvailable.get((currMission.getTimeExpired() - currTick)*100, TimeUnit.MILLISECONDS);
			qtime = currTick;

		}
		// check if can execute mission
		if(((isAgentsAvailable != null & isGadgetAvailable != null) && (isAgentsAvailable & isGadgetAvailable)) && (currTick < currMission.getTimeExpired())){
			getSimplePublisher().sendEvent(new SendAgentsEvent((CopyOnWriteArrayList) currMission.getSerialAgentsNumbers(),currMission.getDuration()));


		}

		// time is expired
		else {

		}
		// write to dairy

		}); // end of lambda

	}

}
