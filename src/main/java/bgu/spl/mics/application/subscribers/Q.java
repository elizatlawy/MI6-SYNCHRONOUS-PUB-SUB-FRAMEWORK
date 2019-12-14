package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inventory;

	public Q() {
		super("Q");
		inventory = Inventory.getInstance();
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		subscribeEvent(GadgetAvailableEvent.class, (ev) -> this.complete(ev,inventory.getItem(ev.getGadgetName())));
		
	}

}
