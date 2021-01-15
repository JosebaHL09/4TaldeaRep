using ConsumingWebAapiRESTinMVC;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Mvc;
using Trial.Models;

namespace ConsumingWebAapiRESTinMVC.Controllers
{
    public class HomeController : Controller
    {
        //Hosted web API REST Service base url  
        string Baseurl = "http://192.168.72.7:8080/";
        public async Task<ActionResult> Index()
        {
            List<Person> PersInfo = new List<Person>();

            using (var client = new HttpClient())
            {
                //Passing service base url  
                client.BaseAddress = new Uri(Baseurl);

                client.DefaultRequestHeaders.Clear();
                //Define request data format  
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                //Create new Person
                Person pers1 = new Person("Markel", "Salgado", 19);
                Person pers2 = new Person("Pablo", "Lopez", 19);
                var content = new JsonContent(pers1);
                var postTask = client.PutAsJsonAsync<Person>("api/person",pers2);
                postTask.Wait();
                var Res = postTask.Result;
                //Sending request to find web api REST service resource GetAllEmployees using HttpClient 
                //HttpResponseMessage Res = await client.PostAsync("api/person", content);
                //HttpResponseMessage Res = await client.DeleteAsync("api/person");

                //Checking the response is successful or not which is sent using HttpClient  
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api   
                    var PersResponse = Res.Content.ReadAsStringAsync().Result;

                    //Deserializing the response recieved from web api and storing into the Employee list  
                    //PersInfo = JsonConvert.DeserializeObject<List<Person>>(PersResponse);

                }
                //returning the employee list to view  
                return View();
            }
        }
    }
}