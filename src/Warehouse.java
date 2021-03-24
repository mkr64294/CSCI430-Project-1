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
    clientCount = 1;
    productCount = 1;
    supplierCount = 1;
  }

  public float makePayment(int cid, float amount) { //returns remaining credit or -9999999 if cid not found
    ClientBalance c;
    ListIterator<ClientBalance> iterator;
    for (iterator = clientList.listIterator(); iterator.hasNext();) {
      c = iterator.next();
      if (c.client.getcId() == cid){
        if(c.credit >= amount){
          c.credit -= amount;
          iterator.set(c);
          return c.credit;
        }
        else{
          c.credit = 0;
          iterator.set(c);
          return 0;
        }
      }
    }

    return -9999999;
  }

  public float addCredit(int cid, float amount) { //returns the new credit amount

    ListIterator<ClientBalance> iterator;
    ClientBalance c;
    for (iterator = clientList.listIterator(); iterator.hasNext();){
      c = iterator.next();
      if (c.client.getcId() == cid){
        c.credit += amount;
        iterator.set(c);
        return c.credit;
      }
    }
    return -9999;
  }
  public float getCredit(int cID){ //return -1 if client not found
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if (cbIt == null){return -1;}cbIt.previous();
    ClientBalance cb = cbIt.next();
    return cb.credit;
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

  public String clientsToString() {
    return clientList.toString();
  }

  public boolean isProduct(int pID) { // does the product exist?
    ListIterator<ProductSupplier> iterator = findProduct(pID);
    if(iterator == null){
      return false;
    }
    return true;
  }

  public boolean isClient(int cID) { // does the product exist?
    ListIterator<ClientBalance> iterator = findClient(cID);
    if(iterator == null){
      return false;
    }
    return true;
  }

  public boolean isSupplier(int sID) { // does the supplier exist?
    ListIterator<SupplierProduct> iterator = findSupplier(sID);
    if(iterator == null){
      return false;
    }
    return true;
  }

  public int addSupplier(String sName) { // true means was not already in list
    SupplierProduct sp = new SupplierProduct(sName);
    supplierList.add(sp);
    return sp.supplier.getSId();
  }
  public boolean addSupplierToProduct(int pID, int sID, int amount){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    spIt.previous();
    SupplierProduct sp = spIt.next();

    ps.pSupplierList.add(new SupplierQuantity(sID,amount));
    sp.sProductList.add(new ProductQuantity(pID,amount));
    psIt.set(ps);
    spIt.set(sp);
    return true;
  }
  public boolean addSupplierToProduct(int pID, int sID){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    spIt.previous();
    SupplierProduct sp = spIt.next();

    ps.pSupplierList.add(new SupplierQuantity(sID));
    sp.sProductList.add(new ProductQuantity(pID));
    psIt.set(ps);
    spIt.set(sp);
    return true;
  }

  public String viewSupplierProducts(int sID){
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return "not found";}
    spIt.previous();
    SupplierProduct sp = spIt.next();

    return sp.supplierProductListToString();
  }

  public boolean removeSupplier(int sID) { // true means was in list and was removed;
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    
    spIt.remove();
    return true;
  }

  public int numSuppliers(int pId) { // returns number of suppliers for the given product
    return supplierList.size();
  }

  public float getPrice(int pID, int sID) { // returns 0 if not found
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return 0;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return 0;}
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();
    
    return sq.price;
  }

  public boolean setPrice(int pID, int sID, float newPrice) { // returns false if not found
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return false;}
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();

    sq.price = newPrice;
    sqIt.set(sq);
    psIt.set(ps);
    return true;
  }

  public boolean setDescription(int pID, String newDescription) { // sets a product description
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return false;
    }
    psIt.previous();
    ProductSupplier ps = psIt.next();
    ps.product.setDescription(newDescription);
    psIt.set(ps);
    return true;
  }

  public String getDescription(int pID) {
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return "not found";
    }
    psIt.previous();
    ProductSupplier ps = psIt.next();
    return ps.product.getDescription();
  }

  public int addProduct(String pName) { // adds a product the the product list
    ProductSupplier ps = new ProductSupplier(pName);
    productList.add(ps);
    return ps.product.getPID();
  }

  public int addProduct(String pName, String pDescription) { // adds a product to the product list
    ProductSupplier ps = new ProductSupplier(pName, pDescription);
    productList.add(ps);
    return ps.product.getPID();
  }

  public boolean removeProduct(int pID) { // removes a product from the product list
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return false;
    }
    psIt.remove();
    return true;
  }

  public int getStock(int sID, int pID) { // returns -9999999 if supplier does not exist or if supplier does not supply the product
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return -9999999;}
    psIt.previous();
    ProductSupplier ps = psIt.next();
  
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return -9999999;}
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();

    return sq.quantity;
    
  }

  public boolean addToStock(int sID, int pID, int amtToAdd) { // adds an amount of product to stock for a given supplier
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    spIt.previous();
    SupplierProduct sp = spIt.next();

    ListIterator<ProductQuantity> pqIt = sp.findProductQuantity(pID);
    if(pqIt == null){return false;}
    pqIt.previous();
    ProductQuantity pq = pqIt.next();

    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return false;}
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();
    
    sq.quantity += amtToAdd;
    pq.quantity += amtToAdd;
    sqIt.set(sq);
    pqIt.set(pq);
    spIt.set(sp);
    psIt.set(ps);
    return true;
  }

  public boolean removeFromStock(int sID, int pID, int amtToRemove) { // adds an amount of product to stock
                                                                           // for a given supplier
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    spIt.previous();
    SupplierProduct sp = spIt.next();

    ListIterator<ProductQuantity> pqIt = sp.findProductQuantity(pID);
    if(pqIt == null){return false;}
    pqIt.previous();
    ProductQuantity pq = pqIt.next();

    ListIterator<ProductSupplier> psIt =  findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();
    
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return false;}
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();

    if(sq.quantity < amtToRemove){
      sq.quantity = 0;
      pq.quantity = 0;
    }
    else{
      sq.quantity -= amtToRemove;
      pq.quantity -= amtToRemove;
    }
    sqIt.set(sq);
    pqIt.set(pq);
    spIt.set(sp);
    psIt.set(ps);
    return true;
  }

  public boolean setStock(int sID, int pID, int amtToSet) { // sets the stock for a product from a supplier
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    spIt.previous();
    SupplierProduct sp = spIt.next();

    ListIterator<ProductQuantity> pqIt = sp.findProductQuantity(pID);
    if(pqIt == null){return false;}
    pqIt.previous();
    ProductQuantity pq = pqIt.next();

    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return false;}
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();

    sq.quantity = amtToSet;
    pq.quantity = amtToSet;
    sqIt.set(sq);
    pqIt.set(pq);
    spIt.set(sp);
    psIt.set(ps);
    return true;

  }

  public boolean addToWaitlist(int cID, int pID, int sID, int qty) { // adds clients to a suppliers product
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance c = cbIt.next();
    boolean flag;
    flag = c.addToClientWaitlist(pID, qty, sID);
    if(flag == false){return false;}
    cbIt.remove();
    clientList.add(c);
    return true;
  }

  public boolean removeFromWaitlist(int cID, int pID, int sID, int qty) {// removes clients from a suppliers
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance c = cbIt.next();
    boolean flag;
    flag = c.removeFromClientWaitlist(pID,qty,sID);
    if(flag == false){return false;}
    cbIt.remove();
    clientList.add(c);
    return true;
  }

  public boolean generateInvoice(int clientID) { // should only be called after the last item is in the cart.
    ListIterator<ClientBalance> cbIt = findClient(clientID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance c = cbIt.next();
    c.cart.generateInvoice();
    cbIt.set(c);
    return true;
  }
  public String getInvoice(int clientID, int oID) { // should only be called after the last item is in the cart.
    ListIterator<ClientBalance> cbIt = findClient(clientID);
    if(cbIt == null){
      return "not found";
    }
    cbIt.previous();
    ClientBalance c = cbIt.next();
    return c.cart.viewInvoice(oID);
  }

  public int addClient(String cName, String cAddress) { //returns the cID
    ClientBalance cb = new ClientBalance(cName, cAddress);
    clientList.add(cb);
    return cb.client.getcId();
  }

  public ListIterator<SupplierProduct> findSupplier(int sID){
    ListIterator<SupplierProduct> supIt = supplierList.listIterator();
    SupplierProduct sp;
    for (; supIt.hasNext();){
      sp = supIt.next();
      if(sp.supplier.getSId() == sID){
        return supIt;
      }
    }
    return null;
  }
  public ListIterator<ProductSupplier> findProduct(int pID){
    ListIterator<ProductSupplier> prodIt = productList.listIterator();
    ProductSupplier ps;
    for (; prodIt.hasNext();){
      ps = prodIt.next();
      if(ps.product.getPID() == pID){
        return prodIt;
      }
    }
    return null;
  }
  public boolean isSupplierOfProduct(int sID, int pID){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    psIt.previous();
    ProductSupplier ps = psIt.next();

    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return false;}
    return true;
  }
  public ListIterator<ClientBalance> findClient(int cID){
    ListIterator<ClientBalance> cIt = clientList.listIterator();
    ClientBalance cb;
    for (; cIt.hasNext();){
      cb = cIt.next();
      if(cb.client.getcId() == cID){
        return cIt;
      }
    }
    return null;
  }

  public boolean removeClient(int cID) {
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.remove();
    return true;
  }

  public boolean addToCart(int cID, int pID, int qty, int sID){
    boolean bool;
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance cb = cbIt.next();

    bool = cb.addToCart(pID, qty, sID);
    cbIt.set(cb);
    return bool;
  }
  public boolean removeFromCart(int cID, int pID, int qty, int sID){
    boolean bool;
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance cb = cbIt.next();

    bool = cb.removeFromCart(pID, qty, sID);
    cbIt.set(cb);
    return bool;
  }
  public boolean isInCart(int cID, int pID, int sID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance cb = cbIt.next();
    return cb.isInCart(pID, sID);
  }
  public boolean clearCart(int cID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    cbIt.previous();
    ClientBalance cb = cbIt.next();

    cb.clearCart();
    cbIt.set(cb);
    return true;
  }
  public int getCartQuantity(int cID, int pID, int sID){ // returns -1 if client not found
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return -1;}
    cbIt.previous();
    ClientBalance cb = cbIt.next();

    return cb.showItemQty(pID, sID);
  }
  public String showCart(int cID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return "not found";}
    cbIt.previous();
    ClientBalance cb = cbIt.next();

    return cb.cart.toString();
  }
  public String printClientWaitlist(int cID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){
      return "Client not found";
    }
    cbIt.previous();
    ClientBalance cb = cbIt.next();
    return cb.printWaitlist();
  }
  public String showClientDetails(int cID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){
      return "Client not found";
    }
    cbIt.previous();
    ClientBalance cb = cbIt.next();
    return "client ID: " + cb.client.getcId() + "\nClient Name: " + cb.client.getcName() + "\nClient Address: " + cb.client.getcAddress() + "\nClient Balance: " + cb.credit + '\n';
  }
  public String showProductList(){
    ListIterator<ProductSupplier> psIt = productList.listIterator();
    ProductSupplier ps;
    ListIterator<SupplierQuantity> sqIt;
    SupplierQuantity sq;
    String list = "Product List\n";
    while(psIt.hasNext()){
      ps = psIt.next();
      list += "Product ID: " + ps.product.getPID() + " Product Name: " + ps.product.getPName();
      for(sqIt = ps.pSupplierList.listIterator();sqIt.hasNext();){
        sq = sqIt.next();
        list += "\n" + sq.toString();
      }
      list += "\n\n";
    }
    return list;
  }
  public String showClientsWithCredit(){
    ListIterator<ClientBalance> cbIt = clientList.listIterator();
    ClientBalance cb;
    String out = "These Clients owe money: \n";
    while(cbIt.hasNext()){
      cb = cbIt.next();
      if(cb.credit > 0){
        out += cb.toString();
      }
    }
    return out;
  }
  public String showSupplierList(){
    ListIterator<SupplierProduct> spIt = supplierList.listIterator();
    SupplierProduct sp;
    String out = "Supplier List:\n";
    while(spIt.hasNext()){
      sp = spIt.next();
      out += sp.supplierToString();
    }
    return out;
  }
  public String showProductWaitlist(int pID, int sID){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return "product not found";
    }
    psIt.previous();
    ProductSupplier ps = psIt.next();
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return "supplier does not supply this product";
    }
    sqIt.previous();
    SupplierQuantity sq = sqIt.next();
    return sq.toString();
  }

  //Method will return the Suppliers of a certain product 
  public String showProductListForSupplier(int pID){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return "Product Not Found\n";
    }
    psIt.previous();
    ProductSupplier ps = psIt.next();
    ListIterator<SupplierQuantity> sqIt = ps.pSupplierList.listIterator();
    SupplierQuantity sq;
    String list = "Product List\n";
    while(sqIt.hasNext()){
      sq = sqIt.next();
      list += "\n" + sq.toString();
    }
    list += "\n\n";
    return list;
  }

  //Method will return the products of a certain supplier
  public String showSupplierListForProduct(int sID){
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){
      return "Supplier Not Found\n";
    }
    spIt.previous();
    SupplierProduct sp = spIt.next();
    ListIterator<ProductQuantity> pqIt = sp.sProductList.listIterator();
    ProductQuantity pq;
    String out = "Supplier ID: " + sp.supplier.getSName() + " Supplier Name: " + sp.supplier.getSName();
    ListIterator<ProductSupplier> psIt;
    ProductSupplier ps;
    ListIterator<SupplierQuantity> sqIt;
    SupplierQuantity sq;

    while(pqIt.hasNext()){
      pq = pqIt.next();
      psIt = findProduct(pq.pIDpq);
      psIt.previous();
      ps = psIt.next();
      sqIt = ps.pSupplierList.listIterator();
      while(sqIt.hasNext()){
        sq = sqIt.next();
        if(sq.getSID() == sID){
          out += "Product Name: " + ps.product.getPName() + " Product ID: " + ps.product.getPID() + " Price: " + sq.price +'\n';
        }
      }
    }
    out += "\n";
    return out;
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
      product = new Product(pName, pDescription, productCount);
      productCount++;
      pSupplierList = new LinkedList<SupplierQuantity>();
    }
    public ListIterator<SupplierQuantity> findSupplierQuantity(int sID){
      ListIterator<SupplierQuantity> sqIt = pSupplierList.listIterator();
      SupplierQuantity sq;
      while(sqIt.hasNext()){
        sq = sqIt.next();
        if(sq.getSID() == sID){
          return sqIt;
        }
      }    
      return null;
    }
  }


  public class SupplierQuantity {
    public int quantity;
    public float price;
    public LinkedList<ClientWLNode> clientWL;
    public int sIDsq;

    public SupplierQuantity(int sID) {
      quantity = 0;
      clientWL = new LinkedList<>();
      sIDsq = sID;
    }

    public SupplierQuantity(int sID, int qty) {
      quantity = qty;
      sIDsq = sID;
    }

    public int getSID(){
      return sIDsq;
    }
    public String getSName(){
      ListIterator<SupplierProduct> spIt = findSupplier(sIDsq);
      spIt.previous();
      SupplierProduct sp = spIt.next();
      return sp.supplier.getSName();
    }
    public ListIterator<ClientWLNode> findClientWLNode(int cID){
      ListIterator<ClientWLNode> cnIt = clientWL.listIterator();
      ClientWLNode cn;
      while(cnIt.hasNext()){
        cn=cnIt.next();
        if(cn.client == cID){
          return cnIt;
        }
      }
      return null;
    }
    public String toString(){
      return "Supplier Name: " + getSName() + " Supplier ID: " + sIDsq + " Quantity: " + quantity + " Price: " + price;
    }
    public String showWaitlist(){
      ListIterator<ClientWLNode> cnIt = clientWL.listIterator();
      ClientWLNode cn;
      String out = "Product Waitlist:\n";
      while(cnIt.hasNext()){
        cn = cnIt.next();
        out += cn.toString();
      }
      return out;
    }
  }

  public class ClientWLNode {
    public int client;
    public int quantity;

    public ClientWLNode(int cID, int qty) {
      client = cID;
      quantity = qty;
    }
    public String toString(){
      ListIterator<ClientBalance> cbIt = findClient(client);
      if(cbIt == null){
        return "Client no longer exists";
      }
      cbIt.previous();
      ClientBalance cb = cbIt.next();
      return "Client ID: " + client + " Client Name " + cb.client.getcName() + " Quantity " + quantity + '\n';
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
    public ListIterator<ProductQuantity> findProductQuantity(int pID){
      ListIterator<ProductQuantity> pqIt = sProductList.listIterator();
      ProductQuantity pq;
      while(pqIt.hasNext()){
        pq=pqIt.next();
        if(pq.getPID() == pID){
          return pqIt;
        }
      }
      return null;
    }
    public String supplierProductListToString(){
      ListIterator<ProductQuantity> pqIt = sProductList.listIterator();
      ProductQuantity pq;
      String s = "Supplier: " + supplier.getSName() + " sID: " + supplier.getSId() + '\n';
      while(pqIt.hasNext()){
        pq = pqIt.next();        
        s += "Product: " + pq.getPName() + " pID " + pq.getPID() + '\n';
      }
      return s;
    }
    public String supplierToString(){
      return "Supplier ID: " + supplier.getSId() + " SupplierName: " + supplier.getSName();
    }
  }

  public class ProductQuantity {
    public ListIterator<ProductSupplier> product;
    public int quantity;
    public int pIDpq;
    public String pNamepq;

    public ProductQuantity(int pID, int qty) {
      ListIterator<ProductSupplier> psIt = findProduct(pID);
      product = psIt;
      quantity = qty;
      pIDpq = pID;
      psIt.previous();
      pNamepq = psIt.next().product.getPName();
    }

    public ProductQuantity(int pID) {
      ListIterator<ProductSupplier> psIt = findProduct(pID);
      product = psIt;
      product.previous();
      pNamepq = product.next().product.getPName();
      quantity = 0;
      pIDpq=pID;
    }

    public int getPID(){
      return pIDpq;
    }
    public String getPName(){
      return pNamepq;
    }
  }

  // Create the class ClientBalance, which maintains the running balance for the
  // client
  public class ClientBalance {
    public Client client;
    public float credit;
    public ShoppingCart cart;
    public LinkedList<ItemQty> waitlist;

    public ClientBalance(String cName, String cAddress) {
      client = new Client(cName, cAddress, clientCount);
      clientCount++;
      credit = 0;
      cart = new ShoppingCart();
      waitlist = new LinkedList<ItemQty>();
    }
    public String printWaitlist(){
      Iterator<ItemQty> iqIt = waitlist.listIterator();
      ItemQty iq;
      String wl = "Waitlisted Items:\n";
      while(iqIt.hasNext()){
        iq = iqIt.next();
        wl += iq.toString();
      }
      return wl;
    }
    public boolean removeFromCart(int pID, int qty, int sID) {
      return cart.removeItem(pID, qty, sID);
    }
    public boolean addToClientWaitlist(int pID, int qty, int sID){
      ListIterator<ProductSupplier> psIt = findProduct(pID);
      if(psIt == null){return false;}
      psIt.previous();
      ProductSupplier ps = psIt.next();
      ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
      if(sqIt == null){return false;}
      sqIt.previous();
      SupplierQuantity sq = sqIt.next();
      sq.clientWL.add(new ClientWLNode(client.getcId(), qty));
      sqIt.remove();
      ps.pSupplierList.add(sq);
      psIt.remove();
      productList.add(ps);
      waitlist.add(new ItemQty(pID, qty, sID));
      return true;
    }
    public boolean removeFromClientWaitlist(int pID, int qty, int sID){
      ListIterator<ProductSupplier> psIt = findProduct(pID);
      if(psIt == null){return false;}
      psIt.previous();
      ProductSupplier ps = psIt.next();
      ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
      if(sqIt == null){return false;}
      sqIt.previous();
      SupplierQuantity sq = sqIt.next();
      ListIterator<ClientWLNode> cnIt = sq.findClientWLNode(client.getcId());
      if(cnIt == null){return false;}
      cnIt.previous();
      ClientWLNode cn = cnIt.next();
      if(cn.quantity > qty){
        cn.quantity -= qty;
        sq.clientWL.add(cn);
      }
      cnIt.remove();
      ps.pSupplierList.add(sq);
      sqIt.remove();
      productList.add(ps);
      psIt.remove();
      return true;
    }
    public ListIterator<ItemQty> findItemWaitlistPred(int pID, int sID){
      ListIterator<ItemQty> wlIt = waitlist.listIterator();
      ItemQty item;
      while(wlIt.hasNext()){
        item = wlIt.next();
        if(item.pIDiq == pID && item.sIDiq == sID){
          wlIt.previous();
          return wlIt;
        }
      }
      return null;
    }
    public ListIterator<ItemQty> searchCart(int pId, int sID) {
      return cart.searchCart(pId, sID);
    }

    public int showItemQty(int pID, int sID) {
      return cart.showItemQty(pID, sID);
    }

    public boolean setItemQty(int pId, int qty, int sID) {
      return cart.setItemQty(pId, qty, sID);
    }

    public float getCartPrice() {
      return cart.getCartPrice();
    }

    public boolean addToCart(int pID, int qty, int sID){
      return cart.addItem(pID, qty, sID);
    }
    public boolean isInCart(int pID, int sID){
      return cart.isInCart(pID, sID);
    }
    public void clearCart(){
      cart.clear();
    }
    public String toString(){
      return "client ID: " + client.getcId() + " Client Name: " + client.getcName() + " Client Address: " + client.getcAddress() + " Client Balance: " + credit + '\n';
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
        ListIterator<Invoice> invIt = findInvoice(orderID);
        if(invIt != null){
          invIt.remove();
        }
        Invoice inv =new Invoice(orderID, toString());
        invoices.add(inv);
      }
      public void clear(){
        items.clear();
      }
      public float getCartPrice(){
        ListIterator<ItemQty> itemIt = items.listIterator();
        ItemQty iq;
        float total = 0;
        for(;itemIt.hasNext();){
          iq = itemIt.next();
          total += (((float)iq.qty) * iq.getPrice());
        }
        return total;
      }
      public ListIterator<Invoice> findInvoice(int oID){
        ListIterator<Invoice> invIt = invoices.listIterator();
        Invoice inv;
        for (; invIt.hasNext();){
          inv = invIt.next();
          if(inv.orderID == oID){
            return invIt;
          }
        }
        return null;
      }
  
      public String viewInvoice(int oID){
        ListIterator<Invoice> invIt = findInvoice(oID);
        if(invIt == null){return "not found";}
        invIt.previous();
        Invoice inv = invIt.next();
        return inv.orderDetails;
      }
  
      public boolean addItem(int pID, int qty, int sID) {
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        ItemQty iq;
        if(iqIt != null){
          iqIt.previous();
          iq = iqIt.next();
          iq.qty +=qty;
          iqIt.set(iq);
          return true;
        }
        if(isSupplierOfProduct(sID, pID)){
          iq = new ItemQty(pID, qty, sID);
          items.add(iq);
          return true;
        }
        return false;
        
        
      }
    
      public boolean removeItem(int pID, int qty, int sID) {
        ListIterator<ItemQty> iqIt = items.listIterator();
        ItemQty iq;
        while(iqIt.hasNext()){
          iq = iqIt.next();
          if(iq.pIDiq == pID){
            if(iq.qty > qty){
              iq.qty -= qty;
              return true;
            }
            else{
              iqIt.remove();
              return true;
            }
          }
        }
        return false;
      }
        
      public int showItemQty(int pID, int sID) {
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        if(iqIt == null){return 0;}
        iqIt.previous();
        ItemQty iq = iqIt.next();
        return iq.qty;
      }
    
      public boolean setItemQty(int pID, int qty, int sID) { //item must already exist in cart
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        if(iqIt == null){return false;}
        iqIt.remove();
        cart.addItem(pID, qty, sID);
        return true;
      }

      public ListIterator<ItemQty> searchCart(int pID, int sID) {
        ListIterator<ItemQty> iqIt = cart.items.listIterator();
        ItemQty iq;
        while(iqIt.hasNext()){
          iq = iqIt.next();
          if(iq.getPID() == pID && iq.getSID() == sID){
            return iqIt;
          }
        }
        return null;

      }
      public boolean isInCart(int pID, int sID){
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        if(iqIt == null){return false;}
        return true;
      }
    
      public int getOrderID() {
        return orderID;
      }
    
      public String toString(){
        ListIterator<ItemQty> iqIt = items.listIterator();
        ItemQty iq;
        String s = "Order ID: " + orderID + "\n";
        for(;iqIt.hasNext();){
          iq = iqIt.next();
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
      
      private static final long serialVersionUID = 1L;
      private int qty;
      private int pIDiq;
      private int sIDiq;
    
      public ItemQty(int pID, int quantity, int sID) {
        ListIterator<ProductSupplier> product = findProduct(pID);
        product.previous();
        ProductSupplier ps = product.next();
        pIDiq = ps.product.getPID();
        ListIterator<SupplierQuantity> supplier = ps.findSupplierQuantity(sID);
        supplier.previous();
        SupplierQuantity sq = supplier.next();
        sIDiq = sq.getSID();
        qty = quantity;
      }
      public float getPrice(){
        ListIterator<ProductSupplier> product = findProduct(pIDiq);
        product.previous();
        ProductSupplier ps = product.next();
        ListIterator<SupplierQuantity> supplier = ps.findSupplierQuantity(sIDiq); 
        supplier.previous();
        return supplier.next().price;
      }
      public int getPID(){
        return pIDiq;
      }
      public String getPName() {
        ListIterator<ProductSupplier> psIt = findProduct(pIDiq);
        if(psIt == null){return "Not found";}
        psIt.previous();
        return psIt.next().product.getPName();
      }  
      public int getSID(){
        return sIDiq;
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
        return ("item: " + getPName() + " pID: " + pIDiq + " quantity: " + qty + " supplier: " + getSID() + '\n');
      }
    }
  }
}
