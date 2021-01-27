using Newtonsoft.Json;
using PagedList;
using Pokemon_4.Taldea.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace Pokemon_4.Taldea.Controllers
{
    public class TypeController : Controller
    {
        //Hosted web API REST Service base url  
        string Baseurl = "http://192.168.72.7:8080/";
        public async Task<ActionResult> Index()
        {
            List<Types> TypeInfo = new List<Types>();

            using (var client = new HttpClient())
            {
                //Passing service base url  
                client.BaseAddress = new Uri(Baseurl);

                client.DefaultRequestHeaders.Clear();
                //Define request data format  
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                //Sending request to find web api REST service resource GetAllEmployees using HttpClient 

                HttpResponseMessage Res = await client.GetAsync("api/type");

                //Checking the response is successful or not which is sent using HttpClient  
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api   
                    var TypeResponse = Res.Content.ReadAsStringAsync().Result;

                    //Deserializing the response recieved from web api and storing into the Employee list  
                    TypeInfo = JsonConvert.DeserializeObject<List<Types>>(TypeResponse);

                }
                ////returning the employee list to view  
                //int pageSize = 10;
                //int pageNumber = (page ?? 1);
                return View(TypeInfo/*.ToPagedList(pageNumber, pageSize)*/);
            }
        }
    } }