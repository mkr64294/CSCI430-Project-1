import java.util.*;
import java.text.*;
import java.io.*;

public class ShoppingCart {
  private LinkedList<ItemQty> items;

  public ShoppingCart() {
    items = new LinkedList<ItemQty>();
  }

  public ShoppingCart(LinkedList<ItemQty> items) {
    this.items = items;
  }

  public void addItem(String itemName, int qty, int sID) {
    if (findProduct(itemName, sID).getPName() == itemName && findProduct(itemName, sID).getSID() == sID) {
      findProduct(itemName, sID).addQty(qty);
    } else {
      items.add(new ItemQty(itemName, qty, sID));
    }
  }

  public boolean removeItem(String itemName, int qty, int sID) {
    if (findProduct(itemName, sID).getPName() == itemName) {
      int totalQty = findProduct(itemName, sID).getQty();
      int position = items.indexOf(findProduct(itemName, sID));
      if (totalQty <= qty) {
        items.remove(findProduct(itemName, sID));
      } else {
        items.get(position).removeQty(qty);
      }
      return true;
    }
    return false;
  }

  public ItemQty findProduct(String pName, int sID) {
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).getPName() == pName && items.get(i).getSID() == sID) {
        return items.get(i);
      }
    }
    return new ItemQty("not found", -1, -1);
  }

  public int showQuantity(String itemName, int sID) {
    if (findProduct(itemName, sID).getPName() != "not found") {
      return findProduct(itemName, sID).getQty();
    }
    return -9999;
  }

  public boolean setItemQty(String pName, int qty, int sID) {
    if (findProduct(pName, sID).getPName() != "not found") {
      findProduct(pName, sID).setQty(qty);
      return true;
    }
    return false;
  }

}

class ItemQty implements Serializable {
  private String pName;
  private int qty;
  private int sID;

  public ItemQty(String prodName, int quantity, int supID) {
    pName = prodName;
    qty = quantity;
  }

  public String getPName() {
    return pName;
  }

  public void setPName(String newName) {
    this.pName = newName;
    return;
  }

  public int getSID() {
    return sID;
  }

  public void setSID(int supplierID) {
    sID = supplierID;
    return;
  }

  public int getQty() {
    return this.qty;
  }

  public void setQty(int newQty) {
    this.qty = newQty;
  }

  public void addQty(int qty) {
    this.qty += qty;
  }

  public void removeQty(int qty) {
    this.qty -= qty;
  }
}
