
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eus.uni.dam2.java.spring.rest.real.model.Pokemon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lopez.pablo
 */
public class MongoDB {
    private static String connectionString = "mongodb://192.168.72.10:27017";
    
    private static ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();

    private static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static MongoClient mongoClient = (MongoClient) MongoClients.create(MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .codecRegistry(pojoCodecRegistry)
            .build());
    private static MongoDatabase database = mongoClient.getDatabase("pokedex");
    private static MongoCollection<Pokemon> collectionPokemon = database.getCollection("pokemon", Pokemon.class);

    public static void main(String[] args) {

        Consumer<Pokemon> printBlock = (final Pokemon p) -> {
            pokemon.add(p);
        };
        collectionPokemon.find().forEach(printBlock);

        for (Pokemon p : pokemon) {
            System.out.println(p.toString());
        }
    }
}
