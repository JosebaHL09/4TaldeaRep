using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web;

namespace Pokemon_ASP.Models
{
    public class Pokemon
    {
        public int id { get; set; }
        public string num { get; set; }
        [Required]
        public string name { get; set; }
        [Required]
        public string img { get; set; }
        [Required]
        public List<string> type { get; set; }
        [Required]
        public string height { get; set; }
        [Required]
        public string weight { get; set; }
        [Required]
        public List<string> weaknesses { get; set; }
        public List<Pokemon> prev_evolution { get; set; }
        public List<Pokemon> next_evolution { get; set; }

        public Pokemon()
        {
        }

        public Pokemon(string num, string name)
        {
            this.num = num;
            this.name = name;
        }
        public Pokemon(string name, string img, string height, string weight, List<string> type, List<string> weaknesses)
        {
            this.name= name;
            this.img = img;
            this.height = height;
            this.weight = weight;
            this.type = type;
            this.weaknesses = weaknesses;
        }

        public static string BaseURL = "http://localhost:8080/";

        // Tipo guztien lista era asinkrono baten jasoko ditugu. ConfigureAwait false izan behar da errorerik ez emateko
        static async Task<List<string>> GetTypeListAsync()
        {
            List<string> TypeInfo = new List<string>();
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(BaseURL);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                HttpResponseMessage Res = await client.GetAsync("api/type").ConfigureAwait(false);
                if (Res.IsSuccessStatusCode)
                {
                    var TypeResponse = Res.Content.ReadAsStringAsync().Result;
                    TypeInfo = JsonConvert.DeserializeObject<List<string>>(TypeResponse);
                }
                return TypeInfo;
            }
        }

        public static List<string> GetTypeList()
        {
            return GetTypeListAsync().Result;
        }
        static async Task<Pokemon> GetRandomPokemonAsync()
        {
            Pokemon RandomPokInfo = new Pokemon();
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Models.Pokemon.BaseURL);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                HttpResponseMessage Res = await client.GetAsync("api/pokemon/random").ConfigureAwait(false);
                if (Res.IsSuccessStatusCode)
                {
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;
                    RandomPokInfo = JsonConvert.DeserializeObject<Pokemon>(PokResponse);
                }
                return RandomPokInfo;
            }
        }

        public static Pokemon GetRandomPokemon()
        {
            return GetRandomPokemonAsync().Result;
        }
    }
}