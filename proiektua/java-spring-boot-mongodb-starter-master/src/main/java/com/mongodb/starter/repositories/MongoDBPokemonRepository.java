package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;
import com.mongodb.starter.models.Pokemon;
import javax.annotation.PostConstruct;
import org.bson.Document;

/**
 *
 * @author lopez.pablo
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

    @Override
    public List<Pokemon> findAll() {
        return pokemonCollection.find().sort(new Document("_id", 1)).into(new ArrayList<>());
    }

    @Override
    public List<Pokemon> findAll(List<Integer> ids) {
        return pokemonCollection.find(in("_id", ids)).sort(new Document("_id", 1)).into(new ArrayList<>());
    }

    @Override
    public Pokemon findOne(int id) {
        return pokemonCollection.find(eq("_id", id)).first();
    }

    @Override
    public long count() {
        return pokemonCollection.countDocuments();
    }

    @Override
    public long delete(int id) {
        return pokemonCollection.deleteOne(eq("_id", id)).getDeletedCount();
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return pokemonCollection.findOneAndReplace(eq("_id", pokemon.getId()), pokemon, options);
    }
}
