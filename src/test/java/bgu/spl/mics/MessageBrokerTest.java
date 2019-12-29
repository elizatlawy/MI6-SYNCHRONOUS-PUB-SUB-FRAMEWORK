package bgu.spl.mics;


import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.subscribers.ExampleBroadcastSubscriber;
import bgu.spl.mics.example.subscribers.ExampleEventHandlerSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class MessageBrokerTest {
    private MessageBroker messageBroker;
    private ExampleEvent exampleEvent;
    private ExampleBroadcast exampleBroadcast;


    @BeforeEach
    public void setUp() {
        messageBroker = MessageBrokerImpl.getInstance();
        exampleEvent = new ExampleEvent("exampleEvent");
        exampleBroadcast = new ExampleBroadcast("exampleBroadcast");

    }


    @Test
    void subscribeAndSendEventTest() throws InterruptedException {
        String[] mbt = {"1"};
        Subscriber sub = new ExampleEventHandlerSubscriber("ExampleEventHandlerSubscriber", mbt);
        Thread thread = new Thread(sub);
        thread.start();
        messageBroker.sendEvent(exampleEvent);
        //assertSame(messageBroker.awaitMessage(sub),exampleEvent,"event was not received");
    }

    @Test
    public void subscribeAndSendBroadcastTest() throws InterruptedException {
        String[] mbt = {"1"};
        Subscriber sub = new ExampleBroadcastSubscriber("ExampleBroadcastSubscriber", mbt);
        Thread thread = new Thread(sub);
        thread.start();
        messageBroker.sendBroadcast(exampleBroadcast);
    }

    @Test
    public void completeTest() {
        String result = "true";
        messageBroker.complete(exampleEvent, result);

    }

    @Test
    public void registerTest() {
        String[] mbt = {"1"};
        Subscriber sub = new ExampleEventHandlerSubscriber("ExampleEventHandlerSubscriber", mbt);
        messageBroker.register(sub);
    }

    @Test
    public void unregisterTest() {
        String[] mbt = {"1"};
        Subscriber sub = new ExampleEventHandlerSubscriber("ExampleEventHandlerSubscriber", mbt);
        messageBroker.register(sub);
        messageBroker.unregister(sub);
    }

    @Test
    public void awaitMessageTest() {
        String[] mbt = {"5"};
        Subscriber sub = new ExampleEventHandlerSubscriber("ExampleEventHandlerSubscriber", mbt);
        messageBroker.register(sub);
        Message msg = new ExampleEvent("msg");
        messageBroker.register(sub);
        messageBroker.subscribeEvent(ExampleEvent.class, sub);

        messageBroker.sendEvent(exampleEvent);
//        try {
//            msg = messageBroker.awaitMessage(sub);
//        } catch (InterruptedException e) {
//        }
//        messageBroker.sendEvent(exampleEvent);
//        try {
//            assertSame(messageBroker.awaitMessage(sub),exampleEvent,"event was not received");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
