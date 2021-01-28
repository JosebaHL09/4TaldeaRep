using Pokemon_4.Taldea.Models;
using System;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Mvc;
using PagedList;

namespace Pokemon_4.Taldea.Controllers
{
    public class PokemonController : Controller
    {
        //Hosted web API REST Service base url  
        string Baseurl = "http://192.168.72.30:8080/";
        public async Task<ActionResult> Index(int? page, string type)
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
                HttpResponseMessage Res;
                if (type != "0")
                {
                    Res = await client.GetAsync("api/pokemon/type/" + type);
                   
                }
                else
                {
                    Res = await client.GetAsync("api/pokemon");
                }
                
                 
                //Checking the response is successful or not which is sent using HttpClient  
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api   
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;

                    //Deserializing the response recieved from web api and storing into the Employee list  
                    PokInfo = JsonConvert.DeserializeObject<List<Pokemon>>(PokResponse);

                }
                //returning the employee list to view  
                int pageSize = 10;
                int pageNumber = (page ?? 1);
                return View(PokInfo.ToPagedList(pageNumber, pageSize));
            }
        }
        
        public ActionResult ezabatu(int id)
        {

            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://192.168.72.30:8080/");

                //HTTP DELETE
                var deleteTask = client.DeleteAsync("api/pokemon/" + id);
                deleteTask.Wait();

                var result = deleteTask.Result;
                if (result.IsSuccessStatusCode)
                {

                    return RedirectToAction("Index", new { Page = 1, Type = "0" });
                }
            }

            return RedirectToAction("Index", new { Page = 1, Type = "0" });
        }

        public ActionResult Insert(FormCollection collection)
        {
            return View("Insert");
        }

        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            string num = collection["num"];
            string izena = collection["name"];
            string image = collection["img"];
            string height = collection["height"];
            string weight = collection["weight"];

            Pokemon p = new Pokemon( izena, image, height, weight);

            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://192.168.72.30:8080/");

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
       
        public async Task<ActionResult> Delete(int? page, string type)
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
                HttpResponseMessage Res;
                if (type != "0")
                {
                    Res = await client.GetAsync("api/pokemon/type/" + type);

                }
                else
                {
                    Res = await client.GetAsync("api/pokemon");
                }


                //Checking the response is successful or not which is sent using HttpClient  
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api   
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;

                    //Deserializing the response recieved from web api and storing into the Employee list  
                    PokInfo = JsonConvert.DeserializeObject<List<Pokemon>>(PokResponse);

                }
                //returning the employee list to view  
                int pageSize = 10;
                int pageNumber = (page ?? 1);
                return View(PokInfo.ToPagedList(pageNumber, pageSize));
            }
        }
    }
}
