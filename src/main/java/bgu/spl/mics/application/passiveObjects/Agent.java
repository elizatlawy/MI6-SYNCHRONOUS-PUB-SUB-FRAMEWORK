package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Agent {
	private String agentName;
	private String serialNumber;
	private boolean isAvailable;

	public Agent (String agentName, String serialNumber, boolean isAvailable){
		this.agentName = agentName;
		this.serialNumber = serialNumber;
		this.isAvailable = isAvailable;
	}
	/**
	 * Sets the serial number of an agent.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
     * Retrieves the serial number of an agent.
     * <p>
     * @return The serial number of an agent.
     */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the name of the agent.
	 */
	public void setName(String name) {
		this.agentName = agentName;
	}

	/**
     * Retrieves the name of the agent.
     * <p>
     * @return the name of the agent.
     */
	public String getName() {
		return agentName;
	}

	/**
     * Retrieves if the agent is available.
     * <p>
     * @return if the agent is available.
     */
	public synchronized boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * Acquires an agent.
	 */
	public synchronized void acquire(){
		if (!isAvailable)
			throw new RuntimeException(" Cannot acquire an agent that is already acquired for some other mission");
		isAvailable = false;
	}

	/**
	 * Releases an agent.
	 */
	public synchronized void release(){
		isAvailable = true;
		notifyAll();
	}
}
