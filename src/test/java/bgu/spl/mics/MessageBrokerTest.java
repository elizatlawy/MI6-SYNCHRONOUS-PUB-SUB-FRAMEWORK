package bgu.spl.mics;

import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {
    MessageBrokerImpl messageBroker;
    M subscriberM;


    @BeforeEach
    public void setUp(){
        subscriberM = new M();
        messageBroker = new MessageBrokerImpl();
    }

    @Test
        void subscribeEvent() throws InterruptedException {
        messageBroker.register(subscriberM);
        messageBroker.subscribeEvent(MissionReceivedEvent.class,subscriberM);
        MissionReceivedEvent newMission = new MissionReceivedEvent("testMission", "007", "pistol" );
        messageBroker.sendEvent(newMission);
        Message NewMessage = messageBroker.awaitMessage(subscriberM);
        assertTrue(NewMessage != null);
    }
}
