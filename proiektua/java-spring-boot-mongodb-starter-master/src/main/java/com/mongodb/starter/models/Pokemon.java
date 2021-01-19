package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import java.util.Objects;


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
    
    public int getId() {
        return id;
    }

    public Pokemon setId(int id) {
        this.id = id;
        return this;
    }

    public String getNum() {
        return num;
    }

    public Pokemon setNum(String num) {
        this.num = num;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pokemon setName(String name) {
        this.name = name;
        return this;
    }

    public String getImg() {
        return img;
    }

    public Pokemon setImg(String img) {
        this.img = img;
        return this;
    }

    public List<String> getType() {
        return type;
    }

    public Pokemon setType(List<String> type) {
        this.type = type;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public Pokemon setHeight(String height) {
        this.height = height;
        return this;
    }

    public String getWeight() {
        return weight;
    }

    public Pokemon setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public Pokemon setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
        return this;
    }

    public List<Pokemon> getPrev_evolution() {
        return prev_evolution;
    }

    public Pokemon setPrev_evolution(List<Pokemon> prev_evolution) {
        this.prev_evolution = prev_evolution;
        return this;
    }

    public List<Pokemon> getNext_evolution() {
        return next_evolution;
    }

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

    public String toStringSimple() {
        return "Pokemon{" + "id=" + num + ", name=" + name + '}';
    }
    
}
