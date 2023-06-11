package de.gamekeller.Parser;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import de.gamekeller.DataTypes.Client;
import de.gamekeller.DataTypes.Rank;
import static com.mongodb.client.model.Filters.eq;

public class DatabaseParser {
    DatabaseInterface dInterface;
    
    public DatabaseParser(String uri, String database, String collection){
        this.dInterface = new DatabaseInterface(uri, database, collection);
    }

    public void writeClientList(List<Client> clients){
        for (Client client : clients) {
            writeClient(client);
        }
    }

    public void writeClient(Client client){
        if (client != null) {
            Document existingDoc = this.dInterface.collection.find(eq("teamspeakConnection.uuid", client.teamspeakConnection.uuid)).first();
            if (existingDoc != null) {
                Document newDoc = buildDocument(client);
                this.dInterface.collection.replaceOne(existingDoc, newDoc);
            }
        }
    }

    public Document readFirst(Bson query){
        return this.dInterface.collection.find(query).first();
    }


    public Client readClientFromUUID(String uuid){
        //this assumes that each client is stored as a seperate Document in the mongodb 
        Document doc = this.dInterface.collection.find(eq("teamspeakConnection.uuid", uuid)).first();
        if(doc == null){
            return null;
        }
        Client client =  new Client();

        //main stuff
        client.teamspeakOnline = doc.getBoolean("teamspeakOnline");
        client.rank = Rank.getInstance(doc.getInteger("rank"));
        client.level = doc.getInteger("level");
        client.consent = doc.getBoolean("consent");
        
        //parse teamspeakConnection
        Client.TeamspeakConnection tsConnection = client.new TeamspeakConnection();
        Document connection = doc.get("teamspeakConnection", Document.class);
        tsConnection.uuid = uuid;
        tsConnection.nickname = connection.getString("nickname");
        tsConnection.connectedAt = connection.getLong("connectedAt");
        client.teamspeakConnection = tsConnection;
                        
        //xp
        connection = doc.get("xp", Document.class);
        Client.Xp xp = client.new Xp();
        xp.current = connection.getInteger("current");
        xp.total = connection.getInteger("total");
        xp.boostMinutesRemaining = connection.getInteger("boostMinutesRemaining");
        xp.currentKey = connection.getInteger("currentKey");
        xp.progress = connection.getInteger("progress");
        client.xp = xp;
        
        //tracking
        connection = doc.get("teamspeakTracking", Document.class);
        Client.TeamspeakTracking tracking = client.new TeamspeakTracking();
        tracking.activeTime = connection.getLong("activeTime");
        tracking.onlineTime = connection.getLong("onlineTime");
        tracking.lastSeen = connection.getLong("lastSeen");
        client.teamspeakTracking = tracking;

        return client;
    }


    public static Document buildDocument(Client client) {
        Document doc = new Document();
        doc.append("teamspeakConnection", buildTeamspeakConnectionDocument(client.teamspeakConnection));
        doc.append("teamspeakOnline", client.teamspeakOnline);
        doc.append("rank", client.rank.relativeRank);
        doc.append("xp", buildXpDocument(client.xp));
        doc.append("level", client.level);
        doc.append("consent", client.consent);
        doc.append("teamspeakTracking", buildTeamspeakTrackingDocument(client.teamspeakTracking));
        return doc;
    }

    private static Document buildTeamspeakConnectionDocument(Client.TeamspeakConnection teamspeakConnection) {
        Document doc = new Document();
        doc.append("uuid", teamspeakConnection.uuid);
        doc.append("clientId", teamspeakConnection.clientId);
        doc.append("nickname", teamspeakConnection.nickname);
        doc.append("connectedAt", teamspeakConnection.connectedAt);
        //doc.append("currentChannel", buildCurrentChannelDocument(teamspeakConnection.currentChannel));
        return doc;
    }

    private static Document buildCurrentChannelDocument(Client.CurrentChannel currentChannel) {
        Document doc = new Document();
        doc.append("name", currentChannel.name);
        doc.append("id", currentChannel.id);
        return doc;
    }

    private static Document buildXpDocument(Client.Xp xp) {
        Document doc = new Document();
        doc.append("current", xp.current);
        doc.append("total", xp.total);
        doc.append("boostMinutesRemaining", xp.boostMinutesRemaining);
        doc.append("currentKey", xp.currentKey);
        doc.append("progress", xp.progress);
        return doc;
    }

    private static Document buildTeamspeakTrackingDocument(Client.TeamspeakTracking teamspeakTracking) {
        Document doc = new Document();
        doc.append("activeTime", teamspeakTracking.activeTime);
        doc.append("onlineTime", teamspeakTracking.onlineTime);
        doc.append("lastSeen", teamspeakTracking.lastSeen);
        return doc;
    }

}
