package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import java.util.Objects;

/**
 *
 * Pokemon Class
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokemon {

    @JsonSerialize(using = ToStringSerializer.class)
    private int id;
    private String num;
    private String name;
    private String img;
    private List<String> type;
    private String height;
    private String weight;
    private List<String> weaknesses;
    private List<Pokemon> prev_evolution;
    private List<Pokemon> next_evolution;

    /**
     *
     * @return The ID of the Pokemon
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id  The ID of the Pokemon
     * @return Instance of Pokemon
     */
    public Pokemon setId(int id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return The Number of the Pokemon
     */
    public String getNum() {
        return num;
    }

    /**
     *
     * @param num  The Number of the Pokemon
     * @return Instance of Pokemon
     */
    public Pokemon setNum(String num) {
        this.num = num;
        return this;
    }

    /**
     *
     * @return The Name of the Pokemon
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name  The Name of the Pokemon
     * @return Instance of Pokemon
     */
    public Pokemon setName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return URL of the Image of the Pokemon as a String
     */
    public String getImg() {
        return img;
    }

    /**
     *
     * @param img  URL of the Image of the Pokemon as a String
     * @return Instance of Pokemon
     */
    public Pokemon setImg(String img) {
        this.img = img;
        return this;
    }

    /**
     *
     * @return List containing the types of the Pokemon (1 or 2)
     */
    public List<String> getType() {
        return type;
    }

    /**
     *
     * @param type  List containing the types of the Pokemon (1 or 2)
     * @return Instance of Pokemon
     */
    public Pokemon setType(List<String> type) {
        this.type = type;
        return this;
    }

    /**
     *
     * @return Height of the Pokemon as a String
     */
    public String getHeight() {
        return height;
    }

    /**
     *
     * @param height  Height of the Pokemon as a String
     * @return Instance of Pokemon
     */
    public Pokemon setHeight(String height) {
        this.height = height;
        return this;
    }

    /**
     *
     * @return Weight of the Pokemon as a String
     */
    public String getWeight() {
        return weight;
    }

    /**
     *
     * @param weight  Weight of the Pokemon as a String
     * @return Instance of Pokemon
     */
    public Pokemon setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    /**
     *
     * @return List containing the Weaknesses of the Pokemon (min. 1)
     */
    public List<String> getWeaknesses() {
        return weaknesses;
    }

    /**
     *
     * @param weaknesses List containing the Weaknesses of the Pokemon (min. 1)
     * @return Instance of Pokemon
     */
    public Pokemon setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
        return this;
    }

    /**
     *
     * @return List containing the Previous evolution(s) of the Pokemon. It can range from 0 (NULL) to a maximum of 2
     */
    public List<Pokemon> getPrev_evolution() {
        return prev_evolution;
    }

    /**
     *
     * @param prev_evolution List containing the Previous evolution(s) of the Pokemon. It can range from 0 (NULL) to a maximum of 2
     * @return Instance of Pokemon
     */
    public Pokemon setPrev_evolution(List<Pokemon> prev_evolution) {
        this.prev_evolution = prev_evolution;
        return this;
    }

    /**
     *
     * @return  List containing the Next evolution(s) of the Pokemon. Can be NULL
     */
    public List<Pokemon> getNext_evolution() {
        return next_evolution;
    }

    /**
     *
     * @param next_evolution List containing the Next evolution(s) of the Pokemon. Can be NULL
     * @return Instance of Pokemon
     */
    public Pokemon setNext_evolution(List<Pokemon> next_evolution) {
        this.next_evolution = next_evolution;
        return this;
    }

    @Override
    public String toString() {
        return "Pokemon{" + "id=" + id + ", name=" + name + ", img=" + img + ", type=" + type + ", height=" + height + ", weight=" + weight + ", weaknesses=" + weaknesses + ", prev_evolution=" + this.toStringPokemonArray(prev_evolution) + ", next_evolution=" + this.toStringPokemonArray(next_evolution) + '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pokemon other = (Pokemon) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.num, other.num)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.img, other.img)) {
            return false;
        }
        if (!Objects.equals(this.height, other.height)) {
            return false;
        }
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.weaknesses, other.weaknesses)) {
            return false;
        }
        if (!Objects.equals(this.prev_evolution, other.prev_evolution)) {
            return false;
        }
        if (!Objects.equals(this.next_evolution, other.next_evolution)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param lista  List of Pokemon
     * @return String representation of an List of Pokemon
     */
    public String toStringPokemonArray(List<Pokemon> lista) {
        String listString = "";
        if (lista != null) {
            for (Pokemon p : lista) {
                listString += p.toStringSimple();
            }
            return listString;
        }
        return null;
    }

    /**
     *
     * @return Simplified Pokemon.toString()
     */
    public String toStringSimple() {
        return "Pokemon{" + "id=" + num + ", name=" + name + '}';
    }

}
