package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Pokemon;
import com.mongodb.starter.repositories.PokemonRepository;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

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
     * @param id Id of the Pokemon as an Integer
     * @return JSON representation of the Pokemon with the inserted ID
     */
    @GetMapping("pokemon/{id}")
    public ResponseEntity<Pokemon> getPokemon(@PathVariable int id) {
        Pokemon pokemon = pokemonRepository.findOne(id);
        if (pokemon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(pokemon);
    }

    /**
     *
     * @param ids String that includes one or more ID separated by comma ","
     * @return List of Pokemon found
     */
    @GetMapping("multiplePokemon/{ids}")
    public List<Pokemon> getMultiplePokemon(@PathVariable String ids) {
        List<String> listIdsString = asList(ids.split(","));
        List<Integer> listIds = new ArrayList<>();
        for (String s : listIdsString) {
            listIds.add(Integer.valueOf(s));
        }
        return pokemonRepository.findAll(listIds);
    }

    /**
     *
     * @param type
     * @return
     */
    @GetMapping("multiplePokemon/type/{type}")
    public List<Pokemon> getMultiplePokemonByType(@PathVariable String type) {
        return pokemonRepository.findByType(type);
    }

    /**
     *
     * @return
     */
    @GetMapping("pokemon/count")
    public Long getCount() {
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
    public Pokemon putPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.update(pokemon);
    }

    @PutMapping("type")
    public List<String> findTypes() {
        return pokemonRepository.findTypes();
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
