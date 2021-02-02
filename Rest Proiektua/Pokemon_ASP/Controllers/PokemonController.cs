using Pokemon_ASP.Models;
using System;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Mvc;
using PagedList;

namespace Pokemon_ASP.Controllers
{
    public class PokemonController : Controller
    {
        public string BaseURL = "http://192.168.72.30:8080/";
        public ActionResult Index()
        {
            return View("Index");
        }
        public async Task<ActionResult> PokemonList(int? page, string type)
        {
            List<Pokemon> PokInfo = new List<Pokemon>();
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(BaseURL);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                HttpResponseMessage Res;
                if (type != "0")
                {
                    Res = await client.GetAsync("api/pokemon/type/" + type);
                }
                else
                {
                    Res = await client.GetAsync("api/pokemon");
                }
                if (Res.IsSuccessStatusCode)
                {
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;
                    PokInfo = JsonConvert.DeserializeObject<List<Pokemon>>(PokResponse);
                }
                int pageSize = 10;
                int pageNumber = (page ?? 1);
                return View(PokInfo.ToPagedList(pageNumber, pageSize));
            }
        }
        public ActionResult Insert(FormCollection collection)
        {
            return View("Insert");
        }
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            string num = collection["Num"];
            string izena = collection["Name"];
            string image = collection["Img"];
            string height = collection["Height"];
            string weight = collection["Weight"];

            Pokemon p = new Pokemon( izena, image, height, weight);

            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(BaseURL);

                //HTTP POST
                var content = new JsonContent(p);
                var postTask = client.PostAsJsonAsync<Pokemon>("api/pokemon", p);
                postTask.Wait();
                var Res = postTask.Result;
                var result = postTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index", new { Page = 1, Type = "0" });
                }
            }
            return RedirectToAction("Index", new { Page = 1, Type = "0" });
        }
        public ActionResult DeletePokemon(int id)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(BaseURL);

                //HTTP DELETE
                var deleteTask = client.DeleteAsync("api/pokemon/" + id);
                deleteTask.Wait();

                var result = deleteTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("PokemonList", new { Page = 1, Type = "0" });
                }
            }

            return RedirectToAction("Index", new { Page = 1, Type = "0" });
        }
        public async Task<ActionResult> Delete(int? page, string type)
        {
            List<Pokemon> PokInfo = new List<Pokemon>();

            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(BaseURL);
                client.DefaultRequestHeaders.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                HttpResponseMessage Res;
                if (type != "0")
                {
                    Res = await client.GetAsync("api/pokemon/type/" + type);
                }
                else
                {
                    Res = await client.GetAsync("api/pokemon");
                }
                if (Res.IsSuccessStatusCode)
                {
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;
                    PokInfo = JsonConvert.DeserializeObject<List<Pokemon>>(PokResponse);
                }
                int pageSize = 10;
                int pageNumber = (page ?? 1);
                return View(PokInfo.ToPagedList(pageNumber, pageSize));
            }
        }

    }
}