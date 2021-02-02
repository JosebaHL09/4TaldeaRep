using Pokemon_ASP.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace Pokemon_ASP.Controllers
{
    public class LoginController : Controller
    {
        public ActionResult Login()
        {
            return View("Login");
        }
        public ActionResult Register()
        {
            return View("Register");
        }
        public ActionResult Logout()
        {
            Pokemon_ASP.Models.User.logged = false;
            return RedirectToAction("Index", "Pokemon");
        }
        public async Task<ActionResult> LoginKonprobatu(FormCollection collection)
        {
            string user = collection["Username"];
            string pas = collection["Password"];
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Models.Pokemon.BaseURL);
                client.DefaultRequestHeaders.Clear();
                HttpResponseMessage Res;
                Res = await client.GetAsync("api/user/?password=" + pas + "&username=" + user);
                if (Res.IsSuccessStatusCode)
                {
                    var PokResponse = Res.Content.ReadAsStringAsync().Result;
                    if (PokResponse == "true")
                    {
                        Pokemon_ASP.Models.User.logged = true;
                        return RedirectToAction("Index", "Pokemon");
                    }
                }
                return View("Error");
            }
        }

        public ActionResult RegisterUser(FormCollection collection)
        {
            string user = collection["Username"];
            string pas = collection["Password"];
            User newUser = new User(user, pas);

            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri(Models.Pokemon.BaseURL);

                //HTTP POST
                var content = new JsonContent(newUser);
                var postTask = client.PostAsJsonAsync<User>("api/user", newUser);
                postTask.Wait();
                var Res = postTask.Result;
                var result = postTask.Result;
                if (result.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index", "Pokemon");
                }
            }
            return RedirectToAction("Error");
        }
    }
}