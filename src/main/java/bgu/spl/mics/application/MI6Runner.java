package bgu.spl.mics.application;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import com.google.gson.Gson;

import java.io.DataInput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */

public class MI6Runner {
    public static AtomicInteger startCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        // json parse
        try {

            String str = new String(Files.readAllBytes(Paths.get(args[0])));
            JsonObject obj = new Gson().fromJson(str, JsonObject.class);
            // read the json to the fields
            LinkedList<Subscriber> subscribers = new LinkedList<>();
            insertToInventory(obj);
            insertToSquad(obj);
            insertSubscribers(obj, subscribers);
            int duration = obj.services.time;
            TimeService timeService = TimeService.getInstance();
            timeService.setDuration(duration);
            Inventory inv = Inventory.getInstance();
            System.out.println(inv.toString());
            // run
            executeThreads(subscribers, timeService);

        } catch (IOException e) {
            throw new RuntimeException("illegal json file");
        }
    }

    private static void executeThreads(List<Subscriber> subscribers, TimeService timeService) {
        //ExecutorService executor = Executors.newFixedThreadPool(subscribers.size()+1);
        List<Thread> threads = new LinkedList<>();
        //initialize all subscribers before the timeService
        for (Subscriber currSubscriber : subscribers) {
            Thread t = new Thread(currSubscriber, currSubscriber.getName());
            threads.add(t);
            t.start();
        }

        while (!startCounter.compareAndSet(threads.size(), 0)) {}
        Thread timeThread = new Thread(TimeService.getInstance(), TimeService.getInstance().getName());
        timeThread.start();

        for (int i = 0; i < threads.size(); i++) {
            Thread t = threads.get(i);
            try {
                //System.out.println("terminating: " + t.getName());
                t.join();
//                if (i < threads.size() - 1)
//                    System.out.println("terminated: " + t.getName() + " NEXT: " + threads.get(i + 1));
            } catch (InterruptedException e) {
            }
        }
        System.out.println("THE Diary: ");
        System.out.println(Diary.getInstance().toString());
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

    private static void insertSubscribers(JsonObject obj, List<Subscriber> subscribers) {

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

