
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
        String cName = scan.next();

        System.out.print("What is this client's address?   : ");
        String cAddress = scan.next();

        System.out.print("Please create a numerical ID for this client   : ");
        int clientID = scan.nextInt();

        if (WH.addClient(cName, cAddress, clientID)){
          System.out.println("Client sucessfully added.\n");
        } else{
          System.out.println("Unable to add client.\n");
        }
        
      } else if (entry == 'B' || entry == 'b') {  // adding product
        System.out.print("What is this product called?  : ");
        String pName = scan.next();

        System.out.print("What supplier supplies this product?   : ");
        String sName = scan.next();

        System.out.print("What is the ID of this supplier?   : ");
        int sId = scan.nextInt();

        System.out.print("Does this product have a description? (y/n)   : ");
        while(entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N'){
          entry = scan.next().charAt(0);
          if (entry == 'y' || entry == 'Y'){
            System.out.print("Please enter a description for this product.  : ");
            String pDescription = scan.next();
            if (WH.addProduct(pName, sName, sId, pDescription)){
              System.out.println("Product sucessfully added.\n");
            } else{
              System.out.println("Unable to add product.\n");
            }
          } else if (entry == 'n' || entry == 'N'){
            if (WH.addProduct(pName, sName, sId)){
              System.out.println("Product sucessfully added.\n");
            } else{
              System.out.println("Unable to add product.\n");
            }
          } else {
            System.out.print("Your previous entry is invalid. \nDoes this product have a description? (y/n)   : ");
            entry = scan.next().charAt(0);
          }
        }

      } else if (entry == 'C' || entry == 'c') {  // adding supplier

        System.out.print("What is the name of this supplier?  : ");
        String sName = scan.next();

        System.out.print("What is the ID of this supplier?   : ");
        int sId = scan.nextInt();
        
        if (WH.addSupplier(sId, sName)){
          System.out.println("Supplier sucessfully added.\n");
        } else{
          System.out.println("Unable to add Supplier.\n");
        }

      } else if (entry == 'D' || entry == 'd') {  // remove client

        System.out.print("What is the ID of this client?  : ");
        int clientID = scan.nextInt();

        if (WH.removeClient(clientID)){
          System.out.println("Client sucessfully removed.\n");
        } else{
          System.out.println("Unable to remove client.\n");
        }

      } else if (entry == 'E' || entry == 'e') {  // remove product
        
        System.out.print("What is the name of this product?  : ");
        String pName = scan.next();

        System.out.print("What is the ID of the supplier that supplies this product?  : ")
        int sId = scan.nextInt();

        if (WH.removeProduct(pName, sId)){
          System.out.println("Product sucessfully removed.\n");
        } else{
          System.out.println("Unable to remove product.\n");
        }

      } else if (entry == 'F' || entry == 'f') {  // remove supplier

        System.out.print("What is the ID of this supplier?  : ")
        int sId = scan.nextInt();

        if (WH.removeSupplier(sId)){
          System.out.println("Supplier sucessfully removed.\n");
        } else{
          System.out.println("Supplier to remove supplier.\n");
        }

      } else if (entry == 'G' || entry == 'g') {  // buy product

        buyProduct(scan);

      } else if (entry == 'H' || entry == 'h') {
        WH.addProductInventory(pList, cList, sList, cart);
      } else if (entry == 'Z' || entry == 'z') {
        displayMenu();
      } else { // This is is any entry does not match one of these options
        System.out.println("This in not a valid selection. Please try again.\n");
      }
    }

  private static void displayMenu() {
    System.out.println("MAIN MENU\n\nPlease select the action you would like to perform\n");
    System.out.println("Action                    Selection Key\n");
    System.out.println("Add Client                      A"); // Method written
    System.out.println("Add Product                     B"); // Method written
    System.out.println("Add Supplier                    C\n"); // Method written
    System.out.println("Remove Client                   D"); // Method written
    System.out.println("Remove Product                  E"); // Method written
    System.out.println("Remove Supplier                 F\n"); // Method written
    System.out.println("Buy Product                     G"); // Method written
    System.out.println("Add Product Inventory           H\n"); //
    System.out.println("Redisplay Menu                  Z");
    System.out.println("Exit Warehouse                  X");
  }

  public static void buyProduct(Scanner scan){

    System.out.print("What is your client ID?  : ")
    int cId = scan.nextInt();
    
    System.out.print("What product would you like to buy?  : ")
    String pName = scan.next();

    if (!isProduct(pName)){
      System.out.println("This product is not available\n");
      return;
    }

    System.out.print("What is the ID of the supplier would you like to buy from?  : ");
    int sId = scan.nextInt();

    if (getStock(sId, pName) < 0){
      System.out.println("This product is not available from this supplier\n");
      return;
    }

    int currentStock = getStock(sId, pName);

    float price = getPrice(pName, sId);

    System.out.println("This product has a cost of $"+price+" and there are "+currentStock+" items available.\n");

    System.out.print("How many items would you like to purchase?  : ");
    int amtToRemove = scan.nextInt();
    amtToRemove = abs(amtToRemove);
    while (amtToRemove > currentStock){
        System.out.print("You cannot purchase more items than we have in stock.");
        amtToRemove = scan.nextInt();
    }

    float purchasePrice = amtToRemove * price; //  not required to implement yet

    if(removeFromStock(sId, pName, amtToRemove))
      System.out.print("Sucessfully removed item from stock");

  }

  public float makePayment(int cid, float amount) {
  }

  public float addCredit(int cid, float amount) {
  }

  private void writeObject(java.io.ObjectOutputStream output) {
  }

  private void readObject(java.io.ObjectInputStream input) throws ClassNotFoundException, IOException {
  }

  public String clientsToString() {
  }

  public boolean isProduct(String pName) {
  }

  public boolean isSupplier(int sId) {
  }

  // public boolean addSupplier(int sId, String sName) { }

  // public void removeSupplier(int sId) { }

  public int numSuppliers(String pName) {
  }

  public double getPrice(String pName, int sId) {
  }

  public boolean setPrice(String pName, int sId, float newPrice) {
  }

  public boolean setDescription(String pName, int sId, String newDescription) {
  }

  public String getDescription(String pName) {
  }

  // public boolean addProduct(String pName, String sName, int sId) {}

  // public boolean addProduct(String pName, String sName, int sId, String
  // pDescription) {}

  // public boolean removeProduct(String pName, int sId) {}

  public int getStock(int sId, String pName) {
  }

  public boolean addToStock(int sId, String pName, int amtToAdd) {
  }

  public boolean removeFromStock(int sId, String pName, int amtToRemove) {
  }

  public boolean setStock(int sId, String pName, int amtToSet) {
  }

  public boolean addToWaitlist(Client client, String pName, int sId, int qty) {
  }

  public boolean removeFromWaitlist(int clientID, String pName, int sId) {
  }

  public String generateInvoice(int clientID){}

// public boolean addClient(String cName, String cAddress, int clientID){}

// public boolean removeClient(int clientID) { }
