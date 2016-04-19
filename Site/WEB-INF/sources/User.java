
/* A servlet to display the contents of the MySQL movieDB database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
// 


public class User 
{
   private int userId;
   public String firstName;
   private String lastName;
   private String creditCardNumber;
   private String address;
   private String email;
  
   public User(int userId, String firstName, String lastName, String creditCardNumber, String address, String email)
   {
      this.userId=userId;
      this.firstName=firstName;
      this.lastName=lastName;
      this.creditCardNumber=creditCardNumber;
      this.address=address;
      this.email=email;
    }
   public User()
   {
    this(0, null, null,null, null, null);
   }
   public int getId()
   {
      return userId;
   }
   public String getFirstName()
   {
      return firstName;
   }
   public String getLastName()
   {
      return lastName;
   }
   public String getCreditCardNumber()
   {
      return creditCardNumber;
   }
   public String getAddress()
   {
      return address;
   }
   public String getEmail()
   {
      return email;
   }
   
}