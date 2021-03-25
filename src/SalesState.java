import java.util.*;
import java.io.*;

public class SalesState extends WareState {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //private static Warehouse warehouse;
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

        System.out.println("Adding Client");

        String cName = getToken("What is the name of this client?  : ");

        String cAddress = getToken("What is this client's address?   : ");
        int cId = warehouse.addClient(cName, cAddress);
        System.out.println("Client "+ cName +" at address "+ cAddress +" sucessfully added.\nThis client has an ID of "+ cId +".\n");

        
     }

     private void showWaitlist() {

        System.out.println("Getting Product's waitlist");
        int pID = Integer.parseInt(getToken("Enter the product ID of the product you want to view"));
        int sID = Integer.parseInt(getToken("Enter the supplier ID of which supplier you want to see product waitlist for"));
        System.out.println(warehouse.showProductWaitlist(pID, sID));
     }

     private void showProducts() {
        System.out.println("Showing Products");
        System.out.println(warehouse.showProductList());
     }

     

     private void showClients() {
        System.out.println("Showing all Clients");
        
        System.out.println(warehouse.clientsToString());
     }

     private void recieveShipment() {
        System.out.println("Recieving Shipment");
        
        int pID = Integer.parseInt(getToken("Enter the ID of the product recieved"));
        int sID = Integer.parseInt(getToken("Enter who we got that product from"));
        int qty = Integer.parseInt(getToken("Enter how many of that product we recieved"));

        System.out.println(warehouse.showProductWaitlist(pID, sID));
        

        System.out.println("Would you like to fulfill a waitlist order?\n" +
                          "Enter 1 for yes\n Enter 2 for no");
        String confirm = getToken("Enter your choice");
        
        if(confirm.equals("1")){    //Code needed to fulfill a waitlisted order
          System.out.println("Fulfill waitlist");
        }
        else if(confirm.equals("2")){
            warehouse.addToStock(sID, pID, qty);
            System.out.println("Added to stock");
        }
        else{
          System.out.println("Not a valid option");
        }
        
     }

     

     private void showDueClients() {
         System.out.println("Getting Clients with outstanding balance");
         System.out.println(warehouse.showClientsWithCredit());

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
                 (context).setUser(userID);
                 (context).changeState(WareContext.CLIENT_STATE); 
             } else {
                 System.out.println("Invalid user id.");
             }
         }
    
        public void logout() {
            if ((context).getLogin() == WareContext.IsManager) {
                System.out.println("\n\nReturning to Manager");
                (context).changeState(WareContext.MANAGER_STATE);
            } else {
                (context).changeState(WareContext.SALES_STATE);
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