package bgu.spl.mics.application;

public class JsonObject {

    String[] inventory;
    JsonServices services;
    JsonSquad[] squad;

    class JsonServices {
        int M;
        int Moneypenny;
        JsonIntelligence[] intelligence;

        class JsonIntelligence {
            JsonMissions[] missions;
            public JsonMissions[] getMissions(){return missions;}
            class JsonMissions {
                String[] serialAgentsNumbers;
                int duration;
                String gadget;
                String missionName;
                int timeExpired;
                int timeIssued;
            }
        }
        int time;
    }

    class JsonSquad {
        String name;
        String serialNumber;
    }
}
