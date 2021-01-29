using Newtonsoft.Json;
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
    public class LoginController : Controller
    {
        // GET: Login
        string Baseurl = "http://192.168.72.30:8080/";
        public async Task<ActionResult> Logeatu(FormCollection collection)
        {
            string erab = collection["izena"];
            string pas = collection["pasahitza"];

            using (var client = new HttpClient())
            {
                //Passing service base url  
                client.BaseAddress = new Uri(Baseurl);

                client.DefaultRequestHeaders.Clear();
                //Define request data format  
                //client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                //Sending request to find web api REST service resource GetAllEmployees using HttpClient 
                HttpResponseMessage Res;
                Res = await client.GetAsync("api/user/?password=" + pas + "&username=" + erab);
                if (Res.IsSuccessStatusCode)
                {
                    //Storing the response details recieved from web api   
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;

                    //Deserializing the response recieved from web api and storing into the Employee list  
                    if (PokResponse == "true")
                    {
                        return View("Index", "Pokemon");
                    }
                    
                        
                    
                }

                return View("Index");

            }
        }
        public ActionResult Index()
        {
            return View();
        }

        // GET: Login1/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: Login1/Create
        public ActionResult Create(FormCollection collection)
        {
           
            return View();
        }

        // GET: Login1/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: Login1/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Login1/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: Login1/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }
    }
}
