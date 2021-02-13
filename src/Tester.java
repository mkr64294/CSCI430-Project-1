import java.util.*;
import java.text.*;
import java.io.*;

public class Tester {

  public static void main(String[] args) {

    ShoppingCart sc = new ShoppingCart();
    
    sc.addItem("charger", 5);
    sc.addItem("charger", 7);
    sc.addItem("phone", 8);
    sc.addItem("phone", 1);
    
    System.out.println(sc.showQuantity("charger"));
    System.out.println(sc.showQuantity("phone"));

    System.out.println(sc.findProduct("phone").getPName() + " " + sc.findProduct("phone").getQty());
    System.out.println(sc.findProduct("charger").getPName() + " " + sc.findProduct("charger").getQty());

    if(sc.setItemQty("charger",12))
    System.out.println("worked");
    

  Client c1 = new Client(1, "khim");
  Client c2 = new Client(2, "Dhoni");
  
  c1.changeAddress("316 7th ave");
  c2.changeAddress("999 9th ave");

  c1.addCredit(100);
  c2.addCredit(200);

  System.out.println(c1.getCredit());
  System.out.println(c2.getCredit());

  c1.removeCredit(20);
  c2.removeCredit(10);
   
  System.out.println(c1.getcId());
  System.out.println(c1.getcName());
  
  System.out.println(c1.getcAddress());
  System.out.println(c2.getcAddress());
  
  System.out.println(c1.getCredit() + " total remaining credit");
  System.out.println(c2.getCredit() + " total remaining credit");

  ClientList clientlist = new ClientList();
  clientlist.insertClient(c1);
  clientlist.insertClient(c2);
  
  System.out.println(clientlist);
 }
}