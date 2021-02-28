import java.io.*;
import java.util.*;

public class Warehouse {

  public List<ClientBalance> clientList;
  public List<ProductSupplier> productList;
  public List<SupplierProduct> supplierList;

  public Warehouse() {
    clientList = new LinkedList<ClientBalance>();
    productList = new LinkedList<ProductSupplier>();
    supplierList = new LinkedList<SupplierProduct>();
  }

  public float makePayment(int cid, float amount) {

    Iterator iterator;
    ClientBalance c;
    for (iterator = clientList.iterator(); iterator.hasNext();) {
      c = (ClientBalance) iterator.next();
      if (c.client.getcId() == cid)
        return c.credit -= amount;
    }

    return -9999;
  }

  public float addCredit(int cid, float amount) {

    Iterator iterator;
    ClientBalance c;
    for (iterator = clientList.iterator(); iterator.hasNext();) {
      c = (ClientBalance) iterator.next();
      if (c.client.getcId() == cid)
        return c.credit += amount;
    }

    return -9999;
  }

  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(clientList);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void readObject(java.io.ObjectInputStream input) throws ClassNotFoundException, IOException {
    try {
      if (clientList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (clientList == null) {
          clientList = (List<Warehouse.ClientBalance>) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }

  public Iterator getClient() {
    return clientList.iterator();
  }

  public String clientsToString() {
    return clientList.toString();
  }

  public boolean isProduct(String pName) { // product is in the listing
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPName().equals(pName))
        return true;
    }
    return false;
  }

  public double getPrice(String pName, Supplier sup) { // returns 0 if not found

  }

  public boolean isSupplier(Supplier sup, String pName) { // does the supplier supply the object?

  }

  public boolean addSupplier(Supplier sup, String pName, double sPrice, int stock) { // true means was not already in
                                                                                     // list

  }

  public boolean addSupplier(Supplier sup, String pName, double sPrice) { // true means was not already in list

  }

  public boolean removeSupplier(String pName, Supplier sup) { // true means was in list and was removed;

  }

  public int numSuppliers(String pName) { // returns number of suppliers for the given product

  }

  public boolean setPrice(String pName, Supplier sup, double newPrice) { // returns 0 if not found

  }

  public boolean setDescription(String pName, String newDescription) { // sets a product description

  }

  public String getDescription(String pName) {

  }

  public boolean addProduct(String pName) { // adds a product the the product list
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPName().equals(pName))
        return false;
    }
    productList.add(new ProductSupplier(pName));
    return true;
  }

  public boolean addProduct(String pName, String pDescription) { // adds a product to the product list
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPName().equals(pName))
        return false;
    }
    productList.add(new ProductSupplier(pName, pDescription));
    return true;
  }

  public boolean removeProduct(String pName) { // removes a product from the product list
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPName().equals(pName)) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  public int getStock(String sName, String pName) { // returns -9999999 if supplier does not exist
    Iterator iterator1;
    Iterator iterator2;
    ProductSupplier p;
    SupplierQuantity q;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = p.supplierList.iterator(); iterator2.hasNext();) {
          q = (SupplierQuantity) iterator2.next();
          if (q.supplier.getSName().equals(sName))
            return q.quantity;
        }
      }
    }
    return -9999999;
  }

  public boolean addToStock(Supplier sup, String pName, int amtToAdd) { // adds an amount of product to stock for a
                                                                        // given supplier

  }

  public boolean removeFromStock(Supplier sup, String pName, int amtToRemove) { // adds an amount of product to stock
                                                                                // for a given supplier

  }

  public boolean setStock(Supplier sup, String pName, int amtToSet) { // sets the stock for a product from a supplier

  }

  public boolean addToWaitlist(int clientID, String pName, Supplier sup) { // adds clients to a suppliers product
                                                                           // waitlist

  }

  public boolean removeFromWaitlist(int clientID, String pName, Supplier sup) { // removes clients from a suppliers
                                                                                // product waitlist

  }

  public int popWaitlist(Supplier sup, String pName) { // returns -1 if product not found -2 if supplier not found
                                                       // otherwise returns client ID

  }

  // Create the class ProductSupplier, which contains the class SupplierQuantity
  // this is done so that every product we create will instantly have a supplier
  // list and inventory attached to it
  public class ProductSupplier {
    public Product product;
    public List<SupplierQuantity> supplierList;

    public ProductSupplier(String pName) {
      product = new Product(pName);
      supplierList = new LinkedList<SupplierQuantity>();
    }

    public ProductSupplier(String pName, String pDescription) {
      product = new Product(pName, pDescription);
      supplierList = new LinkedList<SupplierQuantity>();
    }
  }

  public class SupplierQuantity {
    public Supplier supplier;
    public int quantity;
    public float price;

    public SupplierQuantity(String sName, int sID) {
      quantity = 0;
      supplier = new Supplier(sName, sID);
    }

    public SupplierQuantity(String sName, int sID, int qty) {
      quantity = qty;
      supplier = new Supplier(sName, sID);
    }
  }

  // Create the class SupplierProduct, which contains the class ProductQuantity
  // this is done so that every product we create will instantly have a supplier
  // list and inventory attached to it
  public class SupplierProduct {
    public Supplier supplier;
    public List<ProductQuantity> productList;

    public SupplierProduct(String sName, int sId) {
      supplier = new Supplier(sName, sId);
      productList = new LinkedList<ProductQuantity>();
    }
  }

  public class ProductQuantity {
    public Product product;
    public int quantity;

    public ProductQuantity(String pName, int qty) {
      product = new Product(pName);
      quantity = qty;
    }

    public ProductQuantity(String pName, String desc, int qty) {
      product = new Product(pName, desc);
      quantity = qty;
    }

    public ProductQuantity(String pName) {
      product = new Product(pName);
      quantity = 0;
    }

    public ProductQuantity(String pName, String desc) {
      product = new Product(pName, desc);
      quantity = 0;
    }
  }

  // Create the class ClientBalance, which maintains the running balance for the
  // client
  public class ClientBalance {
    public Client client;
    public float credit;

    public ClientBalance(String cName, String cAddress, int cID) {
      client = new Client(cName, cAddress, cID);
      credit = 0;
    }
  }
}
