package experiment1;

import com.mongodb.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import static java.util.Collections.singletonList;


public class CRUD {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("expass3-insert");

        MongoCollection<Document> collection = db.getCollection("CRUD");


        // Insert
        insert(collection, 0);

        System.out.println("\n-------- After Single insert --------");

        printAll(collection);



        System.out.println("\n-------- After Bulk insert --------");
        bulk(collection);


        printAll(collection);
        System.out.println("\n-------- Before Single update --------");

        //update
        update(collection, 9, 14, 9, 10.0);
        printAll(collection);


        //remove documents (all in this case)
        clear(collection);
        System.out.println("\n-------- Collection cleared --------");
        printAll(collection);


    }

    public static void insert(MongoCollection<Document> collection, int qty) {
        Document canvas = new Document("item", "canvas").append("qty", qty).append("tags", singletonList("cotton"));
        Document size = new Document("h", 28.0).append("w", 35.5).append("uom", "cm");
        canvas.put("size", size);
        collection.insertOne(canvas);
    }

    public static void printAll(MongoCollection<Document> collection) {
        // Print all from query: SELECT * FROM collection

        FindIterable<Document> findIterable = collection.find(new Document()); // get all documents from collection

        try (MongoCursor<Document> cursor = findIterable.iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }
    }

    public static void update(MongoCollection<Document> collection, int oldqty, int newqty, double newH, double newW) {
        collection.updateOne(eq("qty", oldqty), combine(set("qty", newqty), set("size.h", newH), set("size.w", newW)));
    }

    public static void clear(MongoCollection<Document> collection) {
        collection.deleteMany(new Document());
    }

    public static void bulk(MongoCollection<Document> collection) {
        Document size = new Document("h", 28.0).append("w", 35.5).append("uom", "cm");

        collection.bulkWrite(Arrays.asList(
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 1).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 2).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 3).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 4).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 5).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 6).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 7).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 8).append("tags", singletonList("cotton")).append("size", size)),
                new InsertOneModel<>(new Document("item", "canvas").append("qty", 9).append("tags", singletonList("cotton")).append("size", size))
        ));
    }
}
