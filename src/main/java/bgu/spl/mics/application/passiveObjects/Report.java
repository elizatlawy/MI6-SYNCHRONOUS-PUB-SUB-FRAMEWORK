package bgu.spl.mics.application.passiveObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive data-object representing a delivery vehicle of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Report {
    private String missionName;
    private int M;
    private int Moneypenny;
    private List<String> AgentsSerialNumbers = new LinkedList<>();
    private List<String> agentsNames = new LinkedList<>();
    private String gadgetName;
    private int qTime;
    private int timeIssued;
    private int timeCreated;

    public Report(int timeCreated) {
        this.timeCreated = timeCreated;
    }


    /**
     * Retrieves the mission name.
     */
    public String getMissionName() {
        return missionName;
    }

    /**
     * Sets the mission name.
     */
    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    /**
     * Retrieves the M's id.
     */
    public int getM() {
        return M;
    }

    /**
     * Sets the M's id.
     */
    public void setM(int m) {
        this.M = m;
    }

    /**
     * Retrieves the Moneypenny's id.
     */
    public int getMoneypenny() {
        return Moneypenny;
    }

    /**
     * Sets the Moneypenny's id.
     */
    public void setMoneypenny(int moneypenny) {
        this.Moneypenny = moneypenny;
    }

    /**
     * Retrieves the serial numbers of the agents.
     * <p>
     *
     * @return The serial numbers of the agents.
     */
    public List<String> getAgentsSerialNumbers() {
        return AgentsSerialNumbers;
    }

    /**
     * Sets the serial numbers of the agents.
     */
    public void setAgentsSerialNumbers(List<String> agentsSerialNumbers) {
        this.AgentsSerialNumbers.clear();
        this.AgentsSerialNumbers.addAll(agentsSerialNumbers);
    }

    /**
     * Retrieves the agents names.
     * <p>
     *
     * @return The agents names.
     */
    public List<String> getAgentsNames() {
        return agentsNames;
    }

    /**
     * Sets the agents names.
     */
    public void setAgentsNames(List<String> agentsNames) {
        this.agentsNames.clear();
        this.agentsNames.addAll(agentsNames);
    }

    /**
     * Retrieves the name of the gadget.
     * <p>
     *
     * @return the name of the gadget.
     */
    public String getGadgetName() {
        return gadgetName;
    }

    /**
     * Sets the name of the gadget.
     */
    public void setGadgetName(String gadgetName) {
        this.gadgetName = gadgetName;
    }

    /**
     * Retrieves the time-tick in which Q Received the GadgetAvailableEvent for that mission.
     */
    public int getQTime() {
        return qTime;
    }

    /**
     * Sets the time-tick in which Q Received the GadgetAvailableEvent for that mission.
     */
    public void setQTime(int qTime) {
        this.qTime = qTime;
    }

    /**
     * Retrieves the time when the mission was sent by an Intelligence Publisher.
     */
    public int getTimeIssued() {
        return timeIssued;
    }

    /**
     * Sets the time when the mission was sent by an Intelligence Publisher.
     */
    public void setTimeIssued(int timeIssued) {
        this.timeIssued = timeIssued;
    }

    /**
     * Retrieves the time-tick when the report has been created.
     */
    public int getTimeCreated() {
        return timeCreated;
    }

    /**
     * Sets the time-tick when the report has been created.
     */
    public void setTimeCreated(int timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "Report{" +
                "missionName='" + missionName + '\'' +
                ", M=" + M +
                ", Moneypenny=" + Moneypenny +
                ", AgentsSerialNumbers=" + AgentsSerialNumbers +
                ", agentsNames=" + agentsNames +
                ", gadgetName='" + gadgetName + '\'' +
                ", qTime=" + qTime +
                ", timeIssued=" + timeIssued +
                ", timeCreated=" + timeCreated +
                '}' + '\n';
    }
}
