import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private int cId;
  private String cName; 
  private String cAddress;
  private double credit;
  private static final String MEMBER_STRING = "C";
  private LinkedList<Product> pList = new LinkedList<>(); //list of products with quantity of item
  private ProductList productList = new ProductList();
  public Client(int cId, String cName) {
    this.cId= cId;   
    this.cName = cName;        
  }

    public int getcId(){
      return cId;
    }

    public String getcName(){
      return cName;
    }

    public String getcAddress(){
      return cAddress;
    }

    public double getCredit(){
      return credit;
    }

    public void changeAddress(String newAddress){
      this.cAddress = newAddress;
    }
  
    public void addCredit(double amount) {
      this.credit += amount;
    }
    
    public void removeCredit(double amount) {
      this.credit -= amount;
    }
    
    public void changeName(String newName){
      this.cName = newName;
    }

    public String toString() {
      String string = " \n Client name:  " + cName + " \n address : " + cAddress + " \n id : " + cId;
      return string;
    }
}



/*
  //adds the product in the cart, if cart is empty. otherwise adds up the product
    public void addCart(int supID, String pName, int qty){
      productList.addToStock(supID, pName, qty);
      
      int toAddQuantityIndex = -1;
      for (int i = 0; i < pList.size(); i++) {
        if (pList.get(i).getPName().equalsIgnoreCase(pName)) {
          toAddQuantityIndex = i;
          break;
        }
      }
      
      if(toAddQuantityIndex >= 0){
        this.pList.get(toAddQuantityIndex).addToStock(qty);
      } else {
        Product p = new Product(pName);
        p.addStock(qty);
        this.pList.add(p);      
      } return;
      
    }

    //remove the item from the cart or decrease the amount of prodcut 
    public boolean removeCart(String pName, int qty){
      int toRemoveIndex = -1;
      for (int i = 0; i < pList.size(); i++) {
        if (pList.get(i).getPName().equalsIgnoreCase(pName)) {
          toRemoveIndex = i;
          break;
        }
      }
      
      if(toRemoveIndex >= 0){
        this.pList.remove(toRemoveIndex);
        return true;
      }
      return false;
    }
   
    public boolean inCart() {
      return this.pList.size() > 0;
    }

*/
  









  