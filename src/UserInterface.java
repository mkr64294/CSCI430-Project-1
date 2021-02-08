
/** Author: Mark Rice
  * Date: 2/8/2021
  * Class: CSCI430
  * Description: 
  */

import java.util.*;

class UserInterface {

  public static LinkedList<Integer> clients;
  public static LinkedList<Integer> products;
  public static LinkedList<Integer> suppliers;
  public static Scanner scan;

  public static void main(String[] args) {

    scan = new Scanner(System.in);

    // This is where we initialize our clients, products, and suppliers LinkedList
    clients = new LinkedList<Integer>();
    products = new LinkedList<Integer>();
    suppliers = new LinkedList<Integer>();

    String input = "";

    while (!(input.equals("exit"))) {
      System.out.println(
          "Which class's methods would you like to test?\n   Client\n   Product\n   Supplier\nTo exit this UI, enter \"exit\"");
      input = scan.nextLine();

      // display different menus
      if (input.equals("Client")) {
        clientProcess();
      } else if (input.equals("Product")) {
        productProcess();
      } else if (input.equals("Supplier")) {
        supplierProcess();
      } else if (input.equals("exit")) {
        break;
      } else {
        System.out.println("This is not a valid input\n");
      }
    }
  }

  public static void clientProcess() {
    System.out.print("Would you like to create a new client? y/n: ");
    String input = scan.nextLine();
    if (input.equals("y")) {
      System.out.print("Enter the client's ID (this must be a number): ");
      int ID = scan.nextInt();
      if (clients.contains(ID)) {
        // inform user that this client already exists
        // skip the rest and go back to the start of this function
      }
      clients.addLast(ID);
      System.out.print("Enter the client's name: ");
      String name = scan.nextLine();
    }
    // First, ask if you would like to create a new client. If yes, then do so and
    // add them to the clients LinkedList
    // then run through all the methods for the client in the client class
    // then, ask if you would like to remove a client
    // finally,ask if you would like to create another client. If yes, repeat the
    // last two things
  }

  public static void productProcess() {
    System.out.println("I'm a product");
    // First, ask if you would like to create a new product. If yes, then do so and
    // add them to the products LinkedList
    // then run through all the methods for the product in the product class
    // then, ask if you would like to remove a product
    // finally,ask if you would like to create another product. If yes, repeat the
    // last two things
  }

  public static void supplierProcess() {
    System.out.println("I'm a supplier");
    // First, ask if you would like to create a new supplier. If yes, then do so and
    // add them to the suppliers LinkedList
    // then run through all the methods for the supplier in the supplier class
    // then, ask if you would like to remove a supplier
    // finally,ask if you would like to create another supplier. If yes, repeat the
    // last two things
  }
}
