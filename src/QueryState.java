import java.util.*;
import java.io.*;

public class QueryState extends WareState{

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    private static QueryState instance;
    private static final int EXIT = 0;
    private static final int DISPLAY_CLIENT = 1;
    private static final int DISPLAY_OUTSTANDING_BALANCE = 2;
    private static final int DISPLAY_NO_TRANSACTION = 3;
    private static final int HELP = 4;

    private QueryState() {
        super();
        //warehouse = Warehouse.instance();
    }

    public static QueryState instance() {
        if (instance == null) {
            instance = new QueryState();
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

    public void logout(){
        (context).changeState(WareContext.SALES_STATE);
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

    private void help() {
        System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
        System.out.println(EXIT + " to Exit");
        System.out.println(DISPLAY_CLIENT + " to view all clients.");
        System.out.println(DISPLAY_OUTSTANDING_BALANCE + " to view a client's outstanding balance.");
        System.out.println(DISPLAY_NO_TRANSACTION + " to show all clients with no transactions.");
        System.out.println(HELP + " for help");
      }

      private void showClients() {
        System.out.println("Showing all Clients");
        
        System.out.println(warehouse.clientsToString());
     }

     private void showDueClients() {
        System.out.println("Getting Clients with outstanding balance");
        System.out.println(warehouse.showClientsWithCredit());

    }

    private void showNoTransactions() {
        System.out.println("Getting Clients with no transactions made.");
        System.out.println(warehouse.clientsToString());

    }

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case DISPLAY_CLIENT:{
                    showClients();
                    break;
                }
                case DISPLAY_OUTSTANDING_BALANCE:{
                    showDueClients();
                    break;
                }
                case DISPLAY_NO_TRANSACTION:{
                    showNoTransactions();
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
