package de.gamekeller.Parser;


import java.io.File;

import org.bson.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import de.gamekeller.Configs.XPConfig;
import de.gamekeller.DataTypes.Rank;


public class XpConfigParser {
    
    public static XPConfig parse(File file){
        XPConfig config = new XPConfig();
        
        String uri = "mongodb://localhost:27017";
        String databaseName = "Ts";
        String collectionName = "XPConfig";

        DatabaseParser database = new DatabaseParser(uri, databaseName, collectionName);
        MongoCollection<Document> collection = database.dInterface.database.getCollection(collectionName);
        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                doc.get("levelXpKey", Document.class);
                System.out.println(doc.toJson());
            }
        } finally {
            cursor.close();
        }


        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(file);
            
            config.nextXpKey = root.path("xp").path("nextXpKey").asInt();
            System.out.println("nextXpKey = " + config.nextXpKey);

            config.tickLength = root.path("xp").path("tickLength").asInt();
            System.out.println("tickLength = " + config.tickLength);

            config.boostDuration = root.path("xp").path("boostDuration").asInt();
            System.out.println("boostDuration = " + config.boostDuration);

            config.eachTick = root.path("xp").path("eachTick").asInt();
            System.out.println("eachTick = " + config.eachTick);

            config.eachTickBoosted = root.path("xp").path("eachTickBoosted").asInt();
            System.out.println("eachTickBoosted = " + config.eachTickBoosted);
            
            JsonNode levelXpKey = root.path("xp").path("levelXpKey");
            for (JsonNode node : levelXpKey) {
                int value = node.asInt();
                config.levelXpKey.add(value);
                System.out.println("levelXpKey value = " + value);
            }

            JsonNode nextRankArray = root.get("xp").get("nextRank");

            // Loop over the array and extract the rank information
            int i = 0;
            for (JsonNode rank : nextRankArray) {
                String title = rank.fieldNames().next();
                int requiredLevel = rank.get(title).get("levelNeeded").asInt();
                config.nextRank.add(Rank.getInstance(title, requiredLevel, i++));
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return config;

    }

}
