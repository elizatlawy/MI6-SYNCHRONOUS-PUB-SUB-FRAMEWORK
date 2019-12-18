package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.LinkedList;
import java.util.List;

public class SendAgentsEvent implements Event<Boolean> {
    private LinkedList<String> serialAgentsNumbers;
    private int duration;

    public SendAgentsEvent(List<String> serialAgentsNumbers, int duration) {
        this.serialAgentsNumbers = new LinkedList<>();
        this.serialAgentsNumbers.addAll(serialAgentsNumbers);
        this.duration = duration;
    }

    public LinkedList<String> getSerialAgentsNumbers() {
        return serialAgentsNumbers;
    }
    public int getDuration() {
        return duration;
    }
}
