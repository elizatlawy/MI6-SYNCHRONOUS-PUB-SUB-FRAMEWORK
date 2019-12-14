package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class MissionReceivedEvent implements Event<String> {
    private String missionName;
    private String agentNumber;
    private String gadget;

    public MissionReceivedEvent(String missionName, String agentNumber, String gadget) {
        this.missionName = missionName;
        this.agentNumber = agentNumber;
        this.gadget = gadget;
    }
}

