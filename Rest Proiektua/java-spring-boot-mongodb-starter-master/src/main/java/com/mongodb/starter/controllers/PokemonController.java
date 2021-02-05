package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Pokemon;
import com.mongodb.starter.models.User;
import com.mongodb.starter.repositories.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that allows MVC connect with Java Service
 *
 */
@RestController
@RequestMapping("/api")
public class PokemonController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PokemonController.class);
    private final PokemonRepository pokemonRepository;

    /**
     * Setter for the PokemonRepository of this instance
     *
     * @param pokemonRepository
     */
    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /**
     *
     * @param pokemon Instance of Pokemon you want to insert
     * @return Instance of the inserted Pokemon
     */
    @PostMapping("pokemon")
    @ResponseStatus(HttpStatus.CREATED)
    public Pokemon postPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    /**
     *
     * @return List of every Pokemon
     */
    @GetMapping("pokemon")
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    /**
     *
     * @return JSON representation of the Random Pokemon found
     */
    @GetMapping("pokemon/random")
    public ResponseEntity<Pokemon> getRandomPokemon() {
        Pokemon pokemon = pokemonRepository.findRandomPokemon();
        return ResponseEntity.ok(pokemon);
    }

    /**
     *
     * @param type Type of a Pokemon
     * @return List of Pokemon that have the introduced type
     */
    @GetMapping("pokemon/type/{type}")
    public List<Pokemon> getPokemonByType(@PathVariable String type) {
        try {
            return pokemonRepository.findByType(type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @return Number of Documents in the collection
     */
    @GetMapping("pokemon/count")
    public Long getPokemonCount() {
        return pokemonRepository.count();
    }

    /**
     *
     * @param id Id of the Pokemon you want to delete
     * @return Number of Documents affected by the query
     */
    @DeleteMapping("pokemon/{id}")
    public Long deletePokemon(@PathVariable int id) {
        return pokemonRepository.delete(id);
    }

    /**
     *
     * @param pokemon Instance of Pokemon you want to update
     * @return Instance of the updated Pokemon
     */
    @PutMapping("pokemon")
    public Pokemon updatePokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.update(pokemon);
    }

    /**
     *
     * @return List of String with every different Type
     */
    @GetMapping("type")
    public List<String> getTypeList() {
        return pokemonRepository.findTypes();
    }

    /**
     *
     * @param username String that contains the username we want to check
     * @param password String that contains the password we want to check
     * @return boolean (true if the inserted data matches with the data on the database, else false)
     */
    @GetMapping("user/")
    @ResponseBody
    public boolean checkUser(@RequestParam String username, @RequestParam String password) {
        return pokemonRepository.checkUser(username, password);
    }

    /**
     *
     * @param newUser New Instance of user we want to insert
     * @return New instance of User
     */
    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User newUser) {
        return pokemonRepository.insertUser(newUser);
    }

    /**
     * Handles All Exceptions
     *
     * @param e Runtime Exception
     * @return Runtime Exception
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

}
