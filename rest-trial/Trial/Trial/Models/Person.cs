using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Trial.Models
{
    public class Person
    {

        [BsonId]
        public ObjectId Id { get; set; }
        public String firstName { get; set; }
        public String lastName { get; set; }
        public int age { get; set; }
        //public Address address { get; set; }
         //public DateTime createdAt { get; set; }
        //public Boolean insurance { get; set; }
        //public List<Car> cars { get; set; }

        public Person()
        {

        }
       

        public Person(String firstName, string lastName, int age)
        {
            //this.Id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
    }
}