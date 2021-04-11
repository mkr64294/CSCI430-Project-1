import java.util.*;
import java.io.*;

public class CartState extends WareState{

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    private static CartState instance;
    private static final int EXIT = 0;
    private static final int HELP = 5;
    
    private CartState() {
        super();
        //warehouse = Warehouse.instance();
    }
    public static CartState instance() {
        if (instance == null) {
            instance = new CartState();
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
    public void logout(){
        (context).changeState(WareContext.CLIENT_STATE);
    }

    private void modifyCart() {
      
        System.out.println("Modify cart");
        
        System.out.println("What would you like to do?");
        System.out.println("1 for showing cart");
        System.out.println("2 for adding product");
        System.out.println("3 for removing product");
        System.out.println("4 for changing quantity");
        System.out.println("0 to return to Client Menu");
  
        int choice;
  
        while ((choice = Integer.parseInt(getToken("Enter a number"))) != 0) {
            switch (choice) {
                case 1: {
                  System.out.println(warehouse.showCart(Integer.parseInt(context.getUser())));
                  break;
                }

                case 2: {
                    int pID = Integer.parseInt(getToken("What is the ID of the product you're adding to your cart?  : "));
                
                    while(!warehouse.isProduct(pID)){
                        System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
                        pID = Integer.parseInt(getToken("What is the ID of the product you're adding to your cart?  : "));
                        if(pID == 0){
                            break;
                        }
                    }
                
                    int sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
                
                    while(!warehouse.isSupplierOfProduct(sID, pID)){
                        System.out.print("This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : ");
                        sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
                        if(sID == 0){
                            break;
                        }
                    }
                
                    System.out.print("The current price of this item is $"+ String.format("%.2f", warehouse.getPrice(pID, sID)) +".");
                    int qty = Integer.parseInt(getToken("\nHow many items would you like to buy?  : "));
                
                    while(qty<0){
                        System.out.print("You cannot purchase this number of items. Please enter a valid amount or press 0 to cancel  : ");
                        qty = Integer.parseInt(getToken("\nHow many items would you like to buy?  : "));
                        if(qty == 0){  
                            break;
                        }
                    }
                
                    float totalPrice = (float)qty*warehouse.getPrice(pID, sID);
                
                    if(warehouse.addToCart((Integer.parseInt(context.getUser())), pID, qty, sID)){
                        System.out.print("Item successfully added to cart.\nThis purchase would cost $"+String.format("%.2f", totalPrice)+".\n\n");
                    } else {
                        System.out.print("Unable to add item to cart.\n\n");
                    }
                    break;
                    }
              
  
                case 3: {  
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
                case 4: { 
                    System.out.println("Need to remove qty from cart");
                    int pID = Integer.parseInt(getToken("What is the ID of the product you're editing from your cart?  : "));
                
                    while(!warehouse.isProduct(pID)){
                        System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
                        pID = Integer.parseInt(getToken("What is the ID of the product you're adding to your cart?  : "));
                        if(pID == 0){
                          
                          break;
                        }
                    }
                  
                    int sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
                  
                    while(!warehouse.isSupplierOfProduct(sID, pID)){
                        System.out.print("This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : ");
                        sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
                        if(sID == 0){
                          
                          break;
                        }
                    }
                  
                    System.out.print("The current price of this item is $"+ String.format("%.2f", warehouse.getPrice(pID, sID)) +".");
                    int qty = Integer.parseInt(getToken("\nWhat is the new quantity you want to buy?  : "));
                  
                    while(qty<0){
                        System.out.print("You cannot purchase this number of items. Please enter a valid amount or press 0 to cancel  : ");
                        qty = Integer.parseInt(getToken("\nWhat is the new quantity you want to buy?  : "));
                        if(qty == 0){
                          
                          break;
                        }
                    }
                  
                    float totalPrice = (float)qty*warehouse.getPrice(pID, sID);
                  
                    if(warehouse.setCartQty((Integer.parseInt(context.getUser())), pID, qty, sID)){
                        System.out.print("Quantity changed.\nThis purchase now costs $"+String.format("%.2f", totalPrice)+".\n\n");
                    } else {
                        System.out.print("Unable to change quantity.\n\n");
                    }
                    break;
                }
                    
            }
  
        }
    }

    
    public void run() {
        modifyCart();
        logout();
    }
}
