package bgu.spl.mics.application.passiveObjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

    private Map<String, Agent> agents;

//    public boolean isAllReleased() {
//        for (Agent agent : agents.values())
//            if (!agent.isAvailable())
//                return false;
//        return true;
//    }

    private static class SquadHolder {
        private static Squad instance = new Squad();
    }

    private Squad() {
        agents = new HashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Squad getInstance() {
        return SquadHolder.instance;
    }

    /**
     * Initializes the squad. This method adds all the agents to the squad.
     * <p>
     *
     * @param agents Data structure containing all data necessary for initialization
     *               of the squad.
     */
    public void load(Agent[] agents) {
        for (Agent agent : agents)
            this.agents.putIfAbsent(agent.getSerialNumber(), agent);
    }

    /**
     * Releases agents.
     */
    public void releaseAgents(List<String> serials) {
        if(serials != null){
            for (String serialNumber : serials) {
                if(agents.get(serialNumber) != null){
                    agents.get(serialNumber).release();
                    System.out.println("###################################### releaseAgents: " + serialNumber);
                }
            }
        }
    }

    /**
     * simulates executing a mission by calling sleep.
     *
     * @param time time-ticks to sleep
     */
    public void sendAgents(List<String> serials, int time) {
        try {
            Thread.sleep(time * 100);
        } catch (InterruptedException e) {
        }
        for (String serialNumber : serials) {
            agents.get(serialNumber).release();
            System.out.println("###################################### sendAgents: " + serialNumber);
        }
    }

    /**
     * acquires an agent, i.e. holds the agent until the caller is done with it
     *
     * @param serials the serial numbers of the agents
     * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
     */
    public boolean getAgents(List<String> serials) {
        //check if the agents exist in agents
        serials.sort(String::compareTo);
        for (String serialNumber : serials) {
            if (!agents.containsKey(serialNumber))
                return false;
        }
		/* acquire the agents to the mission, and if some agent is acquired for other mission
		the function will wait until the agent becomes available */
        boolean problem;
        LinkedList<String> acquired = new LinkedList<>();
        do {
            problem = false;
            Agent currAgent;
            for (String serialNumber : serials) {
                currAgent = agents.get(serialNumber);
                synchronized (currAgent) {
                    if (currAgent.isAvailable()) {
                        currAgent.acquire();
                        acquired.add(serialNumber);
                    } else {
                        try {
                            problem = true;
                            releaseAgents(acquired);
                            currAgent.wait(); // waiting for currAgent to become available
                            break; // break the for loop
                        } catch (InterruptedException e) {
                            return false;
                        }

                    }
                } // end of sync
            }
        } while (problem);
        return true;
    }

    /**
     * gets the agents names
     *
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials) {
        List<String> AgentListName = new LinkedList<>();
        for (String serialNumber : serials)
            AgentListName.add(agents.get(serialNumber).getName());
        return AgentListName;
    }

}
