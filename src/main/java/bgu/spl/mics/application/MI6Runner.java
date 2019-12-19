package bgu.spl.mics.application;

import bgu.spl.mics.Printer;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */

public class MI6Runner {

    public static void main(String[] args) {
        // json parse
        try {
            // read the json
            String str = new String(Files.readAllBytes(Paths.get(args[0])));
            JsonObject obj = new Gson().fromJson(str, JsonObject.class);
            // load objects
            LinkedList<Subscriber> subscribers = new LinkedList<>();
            loadInventory(obj);
            loadSquad(obj);
            loadSubscribers(obj, subscribers);
            // set duration to TimeService
            int duration = obj.services.time;
            TimeService timeService = TimeService.getInstance();
            timeService.setDuration(duration);
            // run threads
            // run
            Squad squad = Squad.getInstance();
            Inventory inventory = Inventory.getInstance();
            executeThreads(subscribers);
            // print
            inventory.printToFile(args[1]);
            Diary.getInstance().printToFile(args[2]);


        } catch (IOException e) {
            throw new RuntimeException("illegal json file");
        }
    }

    private static void executeThreads(List<Subscriber> subscribers) {
        List<Thread> threads = new LinkedList<>();
        // initialize all subscribers before the timeService
        for (Subscriber currSubscriber : subscribers) {
            Thread thread = new Thread(currSubscriber, currSubscriber.getName());
            threads.add(thread);
            thread.start();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread timeServiceThread = new Thread(TimeService.getInstance(), TimeService.getInstance().getName());
        timeServiceThread.start();

        for (int i = 0; i < threads.size(); i++) {
            Thread t = threads.get(i);
            try {
                System.out.println("terminating: " + t.getName());
                t.join();
                if (i < threads.size() - 1)
                    System.out.println("terminated: " + t.getName() + " NEXT to terminate: " + threads.get(i + 1));
            } catch (InterruptedException e) {
            }
        }
        // Todo: delete this before submission
        System.out.println("THE Diary: ");
        System.out.println(Diary.getInstance().toString());
    }


    private static void loadInventory(JsonObject obj) {
        // get Gadgets
        LinkedList<String> GadgetsToInsert = new LinkedList<>();
        for (String gadget : obj.inventory)
            GadgetsToInsert.add(gadget);
        // load Gadgets
        Inventory.getInstance().load(GadgetsToInsert.toArray(new String[0]));
    }

    private static void loadSquad(JsonObject obj) {
        // get Agents
        LinkedList<Agent> AgentsToInsert = new LinkedList<>();
        for (JsonObject.JsonSquad agent : obj.squad)
            AgentsToInsert.add(new Agent(agent.name, agent.serialNumber, true));
        // load Agents
        Squad.getInstance().load(AgentsToInsert.toArray(new Agent[AgentsToInsert.size()]));
    }

    private static void loadSubscribers(JsonObject obj, List<Subscriber> subscribers) {
        for (int i = 0; i < obj.services.intelligence.length; i++) {
            Intelligence intel = new Intelligence(i + 1);
            LinkedList<MissionInfo> MissionsToLoad = new LinkedList<>();
            for (JsonObject.JsonServices.JsonIntelligence.JsonMissions currMission : obj.services.intelligence[i].missions) {
                MissionInfo mission = new MissionInfo(currMission.missionName, Arrays.asList(currMission.serialAgentsNumbers), currMission.gadget, currMission.timeIssued, currMission.timeExpired, currMission.duration);
                MissionsToLoad.add(mission);
            }
            intel.load(MissionsToLoad);
            subscribers.add(intel);
        }
        for (int i = 0; i < obj.services.M; i++)
            subscribers.add(new M((i + 1)));
        for (int i = 0; i < obj.services.Moneypenny; i++)
            subscribers.add(new Moneypenny(i + 1));
        subscribers.add(new Q());
    }
}

