package de.gamekeller.Modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;

import de.gamekeller.Configs.XPConfig;
import de.gamekeller.DataTypes.Client;

public class XpRankModule {

    private XPConfig xpConfig;
    private Map<Integer, Client> clientList;
    private TS3Api api;
    private Map<String, Integer> serverGroupIDCache = new HashMap<>();


    public XpRankModule(XPConfig xpConfig, Map<Integer, Client> clientList, TS3Api api){
        this.xpConfig = xpConfig;
        this.clientList = clientList;
        this.api = api;
    }

    //rewards xp and levels the player if necessary
    public void rewardXP(){
        for (Map.Entry<Integer, Client> entry : clientList.entrySet()) {
            //if the client has the lowest rank they are to be passed over in the modules and should 
            //register via some other method
            
            //if(client.rank.title.equals(xpConfig.nextRank.get(0).title)){
            //    continue;
            //}
            Client client = entry.getValue();
            //rewards xp and lowers boost time 
            if (client.xp.boostMinutesRemaining > 0) {
                client.xp.total += xpConfig.eachTickBoosted;
                client.xp.progress += xpConfig.eachTickBoosted;
                client.xp.boostMinutesRemaining--;
            }else{
                client.xp.total += xpConfig.eachTick;
                client.xp.progress += xpConfig.eachTick;
            }

            //checks if level up is needed
            if(client.xp.progress >= xpConfig.levelXpKey.get(client.xp.currentKey)){
                client.level += 1;
                client.xp.progress = client.xp.progress - xpConfig.levelXpKey.get(client.xp.currentKey);
                if (client.xp.currentKey < xpConfig.levelXpKey.size()) {
                    client.xp.currentKey++;
                }
            }

            //if client level is higher or equal to level needed for rank up rank him up
            if(client.level >= xpConfig.nextRank.get(xpConfig.nextRank.indexOf(client.rank)).requiredLevel){
                //List<ServerGroup> serverGroups = api.getServerGroups();
                //api.addClientToServerGroup(6, client.teamspeakConnection.clientId);
                //revoke old servergroup 
                api.removeClientFromServerGroup(getServerGroupID(client.rank.title), client.teamspeakConnection.clientId);

                //add new servergroup
                client.rank =  xpConfig.nextRank.get(xpConfig.nextRank.indexOf(client.rank) + 1);
                api.addClientToServerGroup(getServerGroupID(client.rank.title), client.teamspeakConnection.clientId);
            }
        }
    }

    private int getServerGroupID(String groupTitle) {
        //looks in cache for servergroup
        if (serverGroupIDCache.get(groupTitle) != null) {
            return serverGroupIDCache.get(groupTitle);
        }

        //gets all Servergroups
        List<ServerGroup> groups = api.getServerGroups();

        //searches for servergroup with a matching name
        for (ServerGroup group : groups) {
            if(group.getName().equals(groupTitle)){
                //if fount then puts into cache for easy acces next time
                serverGroupIDCache.put(groupTitle, group.getId());
                return group.getId();
            }
        }
            
        return -1;
    }
}
