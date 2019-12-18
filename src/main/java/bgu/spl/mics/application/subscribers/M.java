package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private int id;
	private Diary diary = Diary.getInstance();;
	private MissionInfo currMission;
	private int currTick;
	public M(int id) {
		super("M No: " + id );
		this.id = id;
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, (brod) -> currTick = brod.getTick());
		subscribeEvent(MissionReceivedEvent.class, (ev)-> { currMission = ev.getMission();
			System.out.println("M No:" + id + " is executing MissionReceivedEvent of: " + currMission.getMissionName());
		int qtime = -1;
		Integer moneypennyID = null;
		Boolean isGadgetAvailable  = null;
		Future<Integer> agentsAvailable = getSimplePublisher().sendEvent(new AgentsAvailableEvent(currMission.getSerialAgentsNumbers()));

		Future<Boolean> gadgetAvailable = getSimplePublisher().sendEvent(new GadgetAvailableEvent(currMission.getGadget()));
		// TODO check if possible to send negative time
		if(currTick <= currMission.getTimeExpired()){
			moneypennyID = agentsAvailable.get((currMission.getTimeExpired() - currTick)*100, TimeUnit.MILLISECONDS);
			isGadgetAvailable = gadgetAvailable.get((currMission.getTimeExpired() - currTick)*100, TimeUnit.MILLISECONDS);
			qtime = currTick;

		}
		// check if can execute mission
		if(((moneypennyID != null & isGadgetAvailable != null) && ((moneypennyID > 0) & isGadgetAvailable)) && (currTick < currMission.getTimeExpired())){
			Future<Boolean> missionComplete = getSimplePublisher().sendEvent(new SendAgentsEvent((CopyOnWriteArrayList) currMission.getSerialAgentsNumbers(),currMission.getDuration()));
			missionComplete.get();
			Future<List<String>> agentsNamesFuture = getSimplePublisher().sendEvent(new GetAgentsNamesEvent(currMission.getSerialAgentsNumbers()));
			// the mission finished, can write the report
			Report missionReport = new Report(currTick);
			missionReport.setMissionName(currMission.getMissionName());
			missionReport.setM(id);
			missionReport.setMoneypenny(moneypennyID);
			missionReport.setAgentsSerialNumbersNumber(currMission.getSerialAgentsNumbers());
			missionReport.setAgentsNames(agentsNamesFuture.get());
			missionReport.setGadgetName(currMission.getGadget());
			missionReport.setQTime(qtime);
			missionReport.setTimeIssued(currMission.getTimeIssued());
			diary.addReport(missionReport);
		}
		// time is expired -> Mission abort
		else
			getSimplePublisher().sendEvent(new ReleaseAgentEvent(currMission.getSerialAgentsNumbers()));
		diary.increaseTotal();
		}); // end of lambda
	}
}
