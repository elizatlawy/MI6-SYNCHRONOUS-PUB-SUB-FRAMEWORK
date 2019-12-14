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
    public String getMissionName() {
        return missionName;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public String getGadget() {
        return gadget;
    }
}

