package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Pokemon;
import com.mongodb.starter.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.bson.Document;

/**
 * Interface that allows PokemonController access the MongoDB database
 *
 */
@Repository
public interface PokemonRepository {

    /**
     *
     * @param pokemon New Instance of Pokemon you want to insert
     * @return Instance of Pokemon
     */
    Pokemon save(Pokemon pokemon);

    /**
     *
     * @return Every Pokemon instance from the database
     */
    List<Pokemon> findAll();

    /**
     *
     * @param type All the Pokemon that have the type you want to find
     * @return List of Pokemon
     */
    List<Pokemon> findByType(String type);

    /**
     *
     * @return Instance of a Random already existing Pokemon 
     */
    Pokemon findRandomPokemon();

    /**
     *
     * @return Number of Documents in the collection
     */
    long count();

    /**
     *
     * @param id ID of the Pokemon you want to delete
     * @return Number of documents affected (0 or 1)
     */
    long delete(int id);

    /**
     *
     * @param pokemon Instance of Pokemon you want to update
     * @return Instance of the updated Pokemon
     */
    Pokemon update(Pokemon pokemon);

    /**
     *
     * @return List of String with every different Type
     */
    List<String> findTypes();

    /**
     *
     * @param username String that contains the username
     * @param password String that contains the password
     * @return boolean (true if the inserted data matches with the data on the database, else false)
     */
    boolean checkUser(String username, String password);

    /**
     *
     * @param newUser New Instance of user we want to insert
     * @return New instance of User
     */
    User insertUser(User newUser);

}
