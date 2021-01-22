using System;
using System.Collections.Generic;

namespace Pokemon_4.Taldea.Models
{
    public class Pokemon
    {
        public int id;
        public String num;
        public String name;
        public String img;
        public List<String> type;
        public String height;
        public String weight;
        public List<String> weaknesses;
        public List<Pokemon> prev_evolution;
        public List<Pokemon> next_evolution;

        public Pokemon()
        {
        }

        public Pokemon(String num, String name)
        {
            this.num = num;
            this.name = name;
        }

        public Pokemon(int id, String name, String img, List<String> type, String height, String weight, List<String> weaknesses, List<Pokemon> prev_evolution, List<Pokemon> next_evolution)
        {
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
        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getNum()
        {
            return num;
        }

        public void setNum(String num)
        {
            this.num = num;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getImg()
        {
            return img;
        }

        public void setImg(String img)
        {
            this.img = img;
        }

        public List<String> getType()
        {
            return type;
        }

        public void setType(List<String> type)
        {
            this.type = type;
        }

        public String getHeight()
        {
            return height;
        }

        public void setHeight(String height)
        {
            this.height = height;
        }

        public String getWeight()
        {
            return weight;
        }

        public void setWeight(String weight)
        {
            this.weight = weight;
        }

        public List<String> getWeaknesses()
        {
            return weaknesses;
        }

        public void setWeaknesses(List<String> weaknesses)
        {
            this.weaknesses = weaknesses;
        }

        public List<Pokemon> getPrev_evolution()
        {
            return prev_evolution;
        }

        public void setPrev_evolution(List<Pokemon> prev_evolution)
        {
            this.prev_evolution = prev_evolution;
        }

        public List<Pokemon> getNext_evolution()
        {
            return next_evolution;
        }

        public void setNext_evolution(List<Pokemon> next_evolution)
        {
            this.next_evolution = next_evolution;
        }

        public string toString()
        {
            return "Pokemon{" + "id=" + id + ", name=" + name + ", img=" + img + ", type=" + type + ", height=" + height + ", weight=" + weight + ", weaknesses=" + weaknesses + ", prev_evolution=" + prev_evolution + ", next_evolution=" + next_evolution + '}';
        }

        public String toStringPokemonArray(List<Pokemon> lista)
        {
            String listString = "";
            if (lista != null)
            {
                foreach (Pokemon p in lista)
                {
                    listString += p.toStringSimple();
                }
                return listString;
            }
            return null;
        }

        public String toStringSimple()
        {
            return "Pokemon{" + "id=" + num + ", name=" + name + '}';
        }

        public void insertPokemon(String name,String img, List<String> type, String height, String weight,List<String> weakness,List<Pokemon> prev_evo,List<Pokemon> next_evo)
        {
            
        }
    }
}
