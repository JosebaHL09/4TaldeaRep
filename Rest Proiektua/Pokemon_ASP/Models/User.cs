using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Pokemon_ASP.Models
{
    public class User
    {
        [Required] 
        public string username { get; set; }
        [Required] 
        public string password { get; set; }

        public static bool logged { get; set; }

        public User(string username, string password)
        {
            this.username = username;
            this.password = password;
        }
    }

}