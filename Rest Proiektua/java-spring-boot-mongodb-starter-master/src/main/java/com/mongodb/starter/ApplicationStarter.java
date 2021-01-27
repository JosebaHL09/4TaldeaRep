package com.mongodb.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  Main Class
 * 
 */
@SpringBootApplication
public class ApplicationStarter {

    /**
     *
     * @param args Contains the supplied command-line arguments as an array of String objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
