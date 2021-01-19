package com.mongodb.starter;



import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.starter.models.Pokemon;
import com.mongodb.starter.repositories.PokemonRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DataJpaTest
class PokemonControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private TestHelperPokemon testHelper;
    private String URL;

    @Autowired
    PokemonControllerIT(MongoClient mongoClient) {
        createPokemonCollectionIfNotPresent(mongoClient);
    }

    @PostConstruct
    void setUp() {
        URL = "http://localhost:" + port + "/api";
    }

    @AfterEach
    void tearDown() {
        pokemonRepository.deleteAll();
    }

    @DisplayName("POST /pokemon with 1 pokemon")
    @Test
    void postPokemon() {
        // GIVEN
        // WHEN
        ResponseEntity<Pokemon> result = rest.postForEntity(URL + "/pokemon", testHelper.getBulbasaur(), Pokemon.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Pokemon pokemonResult = result.getBody();
        assertThat(pokemonResult.getId()).isNotNull();
        assertThat(pokemonResult).isEqualToIgnoringGivenFields(testHelper.getBulbasaur(), "id", "createdAt");
    }

    @DisplayName("POST /multiplePokemon with 2 pokemon")
    @Test
    void postMultiplePokemon() {
        // GIVEN
        // WHEN
        HttpEntity<List<Pokemon>> body = new HttpEntity<>(testHelper.getListPokemon());
        ResponseEntity<List<Pokemon>> response = rest.exchange(URL + "/multiplePokemon", HttpMethod.POST, body, new ParameterizedTypeReference<List<Pokemon>>() {
        });
        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).usingElementComparatorIgnoringFields("id", "createdAt")
                .containsExactlyInAnyOrderElementsOf(testHelper.getListPokemon());
    }

    @DisplayName("GET /pokemon with 2 pokemon")
    @Test
    void getAllPokemon() {
        // GIVEN
        List<Pokemon> pokemonInserted = pokemonRepository.saveAll(testHelper.getListPokemon());
        // WHEN
        ResponseEntity<List<Pokemon>> result = rest.exchange(URL + "/pokemon", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pokemon>>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).containsExactlyInAnyOrderElementsOf(pokemonInserted);
    }

    @DisplayName("GET /pokemon/{id}")
    @Test
    void getPokemonById() {
        // GIVEN
        Pokemon pokemonInserted = pokemonRepository.save(testHelper.getCharizard());
        int idInserted = pokemonInserted.getId();
        // WHEN
        ResponseEntity<Pokemon> result = rest.getForEntity(URL + "/pokemon/" + idInserted, Pokemon.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(pokemonInserted);
    }

    /*@DisplayName("GET /multiplePokemon/{ids}")
    @Test
    void getMultiplePokemonById() {
        // GIVEN
        List<Pokemon> pokemonInserted = pokemonRepository.saveAll(testHelper.getListPokemon());
        List<String> idsInserted = pokemonInserted.stream().map(Pokemon::getId).map(ObjectId::toString).collect(toList());
        // WHEN
        String url = URL + "/persons/" + String.join(",", idsInserted);
        ResponseEntity<List<Pokemon>> result = rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pokemon>>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).containsExactlyInAnyOrderElementsOf(pokemonInserted);
    }*/
    @DisplayName("GET /pokemon/count")
    @Test
    void getCount() {
        // GIVEN
        pokemonRepository.saveAll(testHelper.getListPokemon());
        // WHEN
        ResponseEntity<Long> result = rest.getForEntity(URL + "/pokemon/count", Long.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
    }

    @DisplayName("DELETE /pokemon/{id}")
    @Test
    void deletePokemonById() {
        // GIVEN
        Pokemon pokemonInserted = pokemonRepository.save(testHelper.getBulbasaur());
        int idInserted = pokemonInserted.getId();
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/pokemon/" + String.valueOf(idInserted), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Long>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(1L);
        assertThat(pokemonRepository.count()).isEqualTo(0L);
    }

    /*@DisplayName("DELETE /multiplePokemon/{ids}")
    @Test
    void deleteMultiplePokemonByIds() {
        // GIVEN
        List<Pokemon> pokemonInserted = pokemonRepository.saveAll(testHelper.getListPokemon());
        List<String> idsInserted = pokemonInserted.stream().map(Pokemon::getId).map(Pokemong::toString).collect(toList());
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/persons/" + String.join(",", idsInserted), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Long>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        assertThat(pokemonRepository.count()).isEqualTo(0L);
    }
     */
    @DisplayName("DELETE /pokemon")
    @Test
    void deletePokemon() {
        // GIVEN
        pokemonRepository.saveAll(testHelper.getListPokemon());
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/pokemon", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Long>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        assertThat(pokemonRepository.count()).isEqualTo(0L);
    }

    @DisplayName("PUT /pokemon")
    @Test
    void putPokemon() {
        // GIVEN
        Pokemon pokemonInserted = pokemonRepository.save(testHelper.getBulbasaur());
        // WHEN
        HttpEntity<Pokemon> body = new HttpEntity<>(pokemonInserted);
        ResponseEntity<Pokemon> result = rest.exchange(URL + "/pokemon", HttpMethod.PUT, body, new ParameterizedTypeReference<Pokemon>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(pokemonRepository.findOne(pokemonInserted.getId()));
        assertThat(pokemonRepository.count()).isEqualTo(1L);
    }

    @DisplayName("PUT /multiplePokemon with 2 pokemon")
    @Test
    void putMultiplePokemon() {
        // GIVEN
        List<Pokemon> pokemonInserted = pokemonRepository.saveAll(testHelper.getListPokemon());
        // WHEN
        HttpEntity<List<Pokemon>> body = new HttpEntity<>(pokemonInserted);
        ResponseEntity<Long> result = rest.exchange(URL + "/multiplePokemon", HttpMethod.PUT, body, new ParameterizedTypeReference<Long>() {
        });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        Pokemon max = pokemonRepository.findOne(pokemonInserted.get(0).getId());
        Pokemon alex = pokemonRepository.findOne(pokemonInserted.get(1).getId());
        assertThat(pokemonRepository.count()).isEqualTo(2L);
    }

    private void createPokemonCollectionIfNotPresent(MongoClient mongoClient) {
        // This is required because it is not possible to create a new collection within a multi-documents transaction.
        // Some tests start by inserting 2 documents with a transaction.
        MongoDatabase db = mongoClient.getDatabase("pokedex");
        if (!db.listCollectionNames().into(new ArrayList<>()).contains("pokemon")) {
            db.createCollection("pokemon");
        }
    }
}
