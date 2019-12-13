package bgu.spl.mics;

import bgu.spl.mics.application.subscribers.M;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {
    M subscriberM;

    @BeforeEach
    public void setUp(){
        subscriberM = new M();
    }

    @Test
        void subscribeEvent(){



    }
}
