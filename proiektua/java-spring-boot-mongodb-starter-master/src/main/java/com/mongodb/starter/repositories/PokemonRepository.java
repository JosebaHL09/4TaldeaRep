package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository {

    Pokemon save(Pokemon pokemon);

    List<Pokemon> saveAll(List<Pokemon> pokemon);

    List<Pokemon> findAll();

    List<Pokemon> findAll(List<Integer> ids);

    Pokemon findOne(int id);

    long count();

    long delete(int id);

    long delete(List<Integer> ids);

    long deleteAll();

    Pokemon update(Pokemon pokemon);

    long update(List<Pokemon> pokemon);

}
