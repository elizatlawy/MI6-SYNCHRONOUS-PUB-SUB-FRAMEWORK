package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class AgentsAvailableEvent implements Event<String> {
    private String agentNumber;
    /**
     * Constructor
     */
    public AgentsAvailableEvent(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    /**
     *
     * @return name of the agent to be checked
     */
    public String getAgentNumber() {
        return agentNumber;
    }
}
