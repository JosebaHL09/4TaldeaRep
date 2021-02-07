using Pokemon_ASP.Models;
using System;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Mvc;
using PagedList;
using System.Linq;

namespace Pokemon_ASP.Controllers
{
    public class PokemonController : Controller
    {
        public ActionResult Index()
        {
            return View("Index");
        }
        public async Task<ActionResult> PokemonList(int? page, string type)
        {
            List<Pokemon> PokInfo = new List<Pokemon>();
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Models.Pokemon.BaseURL);
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
        public ActionResult Insert()
        {
            return View("Insert");
        }
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            string izena = collection["Name"];
            string image = collection["Img"];
            string height = collection["Height"];
            height += " m";
            string weight = collection["Weight"];
            weight += " kg";

            //Types
            string type1 = Request["type1"];
            string type2 = Request["type2"];
            List<string> types = new List<string>();
            types.Add(type1);
            if (type2 != ("none") && type1 != type2)
            {
                types.Add(type2);
            }

            //Weaknesses
            string weaknessesString = Request["weaknesses"];
            List<string> weaknesses = weaknessesString.Split(',').ToList();

            Pokemon newPokemon = new Pokemon(izena, image, height, weight, types, weaknesses);

            //Evolutions
            string evo1Type = Request["evo1"];
            newPokemon.prev_evolution = new List<Pokemon>();
            newPokemon.next_evolution = new List<Pokemon>();
            if (evo1Type != "none")
            {
                string evo1Num = Request["evo1_num"];
                string evo1Name = Request["evo1_name"];
                if (evo1Type == "prev")
                {
                    newPokemon.prev_evolution.Add(new Pokemon(evo1Num, evo1Name));
                }
                else if (evo1Type == "next")
                {
                    newPokemon.next_evolution.Add(new Pokemon(evo1Num, evo1Name));
                }

            }
            string evo2Type = Request["evo2"];
            if (evo2Type != "none")
            {
                string evo2Num = Request["evo2_num"];
                string evo2Name = Request["evo2_name"];
                if (evo2Type == "prev")
                {
                    newPokemon.prev_evolution.Add(new Pokemon(evo2Num, evo2Name));
                }
                else if (evo2Type == "next")
                {
                    newPokemon.next_evolution.Add(new Pokemon(evo2Num, evo2Name));
                }
            }

            if(newPokemon.prev_evolution.Count == 0)
            {
                newPokemon.prev_evolution = null;
            }
            if (newPokemon.next_evolution.Count == 0)
            {
                newPokemon.next_evolution = null;
            }


            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Models.Pokemon.BaseURL);

                //HTTP POST
                var content = new JsonContent(newPokemon);
                var postTask = client.PostAsJsonAsync<Pokemon>("api/pokemon", newPokemon);
                postTask.Wait();
                var Res = postTask.Result;
                var result = postTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("PokemonList", new { Page = 1, Type = "0" });
                }
            }
            return RedirectToAction("Index", "Pokemon");
        }
        public ActionResult DeletePokemon(int id)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Models.Pokemon.BaseURL);

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
    }
}