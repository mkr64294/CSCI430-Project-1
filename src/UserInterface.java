/**
 * Author: Mark Rice Date: 3/8/2021 Class: CSCI430 Description: UI Class that
 * runs all the functions from the Warehouse class Version 3.0
 */

// import java.util.*;

class UserInterface {

  // private static Scanner scan = new Scanner(System.in);

  pub    WareContext.run();

  /*
   * 
   * // Initialize the Warehouse lists and the shopping cart Warehouse WH = new
   * Warehouse();
   * 
   * displayMenu();
   * 
   * cha entry = '0';
   * 
   * while (e != 'x' && entry != 'X') {
   * 
   * entry = an.nextLine().charAt(0);
   * 
   * if (entry =A' || entry == 'a') { // adding client
   * 
   * Syem.out.print("What is the name of this client?  : "); StriName =
   * scan.nextLine();
   * 
   * System.out.print("What is this client's address?   : "); String cAddress =
   * scan.nextLine(); int cId = WH.addClient(cName, cAddress);
   * System.out.println("Client "+ cName +" at address "+ cAddress
   * +" sucessfully added.\nThis client has an ID of "+ cId +".\n");
   * 
   * } el if (entry == 'B' || entry == 'b') { // adding product
   * System.ut.print("What is this product called?  : "); StName =
   * scan.nextLine();
   * 
   * System.out.print("Does this product have a description? (y/n)   : "); e
   * (entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N') { ent=
   * scan.nextLine().charAt(0); (entry == 'y' || entry == 'Y') {
   * System.out.print("Please enter a description for this product.  : "); String
   * pDescription = scan.nextLine(); } else if (enty == 'n' || entry == 'N') {
   * System.out.println("Product "+ pName
   * +" sucessfully added.\nThis product has an ID of "+ WH.addProduct(pName)
   * +".\n"); } else { System.out.
   * print("Your previous entry is invalid. \nDoes this product have a description? (y/n)   : "
   * ); entry = scan.nextLine().charAt(0); } }
   * 
   * } else if (entry == 'C' || entry == 'c') { // adding supplier
   * 
   * System.out.print("What is the name of this supplier?  : "); String sName =
   * scan.nextLine(); int sId = WH.addSupplier(sName);
   * System.out.println("Supplier "+ sName
   * +" sucessfully added.\nThis supplier has an ID of "+ sId +".\n");
   * 
   * } else if (entry == 'D' || entry == 'd') { // remove client
   * 
   * System.out.print("What is the ID of this client?  : "); int cID =
   * scan.nextInt(); scan .nextLine();
   * 
   * if (WH.r emoveClient(cID)) { Sy
   * stem.out.println("Client sucessfully removed.\n"); } else { Sy
   * stem.out.println("Unable to remove client.\n"); }
   * 
   * } else if (entr == 'E' || entry == 'e') { / remove product at is the ID of
   * this product? : "); int pId = scan.nextInt();
   * 
   * if (WH.removePro duct(pId)) { System.out
   * .println("Product sucessfully removed.\n"); } else {
   * System.out.println("Unable to remove product.\n"); }
   * 
   * } else if (entr == 'F' || entry == 'f') { // remove suppler at is the ID of
   * this supplier? : "); int sId = sc an.nextInt(); scan.nextLin e(;
   * 
   * if (WH.remov eSupplier(sId)) { System
   * .out.println("Supplier sucessfully removed.\n"); } else {
   * System.out.println("Unable to remove supplier.\n");
   * 
   * } else if (entry == 'G' || entry == 'g') { // Add Product To Supplier
   * 
   * System.out.print("What is thent();
   * 
   * System.out.p
   * rit("What ist  he ID of the supplier that will supply this product?  : ");
   * int sID = scan.n extInt(); scan.nextLin e(); if (WH.a
   * ddSupplierToProduct(pID, sID)) {
   * System.out.println("Product sucessfully added to this supplier.\n");
   * 
   * System.out.print("What is the price of this product?  : $"); float prce =
   * scan.nextFloat(); String pri ceString = String.format("%.2f", price); if (WH
   * .setPrice(pID, sID, price)){ System.o
   * ut.println("The price of this product has been set to $"+ priceString
   * +".\n"); } else { Syst
   * em.out.println("Unable to assign a price to this product.\n"); } } else {
   * 
   * Sy stem.out. println("Unable to add product to supplier.\n"); } scan.nex
   * tLine();
   * 
   * } else if (entry == 'H' || entry == 'h') { // Change price of product
   * 
   * System.o ut.print("What is the ID of this product?  : "); int pID = sc
   * an.nextInt();
   * 
   * System.out.p
   * rint("What is the ID of the supplier that supplies this product?  : "); int
   * sID = scan.n exInt();
   * 
   * if (WH.isSupplierOfP roduct(sID, pID)) { System.out.pri
   * ntln("The current price of this product is $" + String.format("%.2f",
   * WH.getPrice(pID, sID))); System.out
   * .print("What is the new price of this product?  : $"); float price =
   * scan.nextFloat(); String pri ceString = String.format("%.2f", price); ID,
   * price)){ System.out.println("The price of this product has been set to $"+
   * priceString +".\n"); } else {
   * System.out.println("Unable to assign a price to this product.\n"); }
   * System.out.println("Unable to change the price of this product.\n"); }
   * scan.nextLine(); } else if (entry == 'I' || addToCartUI(scn, WH);
   * 
   * } else if (entry == 'J' || entry == 'j') { // remove from cart
   * 
   * removeFromCartUI(scan, WH);
   * 
   * } else if (etry == 'K' || entry == 'k') { // accept payment from client
   * 
   * makePaymentUI(scan, WH);
   * 
   * } else if (entry == 'L' || entry == 'l') { // accept shipment from supplier
   * (scan, WH); ='M' || entry == 'm') { // add credit
   * 
   * addCreditUI(scan, WH); //
   * 
   * }else if (entr y = 'Z' || et ry == 'z') {
   * 
   * displayMenu( );
   * 
   * } else if (entry == 'X' || entry == 'x') {
   * 
   * break; s is any entry does not mach one of these options
   * 
   * System.o ut.println("This in not a valid selection. Please try again.\n");
   * 
   * }
   * 
   * private static void displayMenu() { System.out.p rintln
   * ("MAIN MENU\n\nPlease select the action you would like to perform\n");
   * System.o ut.println("Action                    Selection Key\n"); System.o
   * ut.printl n("Add Client                      A"); // written System.out.p
   * rintln("Add Product                     B"); // written System.out.p
   * rintln("Add SupplRemove Client                   D"); // written
   * System.out.println("Remove Product                  E"); // written
   * System.out.p rintl("Remove Supplier                F\n"); // written
   * System.out.print ln("Add Product Tge Price Of Product         H\n"); //
   * written System.out.print ln("Add To Cart                     I"); // written
   * System.out.print ln"Remove Frm Cart                J\n"); // written
   * System.out.println("Accept Payment From Client      K"); // written
   * System.out.println("Accept Shipment From Supplier   L\n"); //written
   * System.out.println("Add Client Credit               M\n");
   * System.out.println("Redisplay Menu                  Z");
   * System.out.println("Exit Warehouse                  X"); }
   * 
   * public static void ddToCartUI(Scanner scan, Warehouse WH){
   * System.out.print("What is your cl;
   * 
   * while(!WH.isClientcID)){ System.out.
   * print("This client does not exist. Please enter a valid client ID or press 0 to cancel  : "
   * ); cID = scan.nextInt(); if(cID == 0){ scan.nextLine(); return; }
   * 
   * System.out.
   * print("What is the ID of the product you're adding to your cart?  : "); int
   * pID = scan.netInt) ){ System.out.
   * print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : "
   * ); pID = scan.nextIt(); if(pID == 0){ scan.nextLine(); return; } }
   * 
   * System.out.
   * print("What is the ID of the supplier that supplies this product?  : "); int
   * sID = scannextInt(); ouct(sID, pID)){ System.out.
   * print("This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : "
   * ); sID = scan.nextInt(); if(sID == 0){ scan.nextLine(); return; } }
   * 
   * System.out.print("The current price of this item is $"+ String.format("%.2f",
   * WH.getPrice(pID, sID)) +".\nHow many items would you like to buy?  : "); i nt
   * qty = scan.nextInt();
   * 
   * while(qty<0){ System.out.
   * print("You cannot purchase this number of items. Please enter a valid amount or press 0 to cancel  : "
   * ); sID = scan.nextInt(); if(sID == 0){ scan.nextLine(); return; } }
   * 
   * float totalPrice = (float)qty*WH.getPrice(pID, sID);
   * 
   * if(WH.addToCart(cID, pID, qty, sID)){ System.out.
   * print("Item successfully added to cart.\nThis purchase would cost $"+String.
   * format("%.2f", totalPrice)+".\n\n"); } else {
   * System.out.print("Unable to add item to cart.\n\n"); } scan.nextLine(); }
   * 
   * public static void removeFromCartUI(Scanner scan, Warehouse WH){
   * System.out.print("What is your client ID?  : "); int cID = scan.nextInt();
   * 
   * while(!WH.isClient(cID)){ System.out.
   * print("This client does not exist. Please enter a valid client ID or press 0 to cancel  : "
   * ); cID = scan.nextInt(); if(cID == 0){ scan.nextLine(); return; } }
   * 
   * System.out.println("These are the items currently in your shopping cart.");
   * System.out.println(WH.showCart(cID));
   * 
   * System.out.print("Would you like to empty this cart? (y/n)   : "); char entry
   * = 'a'; while (entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N') {
   * scan.nextLine(); entry = scan.nextLine().charAt(0); if (entry == 'y' || entry
   * == 'Y') { if(WH.clearCart(cID)){
   * System.out.println("This cart is now empty"); return; } } else if (entry ==
   * 'n' || entry == 'N') { break; } else { System.out.
   * print("Your previous entry is invalid.\nWould you like to empty this cart? (y/n)   : "
   * ); } }
   * 
   * System.out.
   * print("What is the ID of the product you're removing from your cart?  : ");
   * int pID = scan.nextInt();
   * 
   * while(!WH.isProduct(pID)){ System.out.
   * print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : "
   * ); pID = scan.nextInt(); if(pID == 0){ scan.nextLine(); return; } }
   * 
   * System.out.
   * print("What is the ID of the supplier that supplies this product?  : "); int
   * sID = scan.nextInt();
   * 
   * if(!WH.isInCart(cID, pID, sID)){
   * System.out.print("This item is not in the cart."); return; }
   * 
   * System.out.print("The current price of this item is $"+ String.format("%.2f",
   * WH.getPrice(pID, sID)) +" and there are "+ WH.getCartQuantity(cID, pID, sID)
   * +" items in the cart.\nHow many items would you like to remove?  : "); int
   * qty = scan.nextInt();
   * 
   * while(qty < 0 || WH.getCartQuantity(cID, pID, sID) < qty){ System.out.
   * print("You cannot remove this number of items. Please enter a valid amount or press 0 to cancel  : "
   * ); sID = scan.nextInt(); if(sID == 0){ scan.nextLine(); return; } }
   * 
   * int oldNumOfItems = WH.getCartQuantity(cID, pID, sID);
   * 
   * if(WH.removeFromCart(cID, pID, qty, sID) && (qty == oldNumOfItems)){
   * System.out.println("Item successfully removed from cart"); } else if (qty <
   * oldNumOfItems) { System.out.println("There are "+WH.getCartQuantity(cID, pID,
   * sID)+" items of this product remaining in the cart."); } else {
   * System.out.print("Unable to remove item from cart.\n"); } scan.nextLine(); }
   * 
   * public static void makePaymentUI(Scanner scan, Warehouse WH){
   * System.out.print("What is your client ID?  : "); int cID = scan.nextInt();
   * 
   * while(!WH.isClient(cID)){ System.out.
   * print("This client does not exist. Please enter a valid client ID or press 0 to cancel  : "
   * ); cID = scan.nextInt(); if(cID == 0){ scan.nextLine(); return; } }
   * 
   * System.out.print("You have a current credit of $"+String.format("%.2f",
   * WH.getCredit(cID))+".\nHow much would you like to make a payment for?  : $");
   * float amount = scan.nextInt(); if ((WH.getCredit(cID) - amount) < 0.0){
   * System.out.println("You have a change of $"+String.format("%.2f", (amount -
   * WH.getCredit(cID)))+ "."); amount = WH.getCredit(cID); } float currentCredit
   * = WH.makePayment(cID, amount);
   * System.out.println("Amount of $"+String.format("%.2f",
   * amount)+" sucessfully paid. You have a current credit of $"+String.format(
   * "%.2f", currentCredit)+" remaining."); scan.nextLine(); }
   * 
   * public static void acceptShipmentUI(Scanner scan, Warehouse WH) {
   * 
   * System.out.print("What supplier is shipping this product?  : "); int sID =
   * scan.nextInt();
   * 
   * while(!WH.isSupplier(sID)){ System.out.
   * print("This supplier does not exist. Please enter a valid supplier ID or press 0 to cancel  : "
   * ); sID = scan.nextInt(); if(sID == 0){ scan.nextLine(); return; } }
   * 
   * char entry = 'y'; int qty = 0; while (entry == 'y' || entry == 'Y'){
   * System.out.print("What product would you like to order?  : "); int pID =
   * scan.nextInt();
   * 
   * if (!WH.isSupplierOfProduct(sID, pID)) { System.out.
   * print("This product is not available from this supplier\nPlease enter a different product ID or press 0 to cancel.  : "
   * ); pID = scan.nextInt(); if(pID == 0){ scan.nextLine(); return; } }
   * 
   * System.out.print("How many items would you like to order?  : "); qty =
   * scan.nextInt();
   * 
   * while(qty<0){ System.out.
   * print("You cannot order this number of items. Please enter a valid amount or press 0 to cancel  : "
   * ); sID = scan.nextInt(); if(sID == 0){ scan.nextLine(); return; } }
   * 
   * if(WH.addToStock(sID, pID, qty)){
   * System.out.println("Items successfully ordered and added to stock."); }else{
   * System.out.println("Unable to successfully order items"); }
   * 
   * System.out.
   * print("Would you like to add another item to this shipment? (y/n)  : ");
   * scan.nextLine(); entry = scan.nextLine().charAt(0); } }
   * 
   * public static void addCreditUI(Scanner scan, Warehouse WH){
   * System.out.print("What is your client ID?  : "); int cID = scan.nextInt();
   * 
   * while(!WH.isClient(cID)){ System.out.
   * print("This client does not exist. Please enter a valid client ID or press 0 to cancel  : "
   * ); cID = scan.nextInt(); if(cID == 0){ scan.nextLine(); return; } } String
   * credit = String.format("%.2f", WH.getCredit(cID));
   * 
   * System.out.println("Your current credit balance is $"+ credit+".");
   * System.out.print("How much credit would you like to add?  : $");
   * 
   * float amount = scan.nextFloat();
   * 
   * String newCredit = String.format("%.2f", WH.addCredit(cID, amount));
   * 
   * System.out.println("Your new credit balance is $"+newCredit+".");
   * scan.nextLine();
   */
}

}