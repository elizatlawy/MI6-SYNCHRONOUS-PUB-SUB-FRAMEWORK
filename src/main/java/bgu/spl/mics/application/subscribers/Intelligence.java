package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
    private int id;
    private List<MissionInfo> missions = new CopyOnWriteArrayList<>();

    public Intelligence(int id) {
        super("Intelligence No: " + id);
        this.id = id;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, (tickBroadcast) -> {
            for (MissionInfo currMission : missions) {
                if (currMission.getTimeIssued() == tickBroadcast.getTick()) {
                    System.out.println("Intelligence No:" + id + " is sending MissionReceivedEvent of: " + currMission.getMissionName());
                    getSimplePublisher().sendEvent(new MissionReceivedEvent(currMission));
                }
            }
        }); // end of lambda
    }
    public void load(List<MissionInfo> missionsToLoad) {
        this.missions.addAll(missionsToLoad);
    }
}
