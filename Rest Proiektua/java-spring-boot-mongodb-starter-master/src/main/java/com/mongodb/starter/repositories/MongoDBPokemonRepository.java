package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.ReturnDocument.AFTER;
import com.mongodb.starter.models.Pokemon;
import com.mongodb.starter.models.User;
import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.PostConstruct;
import org.bson.Document;

/**
 *
 * Class that implements PokemonRepository Interface to allow PokemonController
 * to perform CRUD operations in the database
 */
public class MongoDBPokemonRepository implements PokemonRepository {
    
    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Pokemon> pokemonCollection;
    private MongoCollection<Document> typeCollection;
    private MongoCollection<User> userCollection;
    
    @PostConstruct
    void init() {
        pokemonCollection = client.getDatabase("pokedex").getCollection("pokemon", Pokemon.class);
        typeCollection = client.getDatabase("pokedex").getCollection("types");
        userCollection = client.getDatabase("pokedex").getCollection("users", User.class);
    }

    /**
     * Saves a Pokemon in the database
     *
     * @param pokemon New Instance of Pokemon you want to insert
     * @return Instance of Pokemon
     */
    @Override
    public Pokemon save(Pokemon pokemon) {
        pokemon.setId(0);
        List<Pokemon> pokemonGuztiak = findAll();
        for (int i = 0; i < pokemonGuztiak.size(); i++) {
            Pokemon p = pokemonGuztiak.get(i);
            if (p.getId() != i + 1) {
                pokemon.setId(i + 1);
                break;
            }
        }
        if (pokemon.getId() == 0) {
            Pokemon azkenPokemon = pokemonCollection.find().sort(new Document("_id", -1)).first();
            int id = azkenPokemon.getId() + 1;
            pokemon.setId(id);
        }
        pokemonCollection.insertOne(pokemon);
        return pokemon;
    }

    /**
     * Finds every Pokemon in the database
     *
     * @return Every Pokemon instance from the database
     */
    @Override
    public List<Pokemon> findAll() {
        return pokemonCollection.find().sort(new Document("_id", 1)).into(new ArrayList<>());
    }

    /**
     * Finds one or more Pokemon in the database that have the introduced type
     *
     * @param type List of IDs of the Pokemon you want to find
     * @return List of Pokemon
     */
    @Override
    public List<Pokemon> findByType(String type) {
        return pokemonCollection.find(eq("type", type)).sort(new Document("_id", 1)).into(new ArrayList<>());
    }

    /**
     * Finds one Random Pokemon in the database
     *
     * @return Instance of Pokemon
     */
    @Override
    public Pokemon findRandomPokemon() {
        return pokemonCollection.aggregate(Arrays.asList(Aggregates.sample(1))).first();
    }

    /**
     * Returns the number of Pokemon in the collection
     *
     * @return Number of Documents in the collection
     */
    @Override
    public long count() {
        return pokemonCollection.countDocuments();
    }

    /**
     * Deletes one Pokemon in the database using a ID
     *
     * @param id ID of the Pokemon you want to delete
     * @return Number of documents affected (0 or 1)
     */
    @Override
    public long delete(int id) {
        return pokemonCollection.deleteOne(eq("_id", id)).getDeletedCount();
    }

    /**
     * Updates one Pokemon in the database
     *
     * @param pokemon Instance of Pokemon you want to update
     * @return Instance of Pokemon
     */
    @Override
    public Pokemon update(Pokemon pokemon) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return pokemonCollection.findOneAndReplace(eq("_id", pokemon.getId()), pokemon, options);
    }

    /**
     *
     * @return List of String with every different Type
     */
    @Override
    public List<String> findTypes() {
        List<String> types = new ArrayList<>();
        MongoCursor<Document> cursor = typeCollection.find().projection(excludeId()).iterator();
        try {
            while (cursor.hasNext()) {
                types.add(cursor.next().get("name", String.class));
            }
        } finally {
            cursor.close();
        }
        types.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        return types;
    }
    
    /**
     *
     * @param username String that contains the username
     * @param password String that contains the password
     * @return boolean (true if the inserted data matches with the data on the database, else false)
     */
    @Override
    public boolean checkUser(String username, String password) {
        try {
            User checkedUser = userCollection.find(and(eq("username", username), eq("password", password))).first();
            return checkedUser != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     *
     * @param newUser New Instance of user we want to insert
     * @return New instance of User
     */
    @Override
    public User insertUser(User newUser) {
        User azkenUser = userCollection.find().sort(new Document("_id", -1)).first();
        int id = azkenUser.getId() + 1;
        newUser.setId(id);
        userCollection.insertOne(newUser);
        return newUser;
    }
}
