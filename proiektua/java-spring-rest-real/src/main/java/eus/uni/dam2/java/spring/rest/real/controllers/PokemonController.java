/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.java.spring.rest.real.controllers;
import eus.uni.dam2.java.spring.rest.real.model.Pokemon;
import eus.uni.dam2.java.spring.rest.real.repositories.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")


public class PokemonController {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(PokemonController.class);
    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @PostMapping("pokemon")
    @ResponseStatus(HttpStatus.CREATED)
    public Pokemon postPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @PostMapping("pokemones")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Pokemon> postPokemones(@RequestBody List<Pokemon> pokemon) {
        return pokemonRepository.saveAll(pokemon);
    }

    @GetMapping("pokemon")
    public List<Pokemon> getPokemon() {
        return pokemonRepository.findAll();
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<Pokemon> getPokemones(@PathVariable int id) {
        Pokemon pokemon = pokemonRepository.findOne(id);
        if (pokemon == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(pokemon);
    }

    @GetMapping("pokemones/{ids}")
    public List<Pokemon> getPokemones(@PathVariable int ids) {
        List<String> listIds = asList(ids.split(","));
        return pokemonRepository.findAll(listIds);
    }

    @GetMapping("pokemones/count")
    public Long getCount() {
        return pokemonRepository.count();
    }

    @DeleteMapping("pokemon/{id}")
    public Long deletePokemon(@PathVariable int id) {
        return pokemonRepository.delete(id);
    }

    @DeleteMapping("pokemones/{ids}")
    public Long deletePokemones(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
       
        return pokemonRepository.delete(listIds);
    }

    @DeleteMapping("pokemones")
    public Long deletePokemones() {
        return pokemonRepository.deleteAll();
    }

    @PutMapping("pokemon")
    public Pokemon putPokemon(@RequestBody Pokemon pokemon) {
        return pokemonRepository.update(pokemon);
    }

    @PutMapping("pokemones")
    public Long putPerson(@RequestBody List<Pokemon> pokemon) {
        return pokemonRepository.update(pokemon);
    }
    /*
    @GetMapping("pokemones/averageAge")
    public Double averageAge() {
        return pokemonRepository.getAverageAge();
    }
    */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
    
}
