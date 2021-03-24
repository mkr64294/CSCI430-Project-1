import java.util.*;
import java.io.*;

public class ClientState extends WareState {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    //private WareContext context;
    private static ClientState instance;
    private static final int EXIT = 0;
    private static final int CLIENT_DETAILS = 1;
    private static final int MODIFY_CART = 2;
    private static final int GET_TRANSACTIONS = 3;
    private static final int SHOW_PRODUCTS = 4;
    private static final int SHOW_WAITLIST = 5;
    private static final int HELP = 6;

    private ClientState() {
        super();
        //warehouse = Warehouse.instance();
    }

    public static ClientState instance() {
        if (instance == null) {
            instance = new ClientState();
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

     private void clientDetails() {

        System.out.println("Code to get details for client " + context.getUser() + "here");

         String clientId = WareContext.instance().getUser();

         if (warehouse.isClient(Integer.parseInt(clientId))) {
             
            System.out.println("Client is found in list, must add code here");

         } else {
             System.out.println("Client is not found.");
         }
     }

     private void showWaitlist() {

        System.out.println("Code to get client waitlist " + context.getUser() + "here");

         String clientId = WareContext.instance().getUser();

         if (warehouse.isClient(Integer.parseInt(clientId))) {
             
            System.out.println("Client is found in list, must add code here");

         } else {
             System.out.println("Client is not found.");
         }
     }

     private void showProducts() {
        System.out.println("Code for showing warehouse products, will need to add to Warehouse");
        /*
         Iterator allProducts = warehouse.getProducts();
         while (allProducts.hasNext()) {
             Product product = (Product) (allProducts.next());
             System.out.println(product.toString());
         }
         */
     }

     private void modifyCart() {


         String clientId = WareContext.instance().getUser();
         System.out.println("Code to modify cart");
         if (warehouse.isClient(Integer.parseInt(clientId))) {
            System.out.println("Client " + context.getUser() + "found in list");
             
         } else {
             System.out.println("Client is not found.");
         }

     }

     private void getTransactions() {
         System.out.println("Code to get transactions for client " + context.getUser() + "here");
/*
         Iterator result;
         String clientId = WareContext.instance().getUser();
         //Calendar date  = getDate("Please enter the date for which you want records as mm/dd/yy");
         result = warehouse.getTransactions(clientId);
         if (result == null) {
             System.out.println("Invalid Client ID");
         } else {
             while (result.hasNext()) {
                 Transaction transaction = (Transaction) result.next();
                 System.out.println(transaction.toString());
             }
             System.out.println("\n  There are no more transactions \n");
         }*/
     }

    private void help() {
        System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(CLIENT_DETAILS + " to view client details");
        System.out.println(MODIFY_CART + " to modify client cart ");
        System.out.println(GET_TRANSACTIONS + " to show transaction list of a client");
        System.out.println(SHOW_PRODUCTS + " to  print products");
        System.out.println(SHOW_WAITLIST + " to show client's waitlist");
        System.out.println(HELP + " for help");
    }

    public void logout() {
        if ((WareContext.instance()).getLogin() == WareContext.IsManager) {
            System.out.println(" \n\nReturning to Clerk");
            (WareContext.instance()).changeState(WareContext.SALES_STATE); // exit with a code 1
            
        } else if (WareContext.instance().getLogin() == WareContext.IsSales) {
            System.out.println(" \n\nReturning to Clerk");
            (WareContext.instance()).changeState(WareContext.SALES_STATE); // exit with a code 2
            
        } else if (WareContext.instance().getLogin() == WareContext.IsClient) {
            (WareContext.instance()).changeState(WareContext.LOGIN_STATE); 
            
        } else {
            (WareContext.instance()).changeState(WareContext.CLIENT_STATE); // exit code 2, indicates error
        }
    }

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                 case CLIENT_DETAILS:{
                     clientDetails();
                     break;
                    }
                 case MODIFY_CART:{
                     modifyCart();
                     break;
                    }
                 case GET_TRANSACTIONS:{
                     getTransactions();
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