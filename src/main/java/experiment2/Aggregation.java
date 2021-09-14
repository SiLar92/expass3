package experiment2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import experiment1.CRUD;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.group;

public class Aggregation {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("expass3-Aggregation");

        MongoCollection<Document> collection = db.getCollection("Aggregation");

        CRUD.printAll(collection);

        var mapFunction1 = function(){
            emit(this.cust_id, this.price);
        };
        var reduceFunction1 = function(keyCustId, valuesPrices) {
            return Array.sum(valuesPrices);
        };

        collection.mapReduce(
                mapFunction1,
                reduceFunction1,
                { out: "total_qty" }
        );


        System.out.println(

        );


    }
}
