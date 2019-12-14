package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private int tick;
    private int speed;
    private int duration;


    /**
     * constructor.
     */
    public TickBroadcast(int tick, int speed, int duration) {
        this.tick = tick;
        this.speed = speed;
        this.duration = duration;
    }
}



