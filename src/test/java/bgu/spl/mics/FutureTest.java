package bgu.spl.mics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {

    Future future;

    @BeforeEach
    public void setUp(){
        this.future = new Future<>();
    }

    @Test
    public void getTest() {
        Thread thread = new Thread(() -> future.get());
        thread.start();
        assertEquals(Thread.State.WAITING, thread.getState());
        future.resolve("test");
        assertNotEquals(Thread.State.WAITING, thread.getState());
        assertNotEquals(Thread.State.BLOCKED, thread.getState());
        assertEquals("test", future.get());
    }

    @Test
    public void resolveTest() {
    String test1 = "test1";

    assertFalse(future.isDone());
    future.resolve("test1");
    assertTrue(future.isDone());
    assertEquals(test1,future.get());
    }

    @Test
    public void isDoneTest() {
        assertFalse(future.isDone());
        future.resolve("test");
        assertTrue(future.isDone());

    }

    @Test
    public void getWithTimeoutTest() {
        assertEquals(null, future.get(1, TimeUnit.SECONDS));
        future.resolve("test");
        assertEquals("test", future.get(1, TimeUnit.SECONDS));
    }

}
