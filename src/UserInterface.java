
/**
 Author: Mark Rice 
 Date: 3/8/2021 
 Class: CSCI430 
 Description: UI Class that runs all the functions from the Warehouse class
 Version 3.0
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
          }
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
          System.out.print("The current price of this product is $" + WH.getPrice(pID, sID));
          System.out.print("What is the new price of this product?  : $");
          float price = scan.nextFloat();

          if (WH.setPrice(pID, sID, price)){
            System.out.println("The price of this product has been set to"+ price +".\n");
          } else {
            System.out.println("Unable to assign a price to this product.\n");
          }
        } else {
          System.out.println("Unable to add product to supplier.\n");
        }

      } else if (entry == 'I' || entry == 'i') { // add to cart

        addToCartUI(scan, WH);

      } else if (entry == 'J' || entry == 'j') { // remove from cart

        removeFromCartUI(scan, WH);

      } else if (entry == 'K' || entry == 'k') { // accept payment from client

        makePaymentUI(scan, WH);

      } else if (entry == 'L' || entry == 'l') { // accept shipment from supplier
      
        acceptShipmentUI(scan, WH);
      
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
    System.out.println("Add To Cart                     I"); // written
    System.out.println("Remove From Cart                J\n"); // written
    System.out.println("Accept Payment From Client      K"); // written
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
    int sID = scan.nextInt();

    while(!WH.isSupplierOfProduct(sID, pID)){
      System.out.print("This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : ");
      scan.nextLine();
      sID = scan.nextInt();
      if(sID == 0)
        return;
    }

    System.out.print("The current price of this item is $"+ WH.getPrice(pID, sID) +".\nHow many items would you like to buy?  : ");
    scan.nextLine();
    int qty = scan.nextInt();

    while(qty<0){
      System.out.print("You cannot purchase this number of items. Please enter a valid amount or press 0 to cancel  : ");
      scan.nextLine();
      sID = scan.nextInt();
      if(sID == 0)
        return;
    }

    float totalPrice = (float)qty*WH.getPrice(pID, sID);

    if(WH.addToCart(cID, pID, qty, sID)){
      System.out.print("Item sucessfully added to cart.\nThis purchase would cost $"+totalPrice+".");
    } else {
      System.out.print("Unable to add item to cart.\n");
    }
  }

  public static void removeFromCartUI(Scanner scan, Warehouse WH){
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

    System.out.print("These are the items currently in your shopping cart");
    WH.showCart(cID);

    System.out.print("Would you like to empty this cart? (y/n)   : ");
    char entry = 'a';
    while (entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N') {
      entry = scan.next().charAt(0);
      if (entry == 'y' || entry == 'Y') {
        if(WH.clearCart(cID)){
          System.out.println("This cart is now empty");
          return;
        }
      } else if (entry == 'n' || entry == 'N') {
        break;
      } else {
        System.out.print("Your previous entry is invalid.\nWould you like to empty this cart? (y/n)   : ");
        entry = scan.next().charAt(0);
      }
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
    int sID = scan.nextInt();

    if(!WH.isInCart(cID, pID, sID)){
      System.out.print("This item is not in the cart.");
      return;
    }

    System.out.print("The current price of this item is $"+ WH.getPrice(pID, sID) +" and there are "+ WH.getCartQuantity(cID, pID, sID) +" items in the cart.\nHow many items would you like to remove?  : ");
    scan.nextLine();
    int qty = scan.nextInt();

    while(qty < 0 || WH.getCartQuantity(cID, pID, sID) < qty){
      System.out.print("You cannot remove this number of items. Please enter a valid amount or press 0 to cancel  : ");
      scan.nextLine();
      sID = scan.nextInt();
      if(sID == 0)
        return;
    }

    int oldNumOfItems = WH.getCartQuantity(cID, pID, sID);

    if(WH.removeFromCart(cID, pID, qty, sID) && (qty == oldNumOfItems)){
      System.out.print("Item successfully removed from cart");
    } else if (qty < oldNumOfItems) {
      System.out.print("There are "+WH.getCartQuantity(cID, pID, sID)+" of this product remaining in the cart.");
    } else {
      System.out.print("Unable to remove item from cart.\n");
    }
  }

  public static void makePaymentUI(Scanner scan, Warehouse WH){
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

        System.out.print("You have a current credit of $"+WH.getCredit(cID)+".\nHow much would you like to make a payment for?  : $");
        scan.nextLine();
        float amount = scan.nextInt();
        
        float currentCredit = WH.makePayment(cID, amount);

        System.out.print("Amount of $"+amount+" sucessfully paid. You have a current credit of $"+currentCredit+" remaining.");
  }
  
  public static void acceptShipmentUI(Scanner scan, Warehouse WH) {

    System.out.print("What supplier could you like to order from?  : ");
    scan.nextLine();
    int sID = scan.nextInt();

    while(!WH.isSupplier(sID)){
      System.out.print("This supplier does not exist. Please enter a valid supplier ID or press 0 to cancel  : ");
      scan.nextLine();
      sID = scan.nextInt();
      if(sID == 0)
        return;
    }

    System.out.print("What product would you like to order?  : ");
    scan.nextLine();
    int pID = scan.nextInt();

    if (!WH.isSupplierOfProduct(sID, pID)) {
      System.out.println("This product is not available from this supplier\nPlease enter a different product ID or press 0 to cancel.  : ");
      scan.nextLine();
      pID = scan.nextInt();
      if(pID == 0)
        return;
    }

    System.out.print("How many items would you like to order?  : ");
    scan.nextLine();
    int qty = scan.nextInt();

    while(qty<0){
      System.out.print("You cannot order this number of items. Please enter a valid amount or press 0 to cancel  : ");
      scan.nextLine();
      sID = scan.nextInt();
      if(sID == 0)
        return;
    }

    if(WH.addToStock(sID, pID, qty)){
      System.out.print("Items sucessfully ordered and added to stock.");
    }else{
      System.out.print("Unable to sucessfully order items");
    }
  }
}