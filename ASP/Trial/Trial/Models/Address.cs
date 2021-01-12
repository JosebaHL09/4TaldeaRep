using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Trial.Models
{
    public class Address
    {
        public int number { get; set; }
        public String street { get; set; }
        public String postcode { get; set; }
        public String city { get; set; }
        public String country { get; set; }

        public override string ToString()
        {
            return base.ToString() + ": " + street.ToString();
        }
    }
}