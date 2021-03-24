import java.util.*;
import java.io.*;

public class SalesState extends WareState {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    //private WareContext context;
    private static SalesState instance;
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int SHOW_CLIENTS = 2;
    private static final int SHOW_DUE_CLIENTS = 3;
    private static final int SHOW_PRODUCTS = 4;
    private static final int SHOW_WAITLIST = 5;
    private static final int RECEIVE_SHIPMENT = 6;
    private static final int BECOME_CLIENT = 7;
    private static final int HELP = 8;

    private SalesState() {
        super();
        //warehouse = Warehouse.instance();
    }

    public static SalesState instance() {
        if (instance == null) {
            instance = new SalesState();
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

     private void addClient() {

        System.out.println("Code to add a client ");

        
     }

     private void showWaitlist() {

        System.out.println("Code to get product's waitlist");

         
     }

     private void showProducts() {
        System.out.println("Code for showing warehouse products and quantity, will need to add to Warehouse");
        /*
         Iterator allProducts = warehouse.getProducts();
         while (allProducts.hasNext()) {
             Product product = (Product) (allProducts.next());
             System.out.println(product.toString());
         }
         */
     }

     

     private void showClients() {
        System.out.println("Code for showing all clients, will need to add to Warehouse");
        /*
         Iterator allProducts = warehouse.getProducts();
         while (allProducts.hasNext()) {
             Product product = (Product) (allProducts.next());
             System.out.println(product.toString());
         }
         */
     }

     private void recieveShipment() {
        System.out.println("Code for accepting shipments then accepting those products to waitlist or not");
        
     }

     

     private void showDueClients() {
         System.out.println("Code to somehow get all clients with an outstanding balance");

     }

    private void help() {
        System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
        System.out.println(EXIT + " to Exit");
        System.out.println(ADD_CLIENT + " to add a client to the system");
        System.out.println(SHOW_CLIENTS + " to print clients ");
        System.out.println(SHOW_DUE_CLIENTS + " to show clients with outstanding balance");
        System.out.println(SHOW_PRODUCTS + " to  print products");
        System.out.println(SHOW_WAITLIST + " to show products's waitlist");
        System.out.println(RECEIVE_SHIPMENT + " to accept an product shipment");
        System.out.println(BECOME_CLIENT + " to use system as a client");
        System.out.println(HELP + " for help");
    }

    private void becomeClient() {
             String userID = getToken("Please input the user id: ");
             if (warehouse.isClient(Integer.parseInt(userID))) {
                System.out.println("\n\nBecoming Client");
                 (WareContext.instance()).setUser(userID);
                 (WareContext.instance()).changeState(WareContext.CLIENT_STATE); 
             } else {
                 System.out.println("Invalid user id.");
             }
         }
    
        public void logout() {
            if ((WareContext.instance()).getLogin() == WareContext.IsManager) {
                System.out.println("\n\nReturning to Manager");
                (WareContext.instance()).changeState(WareContext.MANAGER_STATE);
            } else {
                (WareContext.instance()).changeState(WareContext.LOGIN_STATE);
            }
        }
    

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                 case ADD_CLIENT:{
                     addClient();
                     break;
                    }
                 case SHOW_CLIENTS:{
                     showClients();
                     break;
                    }
                 case SHOW_DUE_CLIENTS:{
                     showDueClients();
                     break;
                    }
                 case SHOW_PRODUCTS:{
                     showProducts();
                     break;
                    }
                    case SHOW_WAITLIST:{
                        showWaitlist();
                        break;
                       }
                       case RECEIVE_SHIPMENT:{
                        recieveShipment();
                        break;
                       }
                       case BECOME_CLIENT:{
                        becomeClient();
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