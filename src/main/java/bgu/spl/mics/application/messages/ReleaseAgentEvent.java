package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;

public class ReleaseAgentEvent implements Event<Boolean> {
    private List<String> AgentsSerialNumbersNumber;

    public ReleaseAgentEvent(List<String> agentsSerialNumbersNumber) {
        AgentsSerialNumbersNumber = agentsSerialNumbersNumber;
    }

    public List<String> getAgentsSerialNumbersNumber() {
        return AgentsSerialNumbersNumber;
    }
}
