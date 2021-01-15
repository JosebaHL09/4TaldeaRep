package eus.uni.dam2.java.spring.rest.real.repositories;

import eus.uni.dam2.java.spring.rest.real.model.Pokemon;
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

    Pokemon update(Pokemon person);

    long update(List<Pokemon> persons);

    //double getAverageAge();

}
