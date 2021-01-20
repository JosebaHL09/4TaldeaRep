package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author lopez.pablo
 */
@Repository
public interface PokemonRepository {

    /**
     *
     * @param pokemon  New Instance of Pokemon created in the ASP side
     * @return
     */
    Pokemon save(Pokemon pokemon);

    /**
     *
     * @return
     */
    List<Pokemon> findAll();

    /**
     *
     * @param ids
     * @return
     */
    List<Pokemon> findAll(List<Integer> ids);

    /**
     *
     * @param id
     * @return
     */
    Pokemon findOne(int id);

    /**
     *
     * @return
     */
    long count();

    /**
     *
     * @param id
     * @return
     */
    long delete(int id);

    /**
     *
     * @param pokemon
     * @return
     */
    Pokemon update(Pokemon pokemon);

}
