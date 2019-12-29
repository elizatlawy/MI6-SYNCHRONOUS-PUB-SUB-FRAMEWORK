package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
    private Inventory inventory;
    int currTick;

    public Q() {
        super("Q");
        inventory = Inventory.getInstance();
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, (brod) -> currTick = brod.getTick());
        subscribeEvent(GadgetAvailableEvent.class, (ev) -> {
            //System.out.println("Q is STARTING executing GadgetAvailableEvent of: " + ev.getGadgetName());
            boolean available = inventory.getItem(ev.getGadgetName());
            if (available)
                this.complete(ev, currTick);
            else
                this.complete(ev, -1);
            //System.out.println("Q ANSWER for GadgetAvailableEvent of: " + ev.getGadgetName() + " is: " + available + " currtick: " + currTick);
        });
    }
}
