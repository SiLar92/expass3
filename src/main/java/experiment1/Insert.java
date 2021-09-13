package experiment1;

import com.mongodb.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Collections.singletonList;


public class Insert {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongoClient.getDatabase("expass3-insert");

        MongoCollection<Document> collection = db.getCollection("insert");


        for (int i = 0; i < 10; i++) {
            insert(collection, i);
        }

        FindIterable<Document> findIterable = collection.find(eq("item", "canvas"));


        for (int i = 0; i < 12; i++) {
            if(findIterable.iterator().hasNext())
                System.out.println(findIterable.iterator().next());
        }

    }
    public static void insert(MongoCollection<Document> collection, int qty) {
        Document canvas = new Document("item", "canvas").append("qty", qty).append("tags", singletonList("cotton"));
        Document size = new Document("h", 28).append("w", 35.5).append("uom", "cm");
        canvas.put("size", size);
        collection.insertOne(canvas);
    }

}
