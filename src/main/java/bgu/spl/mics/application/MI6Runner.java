package bgu.spl.mics.application;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Squad;
import bgu.spl.mics.application.publishers.Intelligence;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {

        try {
            String str = new String(Files.readAllBytes(Paths.get(args[0])));
            JsonObject obj = new Gson().fromJson(str, JsonObject.class);
            // read the json to the fields
            LinkedList<Subscriber> subscribers = new LinkedList<>();
            LinkedList<Publisher> Publishers = new LinkedList<>();
            insertToInventory(obj);
            insertToSquad(obj);
            insertToService(obj, Publishers, subscribers);
            Intelligence intel = new Intelligence();
            System.out.println("done");
        } catch (IOException e) {
        }
    }

    private static void insertToInventory(JsonObject obj) {
        // get Gadgets
        LinkedList<String> GadgetsToInsert = new LinkedList<>();
        for (String gadget : obj.inventory)
            GadgetsToInsert.add(new String(gadget));
        // load Gadgets
        Inventory.getInstance().load(GadgetsToInsert.toArray(new String[0]));
    }

    private static void insertToSquad(JsonObject obj) {
        // get Agents
        LinkedList<Agent> AgentsToInsert = new LinkedList<>();
        for (JsonObject.JsonSquad agent : obj.squad)
            AgentsToInsert.add(new Agent(agent.name, agent.serialNumber, true));
        // load Agents
        Squad.getInstance().load(AgentsToInsert.toArray(new Agent[AgentsToInsert.size()]));
    }

    private static void insertToService(JsonObject obj, List<Publisher> Publishers, List<Subscriber> subscribers) {
        for (int i = 1; i <= obj.services.M; i++)
            subscribers.add(new M());
        for (int i = 1; i <= obj.services.Moneypenny; i++)
            subscribers.add(new Moneypenny());
        for (int i = 1; i <= obj.services.intelligence.length; i++) {
            Intelligence intel = new Intelligence();
            LinkedList<MissionInfo> MissionsToLoad = new LinkedList<>();
            for (JsonObject.JsonServices.JsonIntelligence.JsonMissions currMission : obj.services.intelligence[i].missions) {
                MissionInfo mission = new MissionInfo(currMission.missionName, Arrays.asList(currMission.serialAgentsNumbers), currMission.gadget, currMission.timeIssued, currMission.timeExpired, currMission.duration);
                MissionsToLoad.add(mission);
            }
            intel.load(MissionsToLoad);
            Publishers.add(intel);
        }
        //TODO insert time to timeService


    }


}

