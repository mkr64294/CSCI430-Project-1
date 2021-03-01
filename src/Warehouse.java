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

  public boolean isProduct(String pName) { // does the product exist?
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPName().equals(pName))
        return true;
    }
    return false;
  }

  public boolean isSupplier(int sId) { // does the supplier exist?
    Iterator iterator;
    SupplierProduct s;
    for (iterator = supplierList.iterator(); iterator.hasNext();) {
      s = (SupplierProduct) iterator.next();
      if (s.supplier.getSId() == sId)
        return true;
    }
    return false;
  }

  public boolean addSupplier(int sId, String sName) { // true means was not already in list
    Iterator iterator;
    SupplierProduct s;
    for (iterator = supplierList.iterator(); iterator.hasNext();) {
      s = (SupplierProduct) iterator.next();
      if (s.supplier.getSId() == sId)
        return false;
    }
    supplierList.add(new SupplierProduct(sName, sId));
    return true;
  }

  public void removeSupplier(int sId) { // true means was in list and was removed;
    Iterator iterator1;
    Iterator iterator2;
    SupplierProduct s;
    ProductSupplier p;
    SupplierQuantity sq;
    for (iterator1 = supplierList.iterator(); iterator1.hasNext();) { //remove supplier from supplierList
      s = (SupplierProduct) iterator1.next();
      if (s.supplier.getSId() == sId)
        iterator1.remove();
    }
    for(iterator1 = productList.iterator(); iterator1.hasNext();) { //go through all products and remove the supplier
      p = (ProductSupplier) iterator1.next();
      for(iterator2 = p.pSupplierList.iterator(); iterator1.hasNext();){
        sq = (SupplierQuantity) iterator2.next();
        if(sq.supplier.getSId() == sId){
          iterator2.remove();
        }
      }
    }
    return;
  }

  public int numSuppliers(String pName) { // returns number of suppliers for the given product
    return supplierList.size();
  }

  public double getPrice(String pName, int sId) { // returns 0 if not found
    Iterator iterator1;
    Iterator iterator2;
    ProductSupplier p;
    SupplierQuantity sq;

    for(iterator1 = productList.iterator(); iterator1.hasNext();) { //go through all products and remove the supplier
      p = (ProductSupplier) iterator1.next();
      for(iterator2 = p.pSupplierList.iterator(); iterator1.hasNext();){
        sq = (SupplierQuantity) iterator2.next();
        if(sq.supplier.getSId() == sId){
          return sq.price;
        }
      }
    }
    return 0;
  }

  public boolean setPrice(String pName, int sId, float newPrice) { // returns 0 if not found
    Iterator iterator1;
    Iterator iterator2;
    ProductSupplier p;
    SupplierQuantity sq;

    for(iterator1 = productList.iterator(); iterator1.hasNext();) { //go through all products and remove the supplier
      p = (ProductSupplier) iterator1.next();
      for(iterator2 = p.pSupplierList.iterator(); iterator1.hasNext();){
        sq = (SupplierQuantity) iterator2.next();
        if(sq.supplier.getSId() == sId){
          sq.price = newPrice;
          return true;
        }
      }
    }
    return false;
  }

  public boolean setDescription(String pName, int sId, String newDescription) { // sets a product description
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    SupplierProduct s;
    ProductSupplier p;
    ProductQuantity pq;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = supplierList.iterator(); iterator2.hasNext();) {
          s = (SupplierProduct) iterator2.next();
          if (s.supplier.getSId() == sId) {
            for (iterator3 = s.sProductList.iterator(); iterator3.hasNext();) {
              pq = (ProductQuantity) iterator3.next();
              if (pq.product.getPName().equals(pName)) {
                p.product.setDescription(newDescription);
                pq.product.setDescription(newDescription);
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public String getDescription(String pName) {
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPName().equals(pName)) {
        return p.product.getDescription();
      }
    }
    return "Not Found";
  }

  public boolean addProduct(String pName, String sName, int sId) { // adds a product the the product list
    Iterator iterator1;
    ProductSupplier p;
    SupplierProduct s;

    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName))
        return false;
    }
    p = new ProductSupplier(pName);
    p.pSupplierList.add(new SupplierQuantity(sName, sId));
    productList.add(p);
    s = new SupplierProduct(sName, sId);
    s.sProductList.add(new ProductQuantity(pName));
    supplierList.add(s);
    return true;
  }

  public boolean addProduct(String pName, String sName, int sId, String pDescription) { // adds a product to the product
                                                                                        // list
    Iterator iterator1;
    ProductSupplier p;
    SupplierProduct s;

    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName))
        return false;
    }
    p = new ProductSupplier(pName, pDescription);
    p.pSupplierList.add(new SupplierQuantity(sName, sId));
    productList.add(p);
    s = new SupplierProduct(sName, sId);
    s.sProductList.add(new ProductQuantity(pName, pDescription));
    supplierList.add(s);
    return true;
  }

  public boolean removeProduct(String pName, int sId) { // removes a product from the product list
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    SupplierProduct s;
    ProductSupplier p;
    ProductQuantity pq;
    for (iterator1 = supplierList.iterator(); iterator1.hasNext();) {
      s = (SupplierProduct) iterator1.next();
      if (s.supplier.getSId() == sId) {
        for (iterator2 = s.sProductList.iterator(); iterator2.hasNext();) {
          pq = (ProductQuantity) iterator2.next();
          if (pq.product.getPName().equals(pName)) {
            iterator2.remove();
            break;
          }
        }
      }
    }
    for (iterator3 = productList.iterator(); iterator3.hasNext();) {
      p = (ProductSupplier) iterator3.next();
      if (p.product.getPName().equals(pName)) {
        iterator3.remove();
        return true;
      }
    }
    return false;
  }

  public int getStock(int sId, String pName) { // returns -9999999 if supplier does not exist
    Iterator iterator1;
    Iterator iterator2;
    ProductSupplier p;
    SupplierQuantity q;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = p.pSupplierList.iterator(); iterator2.hasNext();) {
          q = (SupplierQuantity) iterator2.next();
          if (q.supplier.getSId() == sId)
            return q.quantity;
        }
      }
    }
    return -9999999;
  }

  public boolean addToStock(int sId, String pName, int amtToAdd) { // adds an amount of product to stock for a
                                                                   // given supplier
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    Iterator iterator4;
    SupplierProduct s;
    ProductSupplier p;
    ProductQuantity pq;
    SupplierQuantity sq;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = supplierList.iterator(); iterator2.hasNext();) {
          s = (SupplierProduct) iterator2.next();
          if (s.supplier.getSId() == sId) {
            for (iterator3 = s.sProductList.iterator(); iterator3.hasNext();) {
              pq = (ProductQuantity) iterator3.next();
              if (pq.product.getPName().equals(pName)) {
                for (iterator4 = p.pSupplierList.iterator(); iterator4.hasNext();) {
                  sq = (SupplierQuantity) iterator4.next();
                  if (sq.supplier.getSId() == sId) {
                    sq.quantity += amtToAdd;
                    pq.quantity += amtToAdd;
                    return true;
                  }
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  public boolean removeFromStock(int sId, String pName, int amtToRemove) { // adds an amount of product to stock
                                                                           // for a given supplier
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    Iterator iterator4;
    SupplierProduct s;
    ProductSupplier p;
    ProductQuantity pq;
    SupplierQuantity sq;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = supplierList.iterator(); iterator2.hasNext();) {
          s = (SupplierProduct) iterator2.next();
          if (s.supplier.getSId() == sId) {
            for (iterator3 = s.sProductList.iterator(); iterator3.hasNext();) {
              pq = (ProductQuantity) iterator3.next();
              if (pq.product.getPName().equals(pName)) {
                for (iterator4 = p.pSupplierList.iterator(); iterator4.hasNext();) {
                  sq = (SupplierQuantity) iterator4.next();
                  if (sq.supplier.getSId() == sId) {
                    sq.quantity -= amtToRemove;
                    pq.quantity -= amtToRemove;
                    return true;
                  }
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  public boolean setStock(int sId, String pName, int amtToSet) { // sets the stock for a product from a supplier
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    Iterator iterator4;
    SupplierProduct s;
    ProductSupplier p;
    ProductQuantity pq;
    SupplierQuantity sq;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = supplierList.iterator(); iterator2.hasNext();) {
          s = (SupplierProduct) iterator2.next();
          if (s.supplier.getSId() == sId) {
            for (iterator3 = s.sProductList.iterator(); iterator3.hasNext();) {
              pq = (ProductQuantity) iterator3.next();
              if (pq.product.getPName().equals(pName)) {
                for (iterator4 = p.pSupplierList.iterator(); iterator4.hasNext();) {
                  sq = (SupplierQuantity) iterator4.next();
                  if (sq.supplier.getSId() == sId) {
                    sq.quantity = amtToSet;
                    pq.quantity = amtToSet;
                    return true;
                  }
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

 
  public boolean addToWaitlist(Client client, String pName, int sId, int qty) { //adds clients to a suppliers product 
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    ProductSupplier p;
    SupplierQuantity sq;
    ClientWLNode cwln;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = p.pSupplierList.iterator(); iterator2.hasNext();) {
          sq = (SupplierQuantity) iterator2.next();
          if (sq.supplier.getSId() == sId) {
            sq.clientWL.add(new ClientWLNode(client, qty));
          }
        }
      }
    }
    return false;
  }
    
  public boolean removeFromWaitlist(int clientID, String pName, int sId) {// removes clients from a suppliers 
    Iterator iterator1;
    Iterator iterator2;
    Iterator iterator3;
    ProductSupplier p;
    SupplierQuantity sq;
    ClientWLNode cwln;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = p.pSupplierList.iterator(); iterator2.hasNext();) {
          sq = (SupplierQuantity) iterator2.next();
          if (sq.supplier.getSId() == sId) {
            for (iterator3 = sq.clientWL.iterator(); iterator3.hasNext();) {
              cwln = (ClientWLNode) iterator3.next();
              if (cwln.client.getcId() == clientID) {
                iterator3.remove();
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }
    
  public int popWaitlist(int sId, String pName) { // returns -1 if product not found -2 if supplier not found // otherwise returns client ID
    Iterator iterator1;
    Iterator iterator2;
    ProductSupplier p;
    SupplierQuantity sq;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = p.pSupplierList.iterator(); iterator2.hasNext();) {
          sq = (SupplierQuantity) iterator2.next();
          if (sq.supplier.getSId() == sId) {
            return sq.clientWL.remove().client.getcId();
          }
        }
      }
    }
    return -999999;
  }
  public void fulfillWaitlist(int sId, String pName, int qty) { 
    Iterator iterator1;
    Iterator iterator2;
    ProductSupplier p;
    SupplierQuantity sq;
    ClientWLNode cwln;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) {
      p = (ProductSupplier) iterator1.next();
      if (p.product.getPName().equals(pName)) {
        for (iterator2 = p.pSupplierList.iterator(); iterator2.hasNext();) {
          sq = (SupplierQuantity) iterator2.next();
          if (sq.supplier.getSId() == sId) {
            while(sq.clientWL.peek().quantity < qty && !(sq.clientWL.isEmpty())){
              qty -= sq.clientWL.peek().quantity;
              sq.clientWL.remove();
            }
            if(sq.clientWL.peek().quantity == qty && !(sq.clientWL.isEmpty())){
              sq.clientWL.remove();
              return;
            }
            else if(sq.clientWL.peek().quantity > qty && !(sq.clientWL.isEmpty()){
              sq.clientWL.peek().quantity -= qty;
              return;
            }
            return;
          }
        }
      }
    }
    return;
  }
   

  // Create the class ProductSupplier, which contains the class SupplierQuantity
  // this is done so that every product we create will instantly have a supplier
  // list and inventory attached to it
  public class ProductSupplier {
    public Product product;
    public List<SupplierQuantity> pSupplierList;

    public ProductSupplier(String pName) {
      product = new Product(pName);
      pSupplierList = new LinkedList<SupplierQuantity>();
    }

    public ProductSupplier(String pName, String pDescription) {
      product = new Product(pName, pDescription);
      pSupplierList = new LinkedList<SupplierQuantity>();
    }
  }

  public class SupplierQuantity {
    public Supplier supplier;
    public int quantity;
    public float price;
    public Queue<ClientWLNode> clientWL;

    public SupplierQuantity(String sName, int sID) {
      quantity = 0;
      supplier = new Supplier(sName, sID);
      clientWL = new LinkedList<>();
    }

    public SupplierQuantity(String sName, int sID, int qty) {
      quantity = qty;
      supplier = new Supplier(sName, sID);
    }
  }

  public class ClientWLNode{
    public Client client;
    public int quantity;
    public ClientWLNode(Client c, int qty){
      client = c;
      quantity = qty;
    }
  }

  // Create the class SupplierProduct, which contains the class ProductQuantity
  // this is done so that every product we create will instantly have a supplier
  // list and inventory attached to it
  public class SupplierProduct {
    public Supplier supplier;
    public List<ProductQuantity> sProductList;

    public SupplierProduct(String sName, int sId) {
      supplier = new Supplier(sName, sId);
      sProductList = new LinkedList<ProductQuantity>();
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
