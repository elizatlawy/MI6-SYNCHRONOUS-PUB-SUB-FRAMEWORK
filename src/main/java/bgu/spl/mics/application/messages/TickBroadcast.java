package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private int tick;
    private final int speed = 100;
    private int duration;


    /**
     * constructor.
     */
    public TickBroadcast(int tick, int duration) {
        this.tick = tick;
        this.duration = duration;
    }
}



