import java.io.*;
import java.util.*;

public class Warehouse {

  private List<ClientBalance> clientList;
  private List<ProductSupplier> productList;
  private List<SupplierProduct> supplierList;
  private int clientCount;
  private int productCount;
  private int supplierCount;

  public Warehouse() {
    clientList = new LinkedList<ClientBalance>();
    productList = new LinkedList<ProductSupplier>();
    supplierList = new LinkedList<SupplierProduct>();
    clientCount = 0;
    productCount = 0;
    supplierCount = 0;
  }

  public float makePayment(int cid, float amount) {
    ClientBalance c;
    Iterator iterator;
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
        input.readObject();
        return;
      } else {
        input.defaultReadObject();
        clientList = (List<Warehouse.ClientBalance>) input.readObject();
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

  public boolean isProduct(int pId) { // does the product exist?
    Iterator iterator;
    ProductSupplier p;
    for (iterator = productList.iterator(); iterator.hasNext();) {
      p = (ProductSupplier) iterator.next();
      if (p.product.getPID() == pId)
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

  public boolean addSupplier(String sName) { // true means was not already in list
    supplierList.add(new SupplierProduct(sName));
    return true;
  }

  public boolean removeSupplier(int sId) { // true means was in list and was removed;
    Iterator iterator1;
    Iterator iterator2;
    Iterator<SupplierProduct> spIt;
    SupplierProduct sp;
    SupplierProduct s;
    ProductSupplier p;
    SupplierQuantity sq;
    for (iterator1 = productList.iterator(); iterator1.hasNext();) { // go through all products and remove the supplier
      p = (ProductSupplier) iterator1.next();
      for (iterator2 = p.pSupplierList.iterator(); iterator1.hasNext();) {
        sq = (SupplierQuantity) iterator2.next();
        spIt = sq.supplier;
        sp = (SupplierProduct) spIt;
        if (sp.supplier.getSId() == sId) {
          iterator2.remove();
        }
      }
    }
    for (iterator1 = supplierList.iterator(); iterator1.hasNext();) { // remove supplier from supplierList
      s = (SupplierProduct) iterator1.next();
      if (s.supplier.getSId() == sId) {
        iterator1.remove();
        return true;
      }
    }
    return false;
  }

  public int numSuppliers(int pId) { // returns number of suppliers for the given product
    return supplierList.size();
  }

  public float getPrice(int pId, int sId) { // returns 0 if not found
    
  }

  public boolean setPrice(int pId, int sId, float newPrice) { // returns false if not found
    
  }

  public boolean setDescription(int pId, int sId, String newDescription) { // sets a product description
    
  }

  public String getDescription(int pId) {
    
  }

  public boolean addProduct(int pId) { // adds a product the the product list
    
  }

  public boolean addProduct(int pId, String pDescription) { // adds a product to the product list
    
  }

  public boolean removeProduct(int pId, int sId) { // removes a product from the product list
    
  }

  public int getStock(int sId, int pId) { // returns -9999999 if supplier does not exist
    
  }

  public boolean addToStock(int sId, int pId, int amtToAdd) { // adds an amount of product to stock for a
                                                                   // given supplier
    
  }

  public boolean removeFromStock(int sId, int pId, int amtToRemove) { // adds an amount of product to stock
                                                                           // for a given supplier
   
  }

  public boolean setStock(int sId, int pId, int amtToSet) { // sets the stock for a product from a supplier
    
  }

  public boolean addToWaitlist(Client client, int pId, int sId, int qty) { // adds clients to a suppliers product
    
  }

  public boolean removeFromWaitlist(int clientID, int pId, int sId) {// removes clients from a suppliers
    
  }

  public int popWaitlist(int sId, int pID) { // returns -1 if product not found -2 if supplier not found //
                                                  // otherwise returns client ID
    
  }

  public void fulfillWaitlist(int sId, int pId, int qty) {
    
  }

  public String generateInvoice(int clientID) { // should only be called after the last item is in the cart.
    
  }

  public boolean addClient(String cName, String cAddress) {
    clientList.add(new ClientBalance(cName, cAddress));
    return true;
  }

  public Iterator<SupplierProduct> findSupplier(int sID){
    Iterator<SupplierProduct> supIt = supplierList.iterator();
    SupplierProduct sp;
    for (; supIt.hasNext();){
      sp = (SupplierProduct) supIt.next();
      if(sp.supplier.getSId() == sID){
        return supIt;
      }
    }
    return null;
  }
  public Iterator<ProductSupplier> findProduct(int pID){
    Iterator<ProductSupplier> prodIt = productList.iterator();
    ProductSupplier ps;
    for (; prodIt.hasNext();){
      ps = (ProductSupplier) prodIt.next();
      if(ps.product.getPID() == pID){
        return prodIt;
      }
    }
    return null;
  }
  public Iterator<ClientBalance> findClient(int cID){
    Iterator<ClientBalance> cIt = clientList.iterator();
    ClientBalance cb;
    for (; cIt.hasNext();){
      cb = (ClientBalance) cIt.next();
      if(cb.client.getcId() == cID){
        return cIt;
      }
    }
    return null;
  }

  public boolean removeClient(int cID) {
    Iterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){
      return false;
    }
    cbIt.remove();
    return true;
  }

  // Create the class ProductSupplier, which contains the class SupplierQuantity
  // this is done so that every product we create will instantly have a supplier
  // list and inventory attached to it
  public class ProductSupplier {
    public Product product;
    public List<SupplierQuantity> pSupplierList;

    public ProductSupplier(String pName) {
      product = new Product(pName, productCount);
      productCount++;
      pSupplierList = new LinkedList<SupplierQuantity>();
    }

    public ProductSupplier(String pName, String pDescription) {
      product = new Product(pName, pDescription,productCount);
      productCount++;
      pSupplierList = new LinkedList<SupplierQuantity>();
    }
    public Iterator<SupplierQuantity> findSupplierQuantity(int sID){
      Iterator<SupplierQuantity> sqIt;
      Iterator<SupplierProduct> spIt;
      SupplierProduct sp;
      SupplierQuantity sq;
      for (sqIt = pSupplierList.iterator(); sqIt.hasNext();) {
        sq = (SupplierQuantity) sqIt.next();
        spIt = sq.supplier;
        sp = (SupplierProduct) spIt;
        if(sp.supplier.getSId() == sID){
          return sqIt;
        }
      }
      return null;
    }
  }


  public class SupplierQuantity {
    public Iterator<SupplierProduct> supplier;
    public int quantity;
    public float price;
    public Queue<ClientWLNode> clientWL;

    public SupplierQuantity(String sName, int sID) {
      quantity = 0;
      supplier = findSupplier(sID);
      clientWL = new LinkedList<>();
    }

    public SupplierQuantity(String sName, int sID, int qty) {
      quantity = qty;
      supplier = findSupplier(sID);
    }
  }

  public class ClientWLNode {
    public Iterator<ClientBalance> client;
    public int quantity;

    public ClientWLNode(int cID, int qty) {
      client = findClient(cID);
      quantity = qty;
    }
  }

  // Create the class SupplierProduct, which contains the class ProductQuantity
  // this is done so that every product we create will instantly have a supplier
  // list and inventory attached to it
  public class SupplierProduct {
    public Supplier supplier;
    public List<ProductQuantity> sProductList;

    public SupplierProduct(String sName) {
      supplier = new Supplier(sName, supplierCount);
      supplierCount++;
      sProductList = new LinkedList<ProductQuantity>();
    }
    public Iterator<ProductQuantity> findProductQuantity(int pID){
      Iterator<ProductQuantity> pqIt;
      Iterator<ProductSupplier> psIt;
      ProductSupplier ps;
      ProductQuantity pq;
      for (pqIt = sProductList.iterator(); pqIt.hasNext();) {
        pq = (ProductQuantity) pqIt.next();
        psIt = pq.product;
        ps = (ProductSupplier) psIt;
        if(ps.product.getPID() == pID){
          return pqIt;
        }
      }
      return null;
    }
  }

  public class ProductQuantity {
    public Iterator<ProductSupplier> product;
    public int quantity;

    public ProductQuantity(int pID, int qty) {
      product = findProduct(pID);
      quantity = qty;
    }

    public ProductQuantity(int pID) {
      product = findProduct(pID);
      quantity = 0;
    }
  }

  // Create the class ClientBalance, which maintains the running balance for the
  // client
  public class ClientBalance {
    public Client client;
    public float credit;
    public ShoppingCart cart;
    private LinkedList<Invoice> invoices;

    public ClientBalance(String cName, String cAddress) {
      client = new Client(cName, cAddress, clientCount);
      clientCount++;
      credit = 0;
      cart = new ShoppingCart();
    }

    public void addItem(int pID, int qty, int sID) {
      cart.addItem(pID, qty, sID);
      return;
    }

    public boolean removeItem(int pID, int qty, int sID) {
      return cart.removeItem(pID, qty, sID);
    }

    public Iterator<ItemQty> findItem(int pId, int sID) {
      return cart.findItem(pId, sID);
    }

    public int showQuantity(int pID, int sID) {
      return cart.showQuantity(pID, sID);
    }

    public boolean setItemQty(int pId, int qty, int sID) {
      return cart.setItemQty(pId, qty, sID);
    }

    public float getCartPrice() {
      float price = 0;
    }

    public boolean invoiceExists(int oID) {
      
    }

    public Iterator getCartIterator() {
      return cart.getCartIterator();
    }
  }
  public class ShoppingCart {
    private LinkedList<ItemQty> items;
    private int orderID;
    private LinkedList<Invoice> invoices;
    public ShoppingCart() {
      items = new LinkedList<ItemQty>();
      orderID = 0;
    }
    public void generateInvoice() { // will update the invoice for the current shopping cart
      Iterator<Invoice> invIt = findInvoice(orderID);
      Invoice inv = (Invoice) invIt;
      if(inv != null){
        invIt.remove();
      }
      inv = new Invoice(orderID, toString());
    }
    public Iterator<Invoice> findInvoice(int oID){
      Iterator<Invoice> invIt = invoices.iterator();
      Invoice inv;
      for (; invIt.hasNext();){
        inv = (Invoice) invIt.next();
        if(inv.orderID == oID){
          return invIt;
        }
      }
      return null;
    }

    public String viewInvoice(int oID){
      Iterator<Invoice> invIt = findInvoice(oID);
      Invoice inv = (Invoice) invIt;
      return inv.orderDetails;
    }

    public boolean addItem(int pID, int qty, int sID) {
      
    }
  
    public boolean removeItem(int pID, int qty, int sID) {
      
    }
  
    public Iterator<ItemQty> findItem(int pID, int sID) {

    }
  
    public int showQuantity(int pID, int sID) {
      
    }
  
    public boolean setItemQty(int pID, int qty, int sID) {
      
    }
  
    public int getOrderID() {
      return orderID;
    }
  
    public Iterator<ItemQty> getCartIterator() {
      return items.iterator();
    }
    public String toString(){
      Iterator<ItemQty> iqIt = items.iterator();
      ItemQty iq;
      String s = "Order ID: " + orderID + "\n";
      for(;iqIt.hasNext();){
        iq = (ItemQty) iqIt.next();
        s += iq.toString();
      }
      return s;
    }

    public class Invoice {
      int orderID;
      String orderDetails;

      public Invoice(int orderID, String contents) {
        this.orderDetails = contents;
        this.orderID = orderID;
      }
    }
  
  }
  
  class ItemQty implements Serializable {
    private Iterator<ProductSupplier> product;
    private int qty;
    private Iterator<SupplierQuantity> supplier;
  
    public ItemQty(int pID, int quantity, int sID) {
      product = findProduct(pID);
      ProductSupplier ps = (ProductSupplier) product;
      supplier = ps.findSupplierQuantity(sID);
    }
    public int getPID(){
      ProductSupplier ps = (ProductSupplier) product;
      return ps.product.getPID();
    }
    public String getPName() {
      ProductSupplier ps = (ProductSupplier) product;
      return ps.product.getPName();
    }  
    public int getSID(){
      SupplierQuantity sq = (SupplierQuantity) supplier;
      SupplierProduct sup = (SupplierProduct) sq.supplier;
      return sup.supplier.getSId();
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
  
    public String toString() {
      return ("item: " + getPName() + " quantity " + qty + " supplier: " + getSID() + '\n');
    }
  }
}
