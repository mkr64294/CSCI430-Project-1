
/**
 Author: Mark Rice 
 Date: 2/28/2021 
 Class: CSCI430 
 Description: UI Class that runs all the functions from the client, product, and supplier classes written by the other member of my group
 Version 2.0: Began adding UI functionality by having the user select a choice from a menu to perform actions
 */

import java.util.*;

class UserInterface {

  private static Scanner scan = new Scanner(System.in);

  public static void main(String[] args) {

    // Initialize the Warehouse lists and the shopping cart
    Warehouse WH = new Warehouse();

    displayMenu();

    char entry = '0';

    while (entry != 'x' && entry != 'X') {

      entry = scan.next().charAt(0);

      if (entry == 'A' || entry == 'a') { // adding client

        System.out.print("What is the name of this client?  : ");
        scan.nextLine();
        String cName = scan.nextLine();

        System.out.print("What is this client's address?   : ");
        String cAddress = scan.nextLine();

        int cId = WH.addClient(cName, cAddress);
        System.out.println("Client "+ cName +" at address "+ cAddress +" sucessfully added.\nThis client has an ID of "+ cId +".\n");

      } else if (entry == 'B' || entry == 'b') { // adding product 
        System.out.print("What is this product called?  : ");
        scan.nextLine();
        String pName = scan.nextLine();

        System.out.print("Does this product have a description? (y/n)   : ");
        while (entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N') {
          entry = scan.next().charAt(0);
          if (entry == 'y' || entry == 'Y') {
            System.out.print("Please enter a description for this product.  : ");
            scan.nextLine();
            String pDescription = scan.nextLine();
            System.out.println("Product "+ pName +" sucessfully added.\nThis product has an ID of "+ WH.addProduct(pName, pDescription) +".\n");
          } else if (entry == 'n' || entry == 'N') {
            System.out.println("Product "+ pName +" sucessfully added.\nThis product has an ID of "+ WH.addProduct(pName) +".\n");
          } else {
            System.out.print("Your previous entry is invalid. \nDoes this product have a description? (y/n)   : ");
            entry = scan.next().charAt(0);
          }
        }

      } else if (entry == 'C' || entry == 'c') { // adding supplier

        System.out.print("What is the name of this supplier?  : ");
        scan.nextLine();
        String sName = scan.nextLine();
        int sId = WH.addSupplier(sName);
        System.out.println("Supplier "+ sName +" sucessfully added.\nThis supplier has an ID of "+ sId +".\n");

      } else if (entry == 'D' || entry == 'd') { // remove client

        System.out.print("What is the ID of this client?  : ");
        scan.nextLine();
        int cID = scan.nextInt();

        if (WH.removeClient(cID)) {
          System.out.println("Client sucessfully removed.\n");
        } else {
          System.out.println("Unable to remove client.\n");
        }

      } else if (entry == 'E' || entry == 'e') { // remove product

        System.out.print("What is the ID of this product?  : ");
        int pId = scan.nextInt();

        if (WH.removeProduct(pId)) {
          System.out.println("Product sucessfully removed.\n");
        } else {
          System.out.println("Unable to remove product.\n");
        }

      } else if (entry == 'F' || entry == 'f') { // remove supplier

        System.out.print("What is the ID of this supplier?  : ");
        scan.nextLine();
        int sId = scan.nextInt();

        if (WH.removeSupplier(sId)) {
          System.out.println("Supplier sucessfully removed.\n");
        } else {
          System.out.println("Unable to remove supplier.\n");
        }

      } else if (entry == 'G' || entry == 'g') { // Add Product To Supplier 
        
        System.out.print("What is the ID of this product?  : ");
        scan.nextLine();
        int pID = scan.nextInt();
        
        System.out.print("What is the ID of the supplier that will supply this product?  : ");
        int sID = scan.nextInt();

        if (WH.addSupplierToProduct(pID, sID)) {
          System.out.println("Product sucessfully added to this supplier.\n");

          System.out.print("What is the price of this product?  : $");
          float price = scan.nextFloat();

          if (WH.setPrice(pID, sID, price)){
            System.out.println("The price of this product has been set to"+ price +".\n");
          } else {
            System.out.println("Unable to assign a price to this product.\n");

        } else {
          System.out.println("Unable to add product to supplier.\n");
        }

      } else if (entry == 'H' || entry == 'h') { // Change price of product 

        System.out.print("What is the ID of this product?  : ");
        scan.nextLine();
        int pID = scan.nextInt();
        
        System.out.print("What is the ID of the supplier that supplies this product?  : ");
        int sID = scan.nextInt();

        if (WH.isSupplierOfProduct(sID, pID)) {
          System.out.print("The current price of this product is $" + WH.getPrice(int pID, int sID));
          System.out.print("What is the new price of this product?  : $");
          float price = scan.nextFloat();

          if (WH.setPrice(pID, sID, price)){
            System.out.println("The price of this product has been set to"+ price +".\n");
          } else {
            System.out.println("Unable to assign a price to this product.\n");

        } else {
          System.out.println("Unable to add product to supplier.\n");
        }

      } else if (entry == 'I' || entry == 'i') { // add to cart

        addToCartUI(scan, WH);

      } else if (entry == 'J' || entry == 'j') { // remove from cart

        removeFromCartUI(scane, WH);

      } else if (entry == 'K' || entry == 'k') { // accept payment from client

        

      } else if (entry == 'L' || entry == 'l') { // accept shipment from supplier
      
      
      } else if (entry == 'Z' || entry == 'z') {

        displayMenu();

      } else if (entry == 'X' || entry == 'x') {

        break;

      } else { // This is is any entry does not match one of these options

        System.out.println("This in not a valid selection. Please try again.\n");

      }
    }
  }

  private static void displayMenu() {
    System.out.println("MAIN MENU\n\nPlease select the action you would like to perform\n");
    System.out.println("Action                    Selection Key\n");
    System.out.println("Add Client                      A"); // written
    System.out.println("Add Product                     B"); // written
    System.out.println("Add Supplier                    C\n"); // written
    System.out.println("Remove Client                   D"); // written
    System.out.println("Remove Product                  E"); // written
    System.out.println("Remove Supplier                 F\n"); // written
    System.out.println("Add Product To Supplier         G"); // written
    System.out.println("Change Price Of Product         H\n"); // written
    System.out.println("Add To Cart                     I"); 
    System.out.println("Remove From Cart                J\n"); 
    System.out.println("Accept Payment From Client      K");
    System.out.println("Accept Shipment From Supplier   L\n");
    System.out.println("Redisplay Menu                  Z");
    System.out.println("Exit Warehouse                  X");
  }

  public static void addToCartUI(Scanner scan, Warehouse WH){
    System.out.print("What is your client ID?  : ");
    scan.nextLine();
    int cID = scan.nextInt();

    while(!WH.isClient(cID)){
      System.out.print("This client does not exist. Please enter a valid client ID or press 0 to cancel  : ");
      scan.nextLine();
      cID = scan.nextInt();
      if(cID == 0)
        return;
    }

    System.out.print("What is the ID of the product you're adding to your cart?  : ");
    scan.nextLine();
    int pID = scan.nextInt();

    while(!WH.isProduct(pID)){
      System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
      scan.nextLine();
      pID = scan.nextInt();
      if(pID == 0)
        return;
    }

    System.out.print("What is the ID of the supplier that supplies this product?  : ");
    scan.nextLine();
    String sID = scan.nextInt();

    while(!WH.isSupplierOfProduct(sID, pID)){
      System.out.print("This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : ");
      scan.nextLine();
      sID = scan.nextInt();
      if(sID == 0)
        return;
    }

    
  }

  public static void removeFromCartUI(Scanner scan, Warehouse WH){
    System.out.print("What is your client ID?  : ");
    scan.nextLine();
    int cID = scan.nextInt();

    if(!WH.isClient()){
      System.out.print("This client does not exist");
      return;
    }

    System.out.print("What is the ID of the product you're adding to your cart?  : ");
    scan.nextLine();
    String pID = scan.nextLine();

    if(!WH.isProduct()){
      System.out.print("This product does not exist");
      return;
    }

    System.out.print("What is the ID of the supplier that supplies this product?  : ");
    scan.nextLine();
    String pName = scan.nextLine();

    if(!WH.isProduct()){
      System.out.print("This product does not exist");
      return;
    }
  }
  
  public static void buyProduct(Scanner scan, Warehouse WH) {

    System.out.print("What is your client ID?  : ");
    scan.nextLine();
    int cId = scan.nextInt();

    System.out.print("What product would you like to buy?  : ");
    scan.nextLine();
    String pName = scan.nextLine();

    if (!WH.isProduct(pName)) {
      System.out.println("This product is not available\n");
      return;
    }

    System.out.print("What is the ID of the supplier would you like to buy from?  : ");
    int sId = scan.nextInt();

    if (WH.getStock(sId, pName) < 0) {
      System.out.println("This product is not available from this supplier\n");
      return;
    }

    int currentStock = WH.getStock(sId, pName);

    float price = WH.getPrice(pName, sId);

    System.out
        .println("This product has a cost of $" + price + " and there are " + currentStock + " items available.\n");

    System.out.print("How many items would you like to purchase?  : ");
    int amtToRemove = scan.nextInt();
    while (amtToRemove > currentStock) {
      System.out.print("You cannot purchase more items than we have in stock.");
      amtToRemove = scan.nextInt();
    }

    float purchasePrice = amtToRemove * price; // not required to implement yet

    if (WH.removeFromStock(sId, pName, amtToRemove))
      System.out.print("Sucessfully removed item from stock");

  }

  public static void addProductInventory(Scanner scan, Warehouse WH) {

    System.out.print("What product would you like add stock?  : ");
    scan.nextLine();
    String pName = scan.nextLine();

    if (!WH.isProduct(pName)) {
      System.out.println("This product does not exist\n");
      return;
    }

    System.out.print("What is the ID of the supplier would you would like to add stock?  : ");
    int sId = scan.nextInt();

    if (WH.getStock(sId, pName) < 0) {
      System.out.println("This product is not available from this supplier\n");
      return;
    }

    System.out.println("This product currently has a stock of " + WH.getStock(sId, pName));

    System.out.print("How many items would you like to add to stock?  : ");
    int amtToAdd = scan.nextInt();

    if (WH.addToStock(sId, pId, amtToAdd))
      System.out.print(
          "Sucessfully added " + amtToAdd + " items to stock for a total of " + WH.getStock(sId, pId) + " items.");
  }

}





public float makePayment(int cid, float amount) {

}

public float addCredit(int cid, float amount) {

}

private void writeObject(java.io.ObjectOutputStream output) {

}

private void readObject(java.io.ObjectInputStream input) throws ClassNotFoundException, IOException {

}

public ListIterator<ClientBalance> getClient() {

}

public String clientsToString() {
 
}

public boolean isClient(int cId) { // does the client exist?

}

public boolean isProduct(int pId) { // does the product exist?

}

public boolean isSupplier(int sId) { // does the supplier exist?

}

public boolean addSupplier(String sName) { // true means was not already in list

}

public boolean addSupplierToProduct(int pID, int sID){

}

public boolean removeSupplier(int sID) { // true means was in list and was removed;

}

public int numSuppliers(int pId) { // returns number of suppliers for the given product

}

public float getPrice(int pID, int sID) { // returns 0 if not found

}

public boolean setPrice(int pID, int sID, float newPrice) { // returns false if not found

}

public boolean setDescription(int pID, String newDescription) { // sets a product description

}

public String getDescription(int pID) {

}

public void addProduct(String pName) { // adds a product the the product list

}

public void addProduct(String pName, String pDescription) { // adds a product to the product list

}

public boolean removeProduct(int pID) { // removes a product from the product list

}

public int getStock(int sID, int pID) { // returns -9999999 if supplier does not exist

}

public boolean addToStock(int sID, int pID, int amtToAdd) { // adds an amount of product to stock for a given supplier

}

public boolean removeFromStock(int sID, int pID, int amtToRemove) { // adds an amount of product to stock
                                                                         // for a given supplier

}

public boolean setStock(int sID, int pID, int amtToSet) { // sets the stock for a product from a supplier

}

public boolean addToWaitlist(int cID, int pID, int sID, int qty) { // adds clients to a suppliers product

}

/*public boolean removeFromWaitlist(int cID, int pID, int sID) {// removes clients from a suppliers

}*/

public int popWaitlist(int sID, int pID) { //returns client ID or 0 if not found

}

public int fulfillWaitlist(int sID, int pID, int qty) { //returns the leftover not needed qty, -1 if not found

}

public void generateInvoice(int clientID) { // should only be called after the last item is in the cart.

}

public String getInvoice(int clientID, int oID) { // should only be called after the last item is in the cart.

}

public boolean addClient(String cName, String cAddress) {

}

public ListIterator<SupplierProduct> findSupplier(int sID){

}

public ListIterator<ProductSupplier> findProduct(int pID){

}

public ListIterator<ClientBalance> findClient(int cID){

}

public boolean removeClient(int cID) {

  
}