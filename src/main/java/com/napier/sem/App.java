package com.napier.sem;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {

        String uri = System.getenv("MONGO_URI");
        if (uri == null || uri.isEmpty()) {
            uri = "mongodb://localhost:27017";
        }
        System.out.println("Connecting to MongoDB at: " + uri);

        // Configure 3-second server selection timeout
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(3000, TimeUnit.MILLISECONDS))
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {

            MongoDatabase database = mongoClient.getDatabase("mydb");
            MongoCollection<Document> collection = database.getCollection("test");

            Document doc = new Document("name", "Kevin Sim")
                    .append("class", "DevOps")
                    .append("year", "2024")
                    .append("result", new Document("CW", 95).append("EX", 85));

            collection.insertOne(doc);
            System.out.println("Successfully inserted document!");

            Document myDoc = collection.find().first();
            if (myDoc != null) {
                System.out.println("\n--- Retrieved Document ---");
                System.out.println(myDoc.toJson());
            }

        } catch (Exception e) {
            System.err.println("\n[ERROR] Connection failed: " + e.getMessage());
        }
    }
}