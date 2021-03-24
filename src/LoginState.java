import java.util.*;
import java.io.*;

public class LoginState extends WareState {
    private static final int MANAGER_LOGIN = 0;
    private static final int CLERK_LOGIN = 1;
    private static final int CLIENT_LOGIN = 2;
    private static final int EXIT = 3;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //private WareContext context;
    private static LoginState instance;
    
    private LoginState() {
        super();
        // context = WareContext.instance();
    }

    public static LoginState instance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:"));
                if (value <= EXIT && value >= MANAGER_LOGIN) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }


    private void manager() {
        
        System.out.println("Login as Manager");
            (context).setLogin(WareContext.IsManager);
            (context).changeState(0);
        

    }

     private void clerk() {
        System.out.println("Login as Salesclerk");
             (context).setLogin(WareContext.IsSales);
             (context).changeState(1);
         }
     

    private void client() {
        String userID = getToken("Please input the client id: ");
        if (warehouse.isClient(Integer.parseInt(userID))) {
            (context).setLogin(WareContext.IsClient);
            (context).setUser(userID);
            (context).changeState(2);

        } else {
            System.out.println("Invalid user id.");
        }
    }

    public void process() {
        int command;
        System.out.println("Please input 0 to login as Manager \n"+
         "input 1 to login as SalesClerk \n" +
         "input 2 to login as Client \n" +
         "input 3 to exit the system\n");
       

        while ((command = getCommand()) != EXIT) {

            switch (command) {
                case MANAGER_LOGIN:{
                    manager();
                    break;
                }
                 case CLERK_LOGIN:{
                     clerk();
                     break;
                    }
                 case CLIENT_LOGIN:{
                     client();
                    break;
                }
                default:
                    System.out.println("Invalid choice");

            }
            
        }

        (context).changeState(3);
    }

    public void run() {
        process();
    }
}