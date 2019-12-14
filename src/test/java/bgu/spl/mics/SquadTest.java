package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class
SquadTest {

    Squad squad;

    @BeforeEach
    public void setUp(){
        squad = Squad.getInstance();
    }
    @AfterEach
    public void tearDown() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class SquadClass = Class.forName("bgu.spl.mics.application.passiveObjects.Squad");
        Field instanceFiled = SquadClass.getDeclaredField("instance");
        instanceFiled.setAccessible(true);
        Constructor InvConstructor = SquadClass.getDeclaredConstructors()[0];
        InvConstructor.setAccessible(true);
        instanceFiled.set(instanceFiled, InvConstructor.newInstance());
    }

    @Test
    void getInstanceTest() {
        Object squad1 = Squad.getInstance();
        assertNotEquals(null, squad1);

        assertTrue(squad1 instanceof Squad);

        Object squad2 = Inventory.getInstance();
        assertEquals(squad1, squad2);
    }

    @Test
    void load() {
        Agent agent1 = new Agent("Eli", "007", true);
        Agent agent2 = new Agent("Bond", "006", true);
        Agent[] agents = {agent1, agent2};
        squad.load(agents);
        List<String> serials = new LinkedList<>();
        serials.add("007");
        serials.add("006");
        List<String> RetrivesAgentsName = squad.getAgentsNames(serials);
        assertTrue(RetrivesAgentsName.contains("007"));
        assertTrue(RetrivesAgentsName.contains("006"));
    }

    @Test
    void releaseAgents() {
        Agent agent1 = new Agent("Eli", "007", false);
        Agent agent2 = new Agent("Bond", "006", false);
        Agent[] agents = {agent1, agent2};
        squad.load(agents);
        List<String> serials = new LinkedList<>();
        serials.add("007");
        serials.add("006");
        squad.releaseAgents(serials);
        assertTrue (agent1.isAvailable());
        assertEquals (agent1.isAvailable(), agent2.isAvailable());
        }

    @Test
    void sendAgents() {

    }

    @Test
    void getAgents() {
        Agent agent1 = new Agent("Eli", "007", true);
        Agent agent2 = new Agent("Bond", "006", true);
        Agent agent3 = new Agent("Nadav", "005", true);
        Agent agent4 = new Agent("Tomer", "004", false);
        Agent[] agents = {agent1, agent2};
        squad.load(agents);
        List<String> serials1 = new LinkedList<>();
        serials1.add("007");
        serials1.add("006");
        assertTrue(squad.getAgents(serials1));
        List<String> serials2 = new LinkedList<>();
        serials1.add("004");
        assertFalse (squad.getAgents(serials2));
        assertFalse(agent1.isAvailable());
        assertFalse(agent2.isAvailable());
        assertTrue(agent3.isAvailable());
    }

    @Test
    void getAgentsNames() {

        Agent agent1 = new Agent("Eli", "007", true);
        Agent agent2 = new Agent("Bond", "006", true);
        Agent agent3 = new Agent("Nadav", "005", true);
        Agent[] agents = {agent1, agent2};
        squad.load(agents);
        List<String> serials = new LinkedList<>();
        serials.add("007");
        serials.add("006");
        List<String> RetrivesAgentsName = squad.getAgentsNames(serials);
        assertTrue (RetrivesAgentsName.contains("Eli"));
        assertFalse (RetrivesAgentsName.contains("Nadav"));
    }
}
