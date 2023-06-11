package de.gamekeller.DataTypes;

public class Client {
    public TeamspeakConnection teamspeakConnection;
    public boolean teamspeakOnline;
    public Rank rank;
    public Xp xp;
    public int level;
    public boolean consent;
    public TeamspeakTracking teamspeakTracking;

    // constructors, getters and setters

    public class TeamspeakConnection {
        public String uuid;
        public int clientId;
        public String nickname;
        public long connectedAt; 
        //public CurrentChannel currentChannel;

        // constructors, getters and setters
    }

    public class CurrentChannel {
        public String name;
        public int id;

        // constructors, getters and setters
    }

    public class Xp {
        public int current;
        public int total;
        public int boostMinutesRemaining;
        public int currentKey;
        public int progress;

        // constructors, getters and setters
    }

    public class TeamspeakTracking {
        public long activeTime;
        public long onlineTime;
        public long lastSeen;

        // constructors, getters and setters
    }
}
