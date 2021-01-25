using Pokemon_4.Taldea.Models;
using System;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Mvc;


namespace Pokemon_4.Taldea.Controllers
{
    public class PokemonController : Controller
    {
        //Hosted web API REST Service base url  
        string Baseurl = "http://192.168.72.7:8080/";
        public async Task<ActionResult> Index()
        {
            List<Pokemon> PokInfo = new List<Pokemon>();

            using (var client = new HttpClient())
            {
                //Passing service base url  
                client.BaseAddress = new Uri(Baseurl);

                client.DefaultRequestHeaders.Clear();
                //Define request data format  
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                //Sending request to find web api REST service resource GetAllEmployees using HttpClient 

                HttpResponseMessage Res = await client.GetAsync("api/pokemon");

                //Checking the response is successful or not which is sent using HttpClient  
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api   
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;

                    //Deserializing the response recieved from web api and storing into the Employee list  
                    PokInfo = JsonConvert.DeserializeObject<List<Pokemon>>(PokResponse);

                }
                //returning the employee list to view  
                return View(PokInfo);
            }
        }
        
        public ActionResult Delete(int id)
        {
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://192.168.72.7:8080/");

                //HTTP DELETE
                var deleteTask = client.DeleteAsync("api/pokemon/" + id);
                deleteTask.Wait();

                var result = deleteTask.Result;
                if (result.IsSuccessStatusCode)
                {

                    return RedirectToAction("Index");
                }
            }

            return RedirectToAction("Index");
        }

        public ActionResult Insert()
        {
            return View("Insert");
        }

        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {

            string izena = collection["name"];
            string i = collection["img"];
            string height = collection["height"];
            string weight = collection["weight"];

            Pokemon p = new Pokemon(izena, i);

            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://192.168.72.7:8080/");

                //HTTP POST
                var content = new JsonContent(p);
                var postTask = client.PostAsJsonAsync<Pokemon>("api/pokemon", p);
                postTask.Wait();
                var Res = postTask.Result;
                var result = postTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
            }

            return RedirectToAction("Index");
        }
    }
}
