package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

public class MissionReceivedEvent implements Event<String> {
    private MissionInfo missionToExecute;


    public MissionReceivedEvent(MissionInfo mission) {
        this.missionToExecute = mission;
    }

    public MissionInfo getMission() {
        return missionToExecute;
    }
}

