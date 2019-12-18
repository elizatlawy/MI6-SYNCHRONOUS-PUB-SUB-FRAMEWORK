package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.LinkedList;
import java.util.List;

public class GetAgentsNamesEvent  implements Event<List<String>> {
    private List<String> AgentsSerialNumbersNumber;

    public GetAgentsNamesEvent(List<String> agentsSerialNumbersNumber) {
        AgentsSerialNumbersNumber = agentsSerialNumbersNumber;
    }

    public List<String> getAgentsSerialNumbersNumber() {
        return AgentsSerialNumbersNumber;
    }
}
