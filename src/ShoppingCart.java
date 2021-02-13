

import java.util.*;
import java.text.*;
import java.io.*;

public class ShoppingCart {
  private int supID;
  private LinkedList<ItemQty> items;
 
  public ShoppingCart(){
    items = new LinkedList<ItemQty>();
  }

  public ShoppingCart(int supID, LinkedList<ItemQty> items){
    this.supID = supID;
    this.items = items;
  }

  public int getSupID(){
    return supID;
  }

  public void setSupID(int newID){
    this.supID = newID;
  }
	
  public void addItem(String itemName, int qty){
    if(findProduct(itemName).getPName() == itemName){
       findProduct(itemName).addQty(qty);
     } else {
	items.add(new ItemQty(itemName, qty));
	findProduct(itemName).setQty(qty);
    }
  }

  public int showQuantity(String itemName){
    if(findProduct(itemName).getPName() != "not found"){
      return findProduct(itemName).getQty();
    }
    return -9999;
  }

  public ItemQty findProduct(String pName){
    for(int i = 0; i < items.size(); i++){
      //if(items.get(i).getPName() == pName){
      if(items.get(i).getPName()== pName){
        return items.get(i);
      }
    }
    return new ItemQty("not found", -1);
  }

  
  public boolean setItemQty(String pName, int qty){
    if(findProduct(pName).getPName() != "not found"){
      findProduct(pName).setQty(qty);
      return true;
    }
    return false;
  }
  
}

 class ItemQty{
  private String pName;
  private int qty;

  public ItemQty(String pName, int qty){
    this.pName = pName;
    this.qty = qty;
  }

  public String getPName(){
    return pName;
  }
  public void setPName(String newName){
    this.pName = newName;
    return;
  }
  public int getQty(){
    return this.qty;
  }
  public void setQty(int newQty){
    this.qty = newQty;
  }

  public void addQty(int qty){
    this.qty += qty;
 }
}














