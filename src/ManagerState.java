import java.util.*;
import java.io.*;

public class ManagerState extends WareState {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    //private WareContext context;
    private static ManagerState instance;
    private static final int EXIT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_SUPPLIER = 2;
    private static final int SHOW_SUPPLIERS = 3;
    private static final int SHOW_SUPPLIER_PRODUCTS = 4;
    private static final int SHOW_PRODUCT_SUPPLIERS = 5;
    private static final int UPDATE_SUPPLIER_PRODUCTS = 6;
    private static final int BECOME_CLERK = 7;
    private static final int HELP = 8;

    private ManagerState() {
        super();
        //warehouse = Warehouse.instance();
    }

    public static ManagerState instance() {
        if (instance == null) {
            instance = new ManagerState();
        }
        return instance;
    }

    public String getToken(String prompt) {
        do {
          try {
            System.out.println(prompt);
            String line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
            if (tokenizer.hasMoreTokens()) {
              return tokenizer.nextToken();
            }
          } catch (IOException ioe) {
            System.exit(0);
          }
        } while (true);
      }

      public int getCommand() {
        do {
          try {
            int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
            if (value >= EXIT && value <= HELP) {
              return value;
            }
          } catch (NumberFormatException nfe) {
            System.out.println("Enter a number");
          }
        } while (true);
      }

     private void addProduct() {

        System.out.println("Code to add a product ");

        
     }

     private void showProductSuppliers() {

        System.out.println("code to get all suppliers that provide a certain product");

     }

     private void showSupplierProducts() {
        System.out.println("Code for showing which products a certain supplier carries");
        
     }

     

     private void addSupplier() {
        System.out.println("Code for adding suppliers");
        
     }

     private void updateSupplierProducts() {
        System.out.println("Code for modifying the products that a certain supplier carries");
        
     }

     

     private void showSuppliers() {
         System.out.println("Code to show all suppliers");

     }

    private void help() {
        System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
        System.out.println(EXIT + " to Exit");
        System.out.println(ADD_PRODUCT + " to add a product to the system");
        System.out.println(ADD_SUPPLIER + " to add suppliers to the system ");
        System.out.println(SHOW_SUPPLIERS + " to show suppliers");
        System.out.println(SHOW_SUPPLIER_PRODUCTS + " to  print products that a certain supplier has");
        System.out.println(SHOW_PRODUCT_SUPPLIERS + " to print suppliers that carry a certain product");
        System.out.println(UPDATE_SUPPLIER_PRODUCTS + " to change the products of a certain supplier");
        System.out.println(BECOME_CLERK + " to use system as a salesclerk");
        System.out.println(HELP + " for help");
    }

    private void becomeClerk() {
        System.out.println("\n\nBecoming Clerk");
        (WareContext.instance()).changeState(WareContext.SALES_STATE);
         }
    
         public void logout()
         {
             (WareContext.instance()).changeState(WareContext.MANAGER_STATE); 
         }
    

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                 case ADD_PRODUCT:{
                     addProduct();
                     break;
                    }
                 case ADD_SUPPLIER:{
                     addSupplier();
                     break;
                    }
                 case SHOW_SUPPLIERS:{
                     showSuppliers();
                     break;
                    }
                 case SHOW_SUPPLIER_PRODUCTS:{
                     showSupplierProducts();
                     break;
                    }
                    case SHOW_PRODUCT_SUPPLIERS:{
                        showProductSuppliers();
                        break;
                       }
                       case UPDATE_SUPPLIER_PRODUCTS:{
                        updateSupplierProducts();
                        break;
                       }
                       case BECOME_CLERK:{
                        becomeClerk();
                        break;
                       }
                case HELP:{
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