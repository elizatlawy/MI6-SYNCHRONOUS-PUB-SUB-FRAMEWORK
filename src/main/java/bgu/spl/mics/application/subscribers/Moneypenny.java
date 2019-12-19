package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Squad;

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
        // we assign the SendAgentsEvent only to 1 MoneyPenny with the ID of 1, all others will do the reset
        if(id == 1){
            subscribeEvent(SendAgentsEvent.class, (ev) -> {
                System.out.println("Moneypenny No:" + id + " is STARTING executing SendAgentsEvent " + ev.getSerialAgentsNumbers().toString());
                squad.sendAgents(ev.getSerialAgentsNumbers(), ev.getDuration());
                complete(ev, true);
                System.out.println("Moneypenny No:" + id + " is FINISHED  executing SendAgentsEvent " + ev.getSerialAgentsNumbers().toString());
            });

            subscribeEvent(ReleaseAgentEvent.class, (ev) -> {
                System.out.println("Moneypenny No:" + id + " is STARTING executing ReleaseAgentEvent " + ev.getAgentsSerialNumbers().toString());
                squad.releaseAgents(ev.getAgentsSerialNumbers());
                complete(ev, true);
                System.out.println("Moneypenny No:" + id + " is FINISHED executing ReleaseAgentEvent " + ev.getAgentsSerialNumbers().toString());
            });
        }
        else{ // all other MoneyPenny do AgentsAvailableEvent & GetAgentsNamesEvent
            subscribeEvent(AgentsAvailableEvent.class, (ev) -> {
                System.out.println("Moneypenny No:" + id + " is STARTING executing AgentsAvailableEvent of " + ev.getSerialAgentsNumbersNumber().toString());
                boolean isAgentsAvailable = squad.getAgents(ev.getSerialAgentsNumbersNumber());

                // if the agents available -> returns Moneypenny id, else returns -1
                if (isAgentsAvailable)
                    complete(ev, id);
                else
                    complete(ev, -1);
                System.out.println("Moneypenny No:" + id + " is FINISHED executing AgentsAvailableEvent of " + ev.getSerialAgentsNumbersNumber().toString());

            });
            subscribeEvent(GetAgentsNamesEvent.class, (ev) -> {
                complete(ev, squad.getAgentsNames(ev.getAgentsSerialNumbers()));
            });

        }
    }

    public int getId() {
        return id;
    }
}
