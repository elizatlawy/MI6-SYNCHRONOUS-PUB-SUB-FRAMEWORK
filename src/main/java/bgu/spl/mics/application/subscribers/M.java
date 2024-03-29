package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
    private int id;
    private Diary diary = Diary.getInstance();
    private MissionInfo currMission;
    private int currTick;

    public M(int id) {
        super("M No: " + id);
        this.id = id;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, (brod) -> currTick = brod.getTick());
        subscribeEvent(MissionReceivedEvent.class, (ev) -> {
            diary.incrementTotal();
            currMission = ev.getMission();
            //System.out.println("M No:" + id + " is STARTING executing MissionReceivedEvent of: " + currMission.getMissionName() + " currtick: " + currTick);
            Integer qtime = -1;
            Integer moneypennyID;
            Future<Integer> agentsAvailable = getSimplePublisher().sendEvent(new AgentsAvailableEvent(currMission.getSerialAgentsNumbers()));
            moneypennyID = agentsAvailable.get((currMission.getTimeExpired() - currTick) * 100, TimeUnit.MILLISECONDS);
            Future<List<String>> agentsNamesFuture;
            if (moneypennyID != null && moneypennyID > 0 ) {
                Future<Integer> gadgetAvailable = getSimplePublisher().sendEvent(new GadgetAvailableEvent(currMission.getGadget()));
                qtime = gadgetAvailable.get((currMission.getTimeExpired() - currTick) * 100, TimeUnit.MILLISECONDS);
            }
            if ((moneypennyID != null & qtime != null) && ((moneypennyID > 0) & qtime > 0) && (qtime < currMission.getTimeExpired())) {
                agentsNamesFuture = getSimplePublisher().sendEvent(new GetAgentsNamesEvent(currMission.getSerialAgentsNumbers()));
                getSimplePublisher().sendEvent(new SendAgentsEvent(currMission.getSerialAgentsNumbers(), currMission.getDuration()));
                if ((agentsNamesFuture != null) && agentsNamesFuture.get((currMission.getTimeExpired() - qtime) * 100, TimeUnit.MILLISECONDS) != null)
                    addReport(qtime,moneypennyID,agentsNamesFuture.get()); // write the report
            }
            // time is expired -> Mission abort
            else {
                //System.out.println("M No:" + id + " is FINISHED executing MissionReceivedEvent of: " + currMission.getMissionName() + ": MISSION FAILED");
                getSimplePublisher().sendEvent(new ReleaseAgentEvent(currMission.getSerialAgentsNumbers()));
            }
        }); // end of lambda
    }

    /**
     * add new report to the diary
     * @param qtime the time-tick in which Q Received the GadgetAvailableEvent for that mission
     * @param moneypennyID the Moneypenny instance who calls squad.getAgents()
     * @param agentsNames the list of the agentsNames that sent to the mission
     */

    private void addReport(int qtime, int moneypennyID,  List<String> agentsNames){
        Report missionReport = new Report(qtime);
        missionReport.setMissionName(currMission.getMissionName());
        missionReport.setM(id);
        missionReport.setMoneypenny(moneypennyID);
        missionReport.setAgentsSerialNumbers(currMission.getSerialAgentsNumbers());
        missionReport.setAgentsNames(agentsNames);
        missionReport.setGadgetName(currMission.getGadget());
        missionReport.setQTime(qtime);
        missionReport.setTimeIssued(currMission.getTimeIssued());
        diary.addReport(missionReport);
        //System.out.println("M No:" + id + " is FINISHED executing MissionReceivedEvent of: " + currMission.getMissionName() + ": MISSION SUCCEEDED");
    }
}


