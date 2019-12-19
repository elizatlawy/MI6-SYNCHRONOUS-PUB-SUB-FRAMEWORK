package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    Inventory inventory;

    @BeforeEach
    public void setUp(){
        inventory = Inventory.getInstance();
    }
    @AfterEach
//    public void tearDown() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        Class InvClass = Class.forName("bgu.spl.mics.application.passiveObjects.Inventory");
//        Field instanceFiled = InvClass.getDeclaredField("instance");
//        instanceFiled.setAccessible(true);
//        Constructor InvConstructor = InvClass.getDeclaredConstructors()[0];
//        InvConstructor.setAccessible(true);
//        instanceFiled.set(instanceFiled, InvConstructor.newInstance());
//    }

    @Test
    void getInstanceTest() {
        Object inventory1 = Inventory.getInstance();
        assertNotEquals(null, inventory1);

        assertTrue(inventory1 instanceof Inventory);

        Object inventory2 = Inventory.getInstance();
        assertEquals(inventory1, inventory2);
    }

    @Test
    void loadTest() {
        String[] toLoad = {"helicopter", "tank", "gun", "RPG"};
        inventory.load(toLoad);
        assertTrue(inventory.getItem("helicopter"));
        assertTrue(inventory.getItem("gun"));
        assertFalse(inventory.getItem("boat"));
    }

    @Test
    void getItem() {
        String[] toLoad = {"helicopter", "tank", "gun", "RPG"};
        inventory.load(toLoad);
        assertFalse(inventory.getItem("sunGlasses"));
        inventory.load(new String[]{"sunGlasses"});
        assertTrue(inventory.getItem("sunGlasses"));

    }
}
