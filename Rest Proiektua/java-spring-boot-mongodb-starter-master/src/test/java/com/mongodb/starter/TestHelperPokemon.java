package com.mongodb.starter;

import com.mongodb.starter.models.Pokemon;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
class TestHelperPokemon {

    Pokemon getBulbasaur() {
        Pokemon p = new Pokemon();
        p.setId(1);
        p.setName("Bulbasaur");
        p.setImg("IMG");
        ArrayList<String> types = new ArrayList<>();
        types.add("Planta");
        p.setType(types);
        p.setHeight("2.34");
        p.setWeight("3.45");
        p.setWeaknesses(types);
        p.setPrev_evolution(null);
        p.setNext_evolution(null);
        return p;
    }

    Pokemon getCharizard() {
        Pokemon p = new Pokemon();
        p.setId(1);
        p.setName("Charizard");
        p.setImg("IMG");
        ArrayList<String> types = new ArrayList<>();
        types.add("Fuego");
        p.setType(types);
        p.setHeight("2.34");
        p.setWeight("3.45");
        p.setWeaknesses(types);
        p.setPrev_evolution(null);
        p.setNext_evolution(null);
        return p;
    }

    List<Pokemon> getListPokemon() {
        return asList(getBulbasaur(), getBulbasaur());
    }
}
