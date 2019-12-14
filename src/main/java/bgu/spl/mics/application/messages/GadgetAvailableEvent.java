package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class GadgetAvailableEvent implements Event<Boolean> {
    private String gadgetName;
    /**
     * Constructor
     */
    public GadgetAvailableEvent(String gadgetName) {
        this.gadgetName = gadgetName;
    }

    /**
     *
     * @return name of the gadget to be checked
     */
    public String getGadgetName() {
        return gadgetName;
    }
}
