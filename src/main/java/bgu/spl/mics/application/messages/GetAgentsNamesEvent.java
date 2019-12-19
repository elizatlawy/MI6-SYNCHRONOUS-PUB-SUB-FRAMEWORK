package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;

public class GetAgentsNamesEvent  implements Event<List<String>> {
    private List<String> AgentsSerialNumbers;

    public GetAgentsNamesEvent(List<String> agentsSerialNumbers) {
        AgentsSerialNumbers = agentsSerialNumbers;
    }

    public List<String> getAgentsSerialNumbers() {
        return AgentsSerialNumbers;
    }
}
