package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;

public class ReleaseAgentEvent implements Event<Boolean> {
    private List<String> AgentsSerialNumbers;
    public ReleaseAgentEvent(List<String> agentsSerialNumbers) {
        AgentsSerialNumbers = agentsSerialNumbers;
    }

    public List<String> getAgentsSerialNumbers() {
        return AgentsSerialNumbers;
    }
}
