package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentsAvailableEvent implements Event<Boolean> {
    private List<String> serialAgentsNumbers = new CopyOnWriteArrayList<String>();
    /**
     * Constructor
     */
    public AgentsAvailableEvent(List<String> serialAgentsNumbers) {
        this.serialAgentsNumbers.addAll(serialAgentsNumbers);
    }

    /**
     *
     * @return name of the agent to be checked
     */
    public List<String> getSerialAgentsNumbersNumber() {
        return serialAgentsNumbers;
    }
}
