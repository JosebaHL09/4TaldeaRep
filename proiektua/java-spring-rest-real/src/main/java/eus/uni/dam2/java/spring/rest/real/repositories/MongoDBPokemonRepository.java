package eus.uni.dam2.java.spring.rest.real.repositories;

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
import eus.uni.dam2.java.spring.rest.real.model.Pokemon;

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

    @Override
    public Pokemon save(Pokemon pokemon) {
        pokemonCollection.insertOne(pokemon);
        return pokemon;
    }

    @Override
    public List<Pokemon> saveAll(List<Pokemon> pokemon) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                pokemonCollection.insertMany(clientSession, pokemon);
                return pokemon;
            }, txnOptions);
        }
    }

    @Override
    public List<Pokemon> findAll() {
        return pokemonCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Pokemon> findAll(List<Integer> ids) {
        return pokemonCollection.find(in("_id", ids)).into(new ArrayList<>());
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
    public long delete(List<Integer> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> pokemonCollection.deleteMany(clientSession, in("_id", ids)).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> pokemonCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return pokemonCollection.findOneAndReplace(eq("_id", pokemon.getId()), pokemon, options);
    }

    @Override
    public long update(List<Pokemon> pokemon) {
        List<WriteModel<Pokemon>> writes = pokemon.stream()
                                                 .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                                                 .collect(Collectors.toList());
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> pokemonCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }
}
