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
 *
 *
 */
@RestController
@RequestMapping("/api")
public class PokemonController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PokemonController.class);
    private final PokemonRepository pokemonRepository;

    /**
     * Setter for the PokemonRepository of this instance
     * @param pokemonRepository
     */
    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /**
     *
     * @param pokemon
     * @return
     */
    @PostMapping("pokemon")
    @ResponseStatus(HttpStatus.CREATED)
    public Pokemon postPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    /**
     *
     * @return
     */
    @GetMapping("pokemon")
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
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
     * @param ids
     * @return
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
     * @return
     */
    @GetMapping("pokemon/count")
    public Long getCount() {
        return pokemonRepository.count();
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("pokemon/{id}")
    public Long deletePokemon(@PathVariable int id) {
        return pokemonRepository.delete(id);
    }

    /**
     *
     * @param pokemon Instance of Pokemon you want to update, 
     * @return Instance of the updated Pokemon
     */
    @PutMapping("pokemon")
    public Pokemon putPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.update(pokemon);
    }

    /**
     * Handles All Exceptions
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
