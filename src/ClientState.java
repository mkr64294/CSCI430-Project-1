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

        System.out.println("Getting User Details");
        System.out.println(warehouse.showClientDetails(Integer.parseInt(context.getUser())));
     }

     private void showWaitlist() {

        System.out.println("Getting User Waitlist");
        System.out.println(warehouse.printClientWaitlist(Integer.parseInt(context.getUser())));
     }

     private void showProducts() {
        System.out.println("Showing Products");
        System.out.println(warehouse.showProductList());
     }

     private void modifyCart() {


         
         System.out.println("Modify cart");
         

         System.out.println(warehouse.showCart(Integer.parseInt(context.getUser())));

         System.out.println("What would you like to do?");
        System.out.println("1 for adding product");
        System.out.println("2 for removing product");
        System.out.println("3 for changing quantity");
        System.out.println("0 to return to Client Menu");

        int choice;

        while ((choice = Integer.parseInt(getToken("Enter a number"))) != 0) {
            switch (choice) {
                 case 1: {
       
                    System.out.print("What is the ID of the product you're adding to your cart?  : ");
                    int pID = Integer.parseInt("What is the ID of the product you're adding to your cart?  : ");
                
                    while(!warehouse.isProduct(pID)){
                      System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
                      pID = Integer.parseInt("What is the ID of the product you're adding to your cart?  : ");
                      if(pID == 0){
                        
                        break;
                      }
                      break;
                }
            }

                 case 2: {
                

    System.out.println("These are the items currently in your shopping cart.");
    System.out.println(warehouse.showCart(Integer.parseInt(context.getUser())));
    String text;
    System.out.print("Would you like to empty this cart? (y/n)   : ");
    char entry = 'a';
    while (entry != 'y' && entry != 'Y' && entry != 'n' && entry != 'N') {
        text = getToken("Does this product have a description? (y/n)   : ");
        entry = text.charAt(0);
      if (entry == 'y' || entry == 'Y') {
        if(warehouse.clearCart(Integer.parseInt(context.getUser()))){
          System.out.println("This cart is now empty");
          break;
        }
      } else if (entry == 'n' || entry == 'N') {
        break;
      } else {
        System.out.print("Your previous entry is invalid.\nWould you like to empty this cart? (y/n)   : ");
      }
    }

    int pID = Integer.parseInt(getToken("What is the ID of the product you're removing from your cart?  : "));

    while(!warehouse.isProduct(pID)){
      System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
      pID = Integer.parseInt(getToken("What is the ID of the product you're removing from your cart?  : "));
      if(pID == 0){
        break;
        
      }
      int sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
  
      if(!warehouse.isInCart(Integer.parseInt(context.getUser()), pID, sID)){
        System.out.print("This item is not in the cart.");
        break;
      }
  
      System.out.print("The current price of this item is $"+ String.format("%.2f", warehouse.getPrice(pID, sID)) +" and there are "+ warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID) +" items in the cart.");
      int qty = Integer.parseInt(getToken("\nHow many items would you like to remove?  : "));
  
      while(qty < 0 || warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID) < qty){
        sID = Integer.parseInt(getToken("You cannot remove this number of items. Please enter a valid amount or press 0 to cancel  : "));
        if(sID == 0){
          
          break;
        }
      }
  
      int oldNumOfItems = warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID);
  
      if(warehouse.removeFromCart(Integer.parseInt(context.getUser()), pID, qty, sID) && (qty == oldNumOfItems)){
        System.out.println("Item successfully removed from cart");
      } else if (qty < oldNumOfItems) {
        System.out.println("There are "+warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID)+" items of this product remaining in the cart.");
      } else {
        System.out.print("Unable to remove item from cart.\n");
      }

                
                    
                        break;
                }
            }

                 case 3: {  //Need to remove qty from cart
                    System.out.println("Need to remove qty from cart");
                  
                  break;
                 }

            }
        }
         

     }

     private void getTransactions() {
         System.out.println("Getting latest Transaction");
        int oID = 0;
         String found = "";
        while(!(found.equals("not found"))){

         System.out.println(warehouse.getInvoice(Integer.parseInt(context.getUser()), oID));
         oID++;
        }
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