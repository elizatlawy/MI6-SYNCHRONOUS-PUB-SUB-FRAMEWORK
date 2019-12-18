package bgu.spl.mics;

import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {
    MessageBrokerImpl messageBroker;
    M subscriberM;


    @BeforeEach
    public void setUp() {
        subscriberM = new M(1);
        messageBroker = new MessageBrokerImpl();
    }

    @Test
    void subscribeEvent() throws InterruptedException {

    }
}
