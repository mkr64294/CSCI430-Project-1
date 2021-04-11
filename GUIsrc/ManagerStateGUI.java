
import java.util.*;
import java.io.*;

public class ManagerStateGUI extends WareStateGUI {

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  // private static Warehouse warehouse;
  // private WareContext context;
  private static ManagerStateGUI instance;
  private static final int EXIT = 0;
  private static final int ADD_PRODUCT = 1;
  private static final int ADD_SUPPLIER = 2;
  private static final int SHOW_SUPPLIERS = 3;
  private static final int SHOW_SUPPLIER_PRODUCTS = 4;
  private static final int SHOW_PRODUCT_SUPPLIERS = 5;
  private static final int UPDATE_SUPPLIER_PRODUCTS = 6;
  private static final int BECOME_CLERK = 7;
  private static final int HELP = 8;

  private static JTextField pNameTag;
  private static JTextField pdescriptionTag;
  private static JTextField pIDTag;
  private static JTextField sIDTag;
  private static JTextField sNameTag;

  private static JButton addNewProduct;
  private static JButton addNewSupplier;
  private static JButton updateProductSupplier;
  private static JButton viewAsSalesclerk;
  private static JButton viewSuppliers;
  private static JButton vieProductSuppliers;
  private static JButton viewSupplierProducts;

  private ManagerStateGUI() {
    super();
    // warehouse = Warehouse.instance();
  }

  public static ManagerStateGUI instance() {
    if (instance == null) {
      instance = new ManagerStateGUI();
    }
    return instance;
  }

  private void addProduct() {

    char entry = '0';
    String text;
    System.out.println("Adding Product ");

    String pName = getToken("What is this product called?  : ");

    while (entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N') {
      text = getToken("Does this product have a description? (y/n)   : ");
      entry = text.charAt(0);
      if (entry == 'y' || entry == 'Y') {
        String pDescription = getToken("Please enter a description for this product.  : ");
        System.out.println("Product " + pName + " sucessfully added.\nThis product has an ID of "
            + warehouse.addProduct(pName, pDescription) + ".\n");
      } else if (entry == 'n' || entry == 'N') {
        System.out.println("Product " + pName + " sucessfully added.\nThis product has an ID of "
            + warehouse.addProduct(pName) + ".\n");
      } else {
        text = getToken("Your previous entry is invalid. \nDoes this product have a description? (y/n)   : ");
        entry = text.charAt(0);
      }
    }
  }

  private String showProductSuppliers(int pID) {

    return warehouse.showProductListForSupplier(pID);

  }

  private String showSupplierProducts(int sID) {
    return warehouse.showSupplierListForProduct(sID);
  }

  private void addSupplier(String sName) {
    warehouse.addSupplier(sName);
  }

  /*
   * private void updateSupplierProducts() { int sID = Integer.parseInt(
   * getToken("Enter the Supplier ID of the supplier you want to edit"));
   * System.out.println(warehouse.showSupplierListForProduct(sID));
   * if(warehouse.isSupplier(sID)){
   * 
   * 
   * System.out.println("What would you like to do?");
   * System.out.println("1 for adding product");
   * System.out.println("2 for removing product");
   * System.out.println("3 for changing price");
   * System.out.println("0 to return to Manager Menu");
   * 
   * int choice;
   * 
   * while ((choice = Integer.parseInt(getToken("Enter a number"))) != 0) { switch
   * (choice) { case 1: { int pID =
   * Integer.parseInt(getToken("What is the ID of this product?  : ")); if
   * (warehouse.addSupplierToProduct(pID, sID)) {
   * System.out.println("Product sucessfully added to this supplier.\n");
   * 
   * float price =
   * Float.parseFloat(getToken("What is the price of this product?  : $")); String
   * priceString = String.format("%.2f", price);
   * 
   * if (warehouse.setPrice(pID, sID, price)){
   * System.out.println("The price of this product has been set to $"+ priceString
   * +".\n"); } else {
   * System.out.println("Unable to assign a price to this product.\n"); } } else {
   * System.out.println("Unable to add product to supplier.\n"); } break; }
   * 
   * case 2: { System.out.println("Removing product from supplier list");
   * 
   * int pID = Integer.parseInt(getToken("What is the ID of this product?  : "));
   * if (warehouse.removeSupplierFromProduct(sID, pID)) {
   * System.out.println("Product sucessfully removed from this supplier.\n");
   * 
   * 
   * } else { System.out.println("Unable to remove product from supplier.\n"); }
   * break; }
   * 
   * case 3: {
   * 
   * int pID = Integer.parseInt(getToken("What is the ID of this product?  : "));
   * 
   * 
   * if (warehouse.isSupplierOfProduct(sID, pID)) {
   * System.out.println("The current price of this product is $" +
   * String.format("%.2f", warehouse.getPrice(pID, sID))); float price =
   * Float.parseFloat(getToken("What is the new price of this product?  : $"));
   * String priceString = String.format("%.2f", price);
   * 
   * if (warehouse.setPrice(pID, sID, price)){
   * System.out.println("The price of this product has been set to $"+ priceString
   * +".\n"); } else {
   * System.out.println("Unable to assign a price to this product.\n"); } } else {
   * System.out.println("Unable to change the price of this product.\n"); } break;
   * }
   * 
   * } } } else{ System.out.println("Supplier not found"); } }
   * 
   * 
   * 
   * private String showSuppliers() {
   * 
   * return warehouse.showSupplierList();
   * 
   * }
   */

  /*
   * private void help() { System.out.println("Enter a number between " + EXIT +
   * " and " + HELP + " as explained below:"); System.out.println(EXIT +
   * " to Exit"); System.out.println(ADD_PRODUCT +
   * " to add a product to the system"); System.out.println(ADD_SUPPLIER +
   * " to add suppliers to the system "); System.out.println(SHOW_SUPPLIERS +
   * " to show suppliers"); System.out.println(SHOW_SUPPLIER_PRODUCTS +
   * " to  print products that a certain supplier has");
   * System.out.println(SHOW_PRODUCT_SUPPLIERS +
   * " to print suppliers that carry a certain product");
   * System.out.println(UPDATE_SUPPLIER_PRODUCTS +
   * " to change the products of a certain supplier");
   * System.out.println(BECOME_CLERK + " to use system as a salesclerk");
   * System.out.println(HELP + " for help"); }
   */

  private void becomeClerk() {
    System.out.println("\n\nBecoming Clerk");
    (context).changeState(WareContextGUI.SALES_STATE);
  }

  public void logout() {
    (context).changeState(WareContextGUI.MANAGER_STATE);
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
      case ADD_PRODUCT: {
        addProduct();
        break;
      }
      case ADD_SUPPLIER: {
        addSupplier();
        break;
      }
      case SHOW_SUPPLIERS: {
        showSuppliers();
        break;
      }
      case SHOW_SUPPLIER_PRODUCTS: {
        showSupplierProducts();
        break;
      }
      case SHOW_PRODUCT_SUPPLIERS: {
        showProductSuppliers();
        break;
      }
      case UPDATE_SUPPLIER_PRODUCTS: {
        updateSupplierProducts();
        break;
      }
      case BECOME_CLERK: {
        becomeClerk();
        break;
      }
      case HELP: {
        help();
        break;
      }
      }
    }
    logout();
  }

  public void run() {
    process();
  }
}