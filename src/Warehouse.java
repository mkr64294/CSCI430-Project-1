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

  public float makePayment(int cid, float amount) { //returns remaining credit or -9999999 if cid not found
    ClientBalance c;
    ListIterator<ClientBalance> iterator;
    for (iterator = clientList.listIterator(); iterator.hasNext();) {
      c = (ClientBalance) iterator.next();
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

  public float addCredit(int cid, float amount) {

    ListIterator<ClientBalance> iterator;
    ClientBalance c;
    for (iterator = clientList.listIterator(); iterator.hasNext();){
      c = (ClientBalance) iterator.next();
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
    if (cbIt == null){return -1;}
    ClientBalance cb = (ClientBalance) cbIt;
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

  public ListIterator<ClientBalance> getClient() {
    return clientList.listIterator();
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

  public boolean addSupplierToProduct(int pID, int sID){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){return false;}
    SupplierProduct sp = (SupplierProduct) spIt;
    ps.pSupplierList.add(new SupplierQuantity(sp.supplier.getSName(), sID));
    sp.sProductList.add(new ProductQuantity(ps.product.getPID()));
    return true;
  }

  public String viewSupplierProducts(int sID){
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    SupplierProduct sp = (SupplierProduct) spIt;
    return sp.supplierProductListToString();
  }

  public boolean removeSupplier(int sID) { // true means was in list and was removed;
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    if(spIt == null){
      return false;
    }
    spIt.remove();
    return true;
  }

  public int numSuppliers(int pId) { // returns number of suppliers for the given product
    return supplierList.size();
  }

  public float getPrice(int pID, int sID) { // returns 0 if not found
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return 0;
    }
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return 0;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
    return sq.price;
  }

  public boolean setPrice(int pID, int sID, float newPrice) { // returns false if not found
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return false;
    }
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return false;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
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
    ProductSupplier ps = (ProductSupplier) psIt;
    ps.product.setDescription(newDescription);
    psIt.set(ps);
    return true;
  }

  public String getDescription(int pID) {
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return "not found";
    }
    ProductSupplier ps = (ProductSupplier) psIt;
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

  public int getStock(int sID, int pID) { // returns -9999999 if supplier does not exist
    ProductSupplier ps = (ProductSupplier) findProduct(pID);
    if(ps == null){
      return 0;
    }
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return 0;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
    return sq.quantity;
    
  }

  public boolean addToStock(int sID, int pID, int amtToAdd) { // adds an amount of product to stock for a given supplier
    ListIterator<SupplierProduct> spIt = findSupplier(sID);
    SupplierProduct sp = (SupplierProduct) spIt;
    if(sp == null){
      return false;
    }
    ListIterator<ProductQuantity> pqIt = sp.findProductQuantity(pID);
    if(pqIt == null){
      return false;
    }
    ProductQuantity pq = (ProductQuantity) pqIt;
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    ProductSupplier ps = (ProductSupplier) psIt;
    if(ps == null){
      return false;
    }
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return false;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
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
   SupplierProduct sp = (SupplierProduct) findSupplier(sID);
    if(sp == null){
      return false;
    }
    ListIterator<ProductQuantity> pqIt = sp.findProductQuantity(pID);
    if(pqIt == null){
      return false;
    }
    ProductQuantity pq = (ProductQuantity) pqIt;
    ProductSupplier ps = (ProductSupplier) findProduct(pID);
    if(ps == null){
      return false;
    }
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return false;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
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
    return true;
  }

  public boolean setStock(int sID, int pID, int amtToSet) { // sets the stock for a product from a supplier
    SupplierProduct sp = (SupplierProduct) findSupplier(sID);
    if(sp == null){
      return false;
    }
    ListIterator<ProductQuantity> pqIt = sp.findProductQuantity(pID);
    if(pqIt == null){
      return false;
    }
    ProductQuantity pq = (ProductQuantity) pqIt;
    ProductSupplier ps = (ProductSupplier) findProduct(pID);
    if(ps == null){
      return false;
    }
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return false;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
    sq.quantity = amtToSet;
    pq.quantity = amtToSet;
    sqIt.set(sq);
    pqIt.set(pq);
    return true;

  }

  public boolean addToWaitlist(int cID, int pID, int sID, int qty) { // adds clients to a suppliers product
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return false;
    }
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return false;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
    ClientWLNode cwln = new ClientWLNode(cID, qty);
    sq.clientWL.add(cwln);
    sqIt.set(sq);
    psIt.set(ps);
    return true;
  }

  /*public boolean removeFromWaitlist(int cID, int pID, int sID) {// removes clients from a suppliers

  }*/

  public int popWaitlist(int sID, int pID) { //returns client ID or 0 if not found
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return 0;
    }
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return 0;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
    ClientWLNode cwln = sq.clientWL.remove();
    return cwln.getcId();
  }

  public int fulfillWaitlist(int sID, int pID, int qty) { //returns the leftover not needed qty, -1 if not found
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){
      return -1;
    }
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){
      return -1;
    }
    SupplierQuantity sq = (SupplierQuantity) sqIt;
    ClientWLNode cwln;
    for(;(!(sq.clientWL.isEmpty()))||(qty == 0);){
      cwln = sq.clientWL.peek();
      if(cwln.quantity <= qty){
        qty -= cwln.quantity;
        sq.clientWL.remove();
      }
      else{
        sqIt.set(sq);
        psIt.set(ps);
        return qty;
      }
    }
    sqIt.set(sq);
    psIt.set(ps);
    return qty;
  }

  public void generateInvoice(int clientID) { // should only be called after the last item is in the cart.
    ListIterator<ClientBalance> cbIt = findClient(clientID);
    ClientBalance c = (ClientBalance) cbIt;
    c.cart.generateInvoice();
  }
  public String getInvoice(int clientID, int oID) { // should only be called after the last item is in the cart.
    ListIterator<ClientBalance> cbIt = findClient(clientID);
    ClientBalance c = (ClientBalance) cbIt;
    return c.cart.viewInvoice(oID);
  }

  public int addClient(String cName, String cAddress) {
    ClientBalance cb = new ClientBalance(cName, cAddress);
    clientList.add(cb);
    return cb.client.getcId();
  }

  public ListIterator<SupplierProduct> findSupplier(int sID){
    ListIterator<SupplierProduct> supIt = supplierList.listIterator();
    SupplierProduct sp;
    for (; supIt.hasNext();){
      sp = (SupplierProduct) supIt.next();
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
      ps = (ProductSupplier) prodIt.next();
      if(ps.product.getPID() == pID){
        return prodIt;
      }
    }
    return null;
  }
  public boolean isSupplierOfProduct(int sID, int pID){
    ListIterator<ProductSupplier> psIt = findProduct(pID);
    if(psIt == null){return false;}
    ProductSupplier ps = (ProductSupplier) psIt;
    ListIterator<SupplierQuantity> sqIt = ps.findSupplierQuantity(sID);
    if(sqIt == null){return false;}
    return true;
  }
  public ListIterator<ClientBalance> findClient(int cID){
    ListIterator<ClientBalance> cIt = clientList.listIterator();
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
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){
      return false;
    }
    cbIt.remove();
    return true;
  }

  public boolean addToCart(int cID, int pID, int qty, int sID){
    boolean bool;
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    ClientBalance cb = (ClientBalance) cbIt;
    bool = cb.addToCart(pID, qty, sID);
    cbIt.set(cb);
    return bool;
  }
  public boolean removeFromCart(int cID, int pID, int qty, int sID){
    boolean bool;
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    ClientBalance cb = (ClientBalance) cbIt;
    bool = cb.removeFromCart(pID, qty, sID);
    cbIt.set(cb);
    return bool;
  }
  public boolean isInCart(int cID, int pID, int sID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    ClientBalance cb = (ClientBalance) cbIt;
    return cb.isInCart(pID, sID);
  }
  public boolean clearCart(int cID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return false;}
    ClientBalance cb = (ClientBalance) cbIt;
    cb.clearCart();
    cbIt.set(cb);
    return true;
  }
  public int getCartQuantity(int cID, int pID, int sID){ // returns -1 if client not found
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return -1;}
    ClientBalance cb = (ClientBalance) cbIt;
    return cb.showItemQty(pID, sID);
  }
  public String showCart(int cID){
    ListIterator<ClientBalance> cbIt = findClient(cID);
    if(cbIt == null){return "not found";}
    ClientBalance cb = (ClientBalance) cbIt;
    return cb.cart.toString();
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
      ListIterator<SupplierQuantity> sqIt;
      ListIterator<SupplierProduct> spIt;
      SupplierProduct sp;
      SupplierQuantity sq;
      for (sqIt = pSupplierList.listIterator(); sqIt.hasNext();) {
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
    public ListIterator<SupplierProduct> supplier;
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
    public ListIterator<ClientBalance> client;
    public int quantity;

    public ClientWLNode(int cID, int qty) {
      client = findClient(cID);
      quantity = qty;
    }
    public int getcId(){
      ClientBalance cb = (ClientBalance) client;
      return cb.client.getcId();
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
      ListIterator<ProductQuantity> pqIt;
      ListIterator<ProductSupplier> psIt;
      ProductSupplier ps;
      ProductQuantity pq;
      for (pqIt = sProductList.listIterator(); pqIt.hasNext();) {
        pq = (ProductQuantity) pqIt.next();
        psIt = pq.product;
        ps = (ProductSupplier) psIt;
        if(ps.product.getPID() == pID){
          return pqIt;
        }
      }
      return null;
    }
    public String supplierProductListToString(){
      ListIterator<ProductQuantity> pqIt = sProductList.listIterator();
      ProductQuantity pq;
      ProductSupplier ps;
      String s = "Supplier: " + supplier.getSName() + " sID: " + supplier.getSId() + '\n';
      while(pqIt.hasNext()){
        pq = (ProductQuantity) pqIt.next();
        ps = (ProductSupplier) pq.product;
        s += "Product: " + ps.product.getPName() + " pID " + ps.product.getPID() + '\n';
      }
      return s;
    }
  }

  public class ProductQuantity {
    public ListIterator<ProductSupplier> product;
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

    public ClientBalance(String cName, String cAddress) {
      client = new Client(cName, cAddress, clientCount);
      clientCount++;
      credit = 0;
      cart = new ShoppingCart();
    }

    public boolean removeFromCart(int pID, int qty, int sID) {
      return cart.removeItem(pID, qty, sID);
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
        Invoice inv = (Invoice) invIt;
        if(inv != null){
          invIt.remove();
        }
        inv = new Invoice(orderID, toString());
      }
      public void clear(){
        items.clear();
      }
      public float getCartPrice(){
        ListIterator<ItemQty> itemIt = items.listIterator();
        ItemQty iq;
        float total = 0;
        SupplierQuantity sq;
        for(;itemIt.hasNext();){
          iq = (ItemQty) itemIt.next();
          sq = (SupplierQuantity) iq.supplier;
          total += (((float)iq.qty) * sq.price);
        }
        return total;
      }
      public ListIterator<Invoice> findInvoice(int oID){
        ListIterator<Invoice> invIt = invoices.listIterator();
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
        ListIterator<Invoice> invIt = findInvoice(oID);
        Invoice inv = (Invoice) invIt;
        return inv.orderDetails;
      }
  
      public boolean addItem(int pID, int qty, int sID) {
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        ItemQty iq;
        if(iqIt != null){
          iq = (ItemQty) iqIt;
          iq.qty +=qty;
          iqIt.set(iq);
          return true;
        }
        if(isSupplierOfProduct(sID, pID)){
          iq = new ItemQty(pID, qty, sID);
          cart.items.add(iq);
          return true;
        }
        return false;
        
        
      }
    
      public boolean removeItem(int pID, int qty, int sID) {
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        ItemQty iq = (ItemQty) iqIt;
        if((iqIt == null)){
          return false;
        }
        else if(iq.qty > qty){
          cart.addItem(pID, (iq.qty - qty), sID);
        }
        iqIt.remove();
        return true;
      }
        
      public int showItemQty(int pID, int sID) {
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        ItemQty iq = (ItemQty) iqIt;
        if(iqIt == null){
          return 0;
        }
        return iq.qty;
      }
    
      public boolean setItemQty(int pID, int qty, int sID) {
        ListIterator<ItemQty> iqIt = searchCart(pID, sID);
        if(iqIt == null){
          return false;
        }
        cart.addItem(pID, qty, sID);
        iqIt.remove();
        return true;
      }

      public ListIterator<ItemQty> searchCart(int pID, int sID) {
        ListIterator<ItemQty> iqIt = items.listIterator();
        ItemQty iq;
        ProductSupplier ps;
        SupplierQuantity sq;
        SupplierProduct sp;
        for (; iqIt.hasNext();){
          iq = (ItemQty) iqIt.next();
          ps = (ProductSupplier) iq.product;
          sq = (SupplierQuantity) iq.supplier;
          sp = (SupplierProduct) sq.supplier;
          if((ps.product.getPID() == pID) && (sp.supplier.getSId() == sID)){
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
    
      public ListIterator<ItemQty> getCartIterator() {
        return items.listIterator();
      }
      public String toString(){
        ListIterator<ItemQty> iqIt = items.listIterator();
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
      
      private static final long serialVersionUID = 1L;
      private ListIterator<ProductSupplier> product;
      private int qty;
      private ListIterator<SupplierQuantity> supplier;
    
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
        return ("item: " + getPName() + " quantity: " + qty + " supplier: " + getSID() + '\n');
      }
    }
  }
}
