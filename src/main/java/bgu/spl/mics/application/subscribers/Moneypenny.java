package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
    private int id;
    private Squad squad;
    public Moneypenny(int id) {
        super("Moneypenny No: " + id);
        this.id = id;
        squad = Squad.getInstance();
    }

    @Override
    protected void initialize() {
        subscribeEvent(AgentsAvailableEvent.class, (ev) -> {
            System.out.println("Moneypenny No:" + id + " is executing AgentsAvailableEvent of " + ev.getSerialAgentsNumbersNumber().toString());
            boolean isAgentsAvailable = squad.getAgents(ev.getSerialAgentsNumbersNumber());

            // if the agents available -> returns Moneypenny id, else returns -1
            if (isAgentsAvailable)
                complete(ev, id);
            else
                complete(ev, -1);

        });
        subscribeEvent(SendAgentsEvent.class, (ev) -> {
            System.out.println("Moneypenny No:" + id + " is executing SendAgentsEvent " + ev.getSerialAgentsNumbers().toString());
            squad.sendAgents(ev.getSerialAgentsNumbers(), ev.getDuration());
            complete(ev, true);
        });
        subscribeEvent(GetAgentsNamesEvent.class, (ev) -> {
            complete(ev, squad.getAgentsNames(ev.getAgentsSerialNumbersNumber()));
        });
        subscribeEvent(ReleaseAgentEvent.class, (ev) -> {
            System.out.println("Moneypenny No:" + id + " is executing ReleaseAgentEvent " + ev.getAgentsSerialNumbersNumber().toString());
            squad.releaseAgents(ev.getAgentsSerialNumbersNumber());
            complete(ev, true);
        });
    }

    public int getId() {
        return id;
    }
}
