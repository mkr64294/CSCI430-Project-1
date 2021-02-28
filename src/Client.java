import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private int cId;
  private String cName;
  private String cAddress;
  private ShoppingCart cart;

  public Client(String cName, String cAddress, int clientID) {
    this.cName = cName;
    this.cAddress = cAddress;
    cId = clientID;
    cart = new ShoppingCart();
  }

  public int getcId() {
    return cId;
  }

  public String getcName() {
    return cName;
  }

  public String getcAddress() {
    return cAddress;
  }

  public void changeAddress(String newAddress) {
    this.cAddress = newAddress;
  }

  public void changeName(String newName) {
    this.cName = newName;
  }

  public String toString() {
    String string = " \n Client name:  " + cName + " \n address : " + cAddress + " \n id : " + cId;
    return string;
  }

  public void addItem(String itemName, int qty, int sID) {
    cart.addItem(itemName, qty, sID);
    return;
  }

  public boolean removeItem(String itemName, int qty, int sID) {
    return cart.removeItem(itemName, qty, sID);
  }

  public ItemQty findProduct(String pName, int sID) {
    return cart.findProduct(pName, sID);
  }

  public int showQuantity(String itemName, int sID) {
    return cart.showQuantity(itemName, sID);
  }

  public boolean setItemQty(String pName, int qty, int sID) {
    return cart.setItemQty(pName, qty, sID);
  }

}
