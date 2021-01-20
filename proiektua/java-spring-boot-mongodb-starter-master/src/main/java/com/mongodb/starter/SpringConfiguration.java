package com.mongodb.starter;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.starter.models.Pokemon;
import com.mongodb.starter.repositories.MongoDBPokemonRepository;
import com.mongodb.starter.repositories.PokemonRepository;
import java.util.List;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 *
 * @author lopez.pablo
 */
@Configuration
public class SpringConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    /**
     *
     * @return
     */
    @Bean
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
                                                      .applyConnectionString(new ConnectionString(connectionString))
                                                      .codecRegistry(codecRegistry)
                                                      .build());
    }
    
    /**
     *
     * @return
     */
    @Bean
    public PokemonRepository pokemonRepository(){
        return new MongoDBPokemonRepository();
    }

}
