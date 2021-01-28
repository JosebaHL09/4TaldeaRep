using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;

namespace Pokemon_4.Taldea.Models
{
    public class Pokemon
    {
        public int id { get; set; }
        public String num { get; set; }
        public String name { get; set; }
        public String img { get; set; }
        public List<String> type { get; set; }
        public String height { get; set; }
        public String weight { get; set; }
        public List<String> weaknesses { get; set; }
        public List<Pokemon> prev_evolution { get; set; }
        public List<Pokemon> next_evolution { get; set; }

        public Pokemon()
        {
        }

        public Pokemon(String name, String img, String height, String weight)
        {
            this.name = name;
            this.img = img;
            this.height = height;
            this.weight = weight;
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
    }

    public class erabiltzailea
    {
        public string Izena { get; set; }
        public string pasahitza { get; set; }
       
        public erabiltzailea(string izena, string pasahitza)
        {
        }
    }  
}

