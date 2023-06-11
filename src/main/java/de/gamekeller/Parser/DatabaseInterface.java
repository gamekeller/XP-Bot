package de.gamekeller.Parser;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.conversions.Bson;

public class DatabaseInterface {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public DatabaseInterface(String uri, String database, String collection){
        this.mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase(database);
        this.collection = this.database.getCollection(collection);
    }

    public FindIterable<Document> read(Bson filter){
        return this.collection.find(filter);
    }

    public void updateFiltered(Bson filter, Bson update){
        this.collection.updateMany(filter, update);
    }

    public void insertDocument(Document doc){
        this.collection.insertOne(doc);
    }
}
