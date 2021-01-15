package eus.uni.dam2.java.spring.rest.real.model;

import java.util.List;

public class Pokemon {

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

    public Pokemon() {
    }

    public Pokemon(String num, String name) {
        this.num = num;
        this.name = name;
    }

    public Pokemon(int id, String name, String img, List<String> type, String height, String weight, List<String> weaknesses, List<Pokemon> prev_evolution, List<Pokemon> next_evolution) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.type = type;
        this.height = height;
        this.weight = weight;
        this.weaknesses = weaknesses;
        this.prev_evolution = prev_evolution;
        this.next_evolution = next_evolution;
    }

    /* public Pokemon(int id, String name, String img, List<String> type, String height, String weight, List<String> weaknesses, List<Pokemon> prev_evolution, List<Pokemon> next_evolution) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.type = type;
        this.setHeight(height);
        this.setWeight(weight);
        this.weaknesses = weaknesses;
        this.prev_evolution = prev_evolution;
        this.next_evolution = next_evolution;
    }*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<Pokemon> getPrev_evolution() {
        return prev_evolution;
    }

    public void setPrev_evolution(List<Pokemon> prev_evolution) {
        this.prev_evolution = prev_evolution;
    }

    public List<Pokemon> getNext_evolution() {
        return next_evolution;
    }

    public void setNext_evolution(List<Pokemon> next_evolution) {
        this.next_evolution = next_evolution;
    }

    @Override
    public String toString() {
        return "Pokemon{" + "id=" + id + ", name=" + name + ", img=" + img + ", type=" + type + ", height=" + height + ", weight=" + weight + ", weaknesses=" + weaknesses + ", prev_evolution=" + this.toStringPokemonArray(prev_evolution) + ", next_evolution=" + this.toStringPokemonArray(next_evolution) + '}';
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
