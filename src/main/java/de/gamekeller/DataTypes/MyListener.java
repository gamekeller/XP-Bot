package de.gamekeller.DataTypes;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.*;

import de.gamekeller.Configs.XPConfig;
import de.gamekeller.Parser.DatabaseParser;

public class MyListener implements TS3Listener {

    public Map<Integer, Client> clientList;

    public TS3Api api; 

    public XPConfig xpConfig;

    public DatabaseParser dParser;

    public MyListener(TS3Api api, XPConfig xpConfig, Map<Integer, Client> clients, DatabaseParser dInterface){
        this.api = api;
        this.xpConfig = xpConfig;
        this.clientList = clients;
        this.dParser = dInterface;
    }

    @Override
    public void onClientMoved(ClientMovedEvent e) {}
    @Override
    public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {}
    @Override
    public void onServerEdit(ServerEditedEvent e) {}
    @Override
    public void onTextMessage(TextMessageEvent e) {}
    @Override
    public void onChannelCreate(ChannelCreateEvent e) {}
    @Override
    public void onChannelDeleted(ChannelDeletedEvent e) {}
    @Override
    public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {}
    @Override
    public void onChannelEdit(ChannelEditedEvent e) {}
    @Override
    public void onChannelMoved(ChannelMovedEvent e) {}
    @Override
    public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {}

    @Override
    public void onClientJoin(ClientJoinEvent e) {
        Client client = dParser.readClientFromUUID(e.getUniqueClientIdentifier());
        if(client != null){
            client.teamspeakConnection.clientId = e.getClientDatabaseId();
            clientList.put(e.getClientId(), client);
        }else{
            //if the user has no entry in database
            client = new Client();
            client.teamspeakOnline = true;
            client.level = 0;
            client.consent = false;
            //the first rank a client gets should be something like guest, if a client is a guest they
            //should not be considered by the modules
            client.rank = Rank.getInstance(0);

            //add connectionData
            Client.TeamspeakConnection tsConnection = client.new TeamspeakConnection();
            //TODO maybe encode this information with sha256 or something. Necessary ?
            tsConnection.uuid = e.getUniqueClientIdentifier();
            tsConnection.clientId = e.getClientId();
            tsConnection.nickname = e.getClientNickname();
            tsConnection.connectedAt = System.currentTimeMillis();
            client.teamspeakConnection = tsConnection;

            Client.Xp xp = client.new Xp();
            xp.boostMinutesRemaining = xpConfig.boostDuration;
            xp.current = 0;
            xp.total = 0;
            xp.currentKey = 0;
            xp.progress = 0;
            client.xp = xp;

            Client.TeamspeakTracking tsTracking = client.new TeamspeakTracking();
            tsTracking.activeTime = 0;
            tsTracking.onlineTime = 0;
            tsTracking.lastSeen = System.currentTimeMillis();
            client.teamspeakTracking = tsTracking;

            clientList.put(e.getClientId(), client);
        }
    }

    @Override
    public void onClientLeave(ClientLeaveEvent e) {
        int x = e.getClientId();
        Client leavingCLient = clientList.get(x);
        if (leavingCLient == null) {
            return;
        }

        //update the last seen element in database
        leavingCLient.teamspeakTracking.lastSeen = System.currentTimeMillis();
        dParser.writeClient(clientList.get(x));
        clientList.remove(x);
    }
}
