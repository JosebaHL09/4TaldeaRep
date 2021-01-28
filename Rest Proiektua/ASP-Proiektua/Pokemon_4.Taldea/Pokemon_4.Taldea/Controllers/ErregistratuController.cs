using Pokemon_4.Taldea.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Pokemon_4.Taldea.Controllers
{
    public class ErregistratuController : Controller
    {
        // GET: Login
        public ActionResult Index()
        {
            return View();
        }
        public ActionResult Erregistratu()
        {
            return View();
        }
        // GET: Login/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: Login/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Login/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                string iz = collection["Izena"];
                string ab = collection["Abizena"];
                string email = collection["emaila"];
                string erab = collection["erabiltzailea"];
                string pas = collection["pasahitza"];
                //erabiltzailea.insertErabiltzailea(iz, ab, email, erab, pas);
                return RedirectToAction("Index", "Home");
            }
            catch
            {
                return View("Index", "Home");
            }
        }

        // GET: Login/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: Login/Edit/5
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

        // GET: Login/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: Login/Delete/5
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