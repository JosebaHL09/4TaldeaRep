using Newtonsoft.Json;
using System;
using System.Collections.Generic;
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
        public string name { get; set; }
        public string img { get; set; }
        public List<string> type { get; set; }
        public string height { get; set; }
        public string weight { get; set; }
        public List<string> weaknesses { get; set; }
        public List<Pokemon> Prev_evolution { get; set; }
        public List<Pokemon> Next_evolution { get; set; }

        public Pokemon()
        {
        }
        public Pokemon(string name, string img, string height, string weight)
        {
            this.name= name;
            this.img = img;
            this.height = height;
            this.weight = weight;
        }

        public static string BaseURL = "http://192.168.72.30:8080/";

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

    }
}