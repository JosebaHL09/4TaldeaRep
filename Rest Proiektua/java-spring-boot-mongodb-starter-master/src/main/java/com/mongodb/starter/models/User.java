/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongodb.starter.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 *
 * @author lopez.pablo
 */
public class User {

    @JsonSerialize(using = ToStringSerializer.class)
    private int id;
    private String username;
    private String password;

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id The ID of the Pokemon
     * @return Instance of Pokemon
     */
    public User setId(int id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * @return
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * @return
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
