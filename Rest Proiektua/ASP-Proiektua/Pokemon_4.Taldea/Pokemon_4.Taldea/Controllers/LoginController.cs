﻿using Pokemon_4.Taldea.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Pokemon_4.Taldea.Controllers
{
    public class LoginController : Controller
    {
        // GET: Login
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
        public ActionResult Create()
        {
            return View();
        }

        // POST: Login1/Create
        [HttpPost]
        //public ActionResult Create(FormCollection collection)
        //{
        //    try
        //    {
        //        string erab = collection["erabiltzailea"];
        //        string pas = collection["pasahitza"];
        //        if (erabiltzailea.getErabiltzaileaByIzPa(erab, pas))
        //        {
        //            return RedirectToAction("Index", "Home");
        //        }
        //        else
        //        {
        //            return RedirectToAction("Index", "Arazoa");
        //        }
        //    }
        //    catch
        //    {
        //        return View();
        //    }
        //}

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
