package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MissionReceivedEvent implements Event<String> {
    private String missionName;
    private List<String> serialAgentsNumbers;
    private String gadget;

    public MissionReceivedEvent(String missionName, List<String> serialAgentsNumbers, String gadget) {
        this.missionName = missionName;
        this.serialAgentsNumbers = new CopyOnWriteArrayList<>();
        this.serialAgentsNumbers.addAll(serialAgentsNumbers);
        this.gadget = gadget;
    }
    public String getMissionName() {
        return missionName;
    }

    public List<String> getAgentNumber()
    {
        return serialAgentsNumbers;
    }

    public String getGadget() {
        return gadget;
    }
}

