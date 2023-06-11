package de.gamekeller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;

import de.gamekeller.Configs.ServerConfig;
import de.gamekeller.Configs.XPConfig;
import de.gamekeller.DataTypes.Client;
import de.gamekeller.DataTypes.MyListener;
import de.gamekeller.Modules.XpRankModule;
import de.gamekeller.Parser.DatabaseParser;
import de.gamekeller.Parser.ServerConfigParser;
import de.gamekeller.Parser.XpConfigParser;

public class Loop {
    public static void main(String[] args) {
        //      -Read Configs                                                                       -- DONE
        //      -Get users that are online from Database                                            -- DONE
        //      -Remove users that go offline from list                                             -- DONE
        //      -Update the online user list                                                        -- DONE
        //      -Reward xp to online users and add new rank if necessary                            -- DONE
        //      -Save users when they leave or every given time intervall ?                         -- 
        //      -implement xp boost for the first 2 hours on server                                 -- Needs to reset somehow
        //      -implement ranks depending on level (reset level for each rank)                     -- DONE
        //      -automatically give rank to user on server upon hitting required level              -- DONE    

        //read Configs
        XPConfig xpConfig = XpConfigParser.parse(new File("XPConfig.json"));
        ServerConfig serverConfig = ServerConfigParser.parse();

        //set up TS3 Config and connect to the server API
        TS3Config config = new TS3Config();
        config.setHost(serverConfig.host);
        config.setQueryPort(serverConfig.port);
        TS3Query query = new TS3Query(config);
        query.connect();
        TS3Api api = query.getApi();
        api.login(serverConfig.adminUserName, serverConfig.adminPassword);
        api.selectVirtualServerById(1);
        api.registerEvent(TS3EventType.SERVER);

        //create Database Interface instance
        String uri = "mongodb://localhost:27017";
        String database = "Ts";
        String collection = "TestServer";
        DatabaseParser dParser = new DatabaseParser(uri, database, collection);

        //register Event Listeners
        Map<Integer, Client> clientList =  new HashMap<>();
        MyListener listener = new MyListener(api, xpConfig, clientList, dParser);
        api.addTS3Listeners(listener);

        //init Modules
        XpRankModule xpRankModule = new XpRankModule(xpConfig, clientList, api);

        //Main loop
        while (true) {
            xpRankModule.rewardXP();
            try {
                Thread.sleep(xpConfig.tickLength * 1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }




    }
}
