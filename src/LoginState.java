
import java.util.*;
import java.text.*;
import java.io.*;

public class LoginState extends WareState {
	private static LoginState loginState;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;

   // private static WareContext context;
   // private static LoginState instance;

    private static final int EXIT =0;
	private static final int CLIENT_DETAILS =1;
 	private static final int PRODUCT_LIST =2;
 	private static final int CLIENT_TRANSACTION=3 ;
 	private static final int MODIFY_CART =4;
 	private static final int CLIENT_WAITLIST=5;
 	private static final int HELP=6;
 	private static final int LOGOUT =7;

 	private LoginState(){
 	warehouse =Warehouse.instance();
    }

    public static LoginState instance(){
 	if(instance ==null){
 		instance = new LoginState();
 	} else {
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
     }while (true);
    }

  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }


  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }


  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
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

   public void help(){
  	 System.out.println("Enter a number between 0 and 6 as explained below:");
  	 System.out.println(EXIT + " to Exit\n");
  	 System.out.println(CLIENT_DETAILS + " to show client details");
     System.out.println(PRODUCT_LIST+ "to show list of products with sales price");
     System.out.println(CLIENT_TRANSACTION + " to show Client Transaction");
  	 System.out.println(MODIFY_CART + "to modify clients shopping cart");
  	 System.out.println(CLIENT_WAITLIST + "to display clients waitlist");
  	 System.out.println(HELP + "for help");
  	 System.out.println(LOGOUT + "for logout");  
   }

    public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
       switch (command) {

         case EXIT:       ;
                                 break;
         case CLIENT_DETAILS:       ();
                                 break;
         case PRODUCT_LIST:        ();
                                 break;
         case CLIENT_TRANSACTION:  ();
                                 break;
         case MODIFY_CART:         ();
                                 break;
         case HELP:              help();
                                 break;
         case LOGOUT: 			logout();
      }
    }
  }

  public void run() {
    process();
  }
 /*
   public void logout()
   {
     if ((WareContext.instance()).getLogin() == WareContext.IsManager)
        { //stem.out.println(" going to clerk \n ");
          (WareContext.instance()).changeState(1); // exit with a code 1
         }
     else if (WareContext.instance().getLogin() == WareContext.IsSales)
        {  //stem.out.println(" going to login \n");
         (WareContext.instance()).changeState(0); // exit with a code 2
        }
     else if (WareContext.instance().getLogin() == WareContext.IsClient)
        {  //stem.out.println(" going to login \n");
         (WareContext.instance()).changeState(0); // exit with a code 2
        }
     else 
        (WareContext.instance()).changeState(2); // exit code 2, indicates error
   }
 */
}



 











	}
}