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
        System.out.println(warehouse.showClientDetails(Integer.parseInt(context.getUser())));
     }

     private void showWaitlist() {

        System.out.println("Code to get client waitlist " + context.getUser() + "here");
        System.out.println(warehouse.printClientWaitlist(Integer.parseInt(context.getUser())));
     }

     private void showProducts() {
        System.out.println("Code for showing warehouse products, will need to add to Warehouse");
        System.out.println(warehouse.showProductList());
     }

     private void modifyCart() {


         
         System.out.println("Code to modify cart");
         System.out.println("Seems every state has that one code that requires more things to set up " +
         "and as such this is that for Client for now here's cart");

         System.out.println(warehouse.showCart(Integer.parseInt(context.getUser())));
         

     }

     private void getTransactions() {
         System.out.println("Code to get transactions for client " + context.getUser() + "here");

         System.out.println("This one is interesting since we don't have any form of tracking transactions, perhaps invoices?");
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
        if ((context).getLogin() == WareContext.IsManager) {
            System.out.println(" \n\nReturning to Clerk");
            (context).changeState(WareContext.SALES_STATE); // exit with a code 1
            
        } else if (context.getLogin() == WareContext.IsSales) {
            System.out.println(" \n\nReturning to Clerk");
            (context).changeState(WareContext.SALES_STATE); // exit with a code 2
            
        } else if (context.getLogin() == WareContext.IsClient) {
            (context).changeState(WareContext.CLIENT_STATE); 
            
        } else {
            (context).changeState(WareContext.LOGIN_STATE); // exit code 2, indicates error
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