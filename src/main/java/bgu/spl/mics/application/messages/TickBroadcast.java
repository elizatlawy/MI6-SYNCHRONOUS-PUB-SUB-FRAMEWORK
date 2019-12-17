package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private int tick;
    private int duration;


    /**
     * constructor.
     */
    public TickBroadcast(int tick, int duration) {
        this.tick = tick;
        this.duration = duration;
    }

    public int getTick() {
        return tick;
    }

    public int getDuration() {
        return duration;
    }
}



