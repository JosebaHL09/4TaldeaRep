package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;
import com.mongodb.starter.models.Pokemon;
import javax.annotation.PostConstruct;
import org.bson.Document;

/**
 *
 * Class that implements PokemonRepository Interface to allow PokemonController to perform CRUD operations in the database
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

    @PostConstruct
    void init() {
        pokemonCollection = client.getDatabase("pokedex").getCollection("pokemon", Pokemon.class);
    }

    /**
     *
     * @param pokemon New Instance of Pokemon created in 
     * @return 
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
     *
     * @return
     */
    @Override
    public List<Pokemon> findAll() {
        return pokemonCollection.find().sort(new Document("_id", 1)).into(new ArrayList<>());
    }

    /**
     *
     * @param ids
     * @return
     */
    @Override
    public List<Pokemon> findAll(List<Integer> ids) {
        return pokemonCollection.find(in("_id", ids)).sort(new Document("_id", 1)).into(new ArrayList<>());
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Pokemon findOne(int id) {
        return pokemonCollection.find(eq("_id", id)).first();
    }

    /**
     *
     * @return
     */
    @Override
    public long count() {
        return pokemonCollection.countDocuments();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public long delete(int id) {
        return pokemonCollection.deleteOne(eq("_id", id)).getDeletedCount();
    }

    /**
     *
     * @param pokemon
     * @return
     */
    @Override
    public Pokemon update(Pokemon pokemon) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return pokemonCollection.findOneAndReplace(eq("_id", pokemon.getId()), pokemon, options);
    }
}
