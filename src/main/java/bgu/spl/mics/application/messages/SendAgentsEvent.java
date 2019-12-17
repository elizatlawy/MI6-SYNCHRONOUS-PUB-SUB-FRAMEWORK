package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SendAgentsEvent implements Event<Boolean> {
    private CopyOnWriteArrayList serialAgentsNumbers = new CopyOnWriteArrayList();
    private int duration;

    public SendAgentsEvent(CopyOnWriteArrayList serialAgentsNumbers, int duration) {
        this.serialAgentsNumbers = serialAgentsNumbers;
        this.duration = duration;
    }

    public CopyOnWriteArrayList getSerialAgentsNumbers() {
        return serialAgentsNumbers;
    }
    public int getDuration() {
        return duration;
    }
}
