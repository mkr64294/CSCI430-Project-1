import java.util.*;

import java.io.*;

public class WareContext {

  private int currentState;

  private static WareContext context;
  private int currentUser;
  private String userID;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  public static final int IsManager = 0;
  public static final int IsSales = 1;
  public static final int IsClient = 2;
  public static final int IsShoppingCart = 4;
  public static final int IsQuery = 5;

  public static final int MANAGER_STATE = 0;
  public static final int SALES_STATE = 1;
  public static final int CLIENT_STATE = 2;
  public static final int LOGIN_STATE = 3;
  public static final int CART_STATE = 4;
  public static final int QUERY_STATE = 5;

  private WareState[] states;
  private int[][] nextState;

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

  public void setLogin(int code) {
    currentUser = code;
  }

  public void setUser(String uID) {
    userID = uID;
  }

  public int getLogin() {
    return currentUser;
  }

  public String getUser() {
    return userID;
  }

  private WareContext() {

    states = new WareState[6];
    states[0] = ManagerState.instance();
    states[1] = SalesState.instance();
    states[2] = ClientState.instance();
    states[3] = LoginState.instance();
    states[4] = CartState.instance();
    states[5] = QueryState.instance();

    nextState = new int[6][6];
    nextState[MANAGER_STATE][MANAGER_STATE] = LOGIN_STATE;
    nextState[MANAGER_STATE][SALES_STATE] = SALES_STATE;
    nextState[MANAGER_STATE][CLIENT_STATE] = -2;
    nextState[MANAGER_STATE][LOGIN_STATE] = -2;
    nextState[MANAGER_STATE][CART_STATE] = -2;
    nextState[MANAGER_STATE][QUERY_STATE] = -2;

    nextState[SALES_STATE][MANAGER_STATE] = MANAGER_STATE;
    nextState[SALES_STATE][SALES_STATE] = LOGIN_STATE;
    nextState[SALES_STATE][CLIENT_STATE] = CLIENT_STATE;
    nextState[SALES_STATE][LOGIN_STATE] = -2;
    nextState[SALES_STATE][CART_STATE] = -2;
    nextState[SALES_STATE][QUERY_STATE] = QUERY_STATE;

    nextState[CLIENT_STATE][MANAGER_STATE] = -2;
    nextState[CLIENT_STATE][SALES_STATE] = SALES_STATE;
    nextState[CLIENT_STATE][CLIENT_STATE] = LOGIN_STATE;
    nextState[CLIENT_STATE][LOGIN_STATE] = -2;
    nextState[CLIENT_STATE][CART_STATE] = CART_STATE;
    nextState[CLIENT_STATE][QUERY_STATE] = -2;

    nextState[LOGIN_STATE][MANAGER_STATE] = MANAGER_STATE;
    nextState[LOGIN_STATE][SALES_STATE] = SALES_STATE;
    nextState[LOGIN_STATE][CLIENT_STATE] = CLIENT_STATE;
    nextState[LOGIN_STATE][LOGIN_STATE] = -1;
    nextState[LOGIN_STATE][CART_STATE] = -2;
    nextState[LOGIN_STATE][QUERY_STATE] = -2;

    nextState[CART_STATE][MANAGER_STATE] = -2;
    nextState[CART_STATE][SALES_STATE] = -2;
    nextState[CART_STATE][CLIENT_STATE] = CLIENT_STATE;
    nextState[CART_STATE][LOGIN_STATE] = -2;
    nextState[CART_STATE][CART_STATE] = -2;
    nextState[CART_STATE][QUERY_STATE] = -2;

    nextState[QUERY_STATE][MANAGER_STATE] = -2;
    nextState[QUERY_STATE][SALES_STATE] = SALES_STATE;
    nextState[QUERY_STATE][CLIENT_STATE] = -2;
    nextState[QUERY_STATE][LOGIN_STATE] = -2;
    nextState[QUERY_STATE][CART_STATE] = -2;
    nextState[QUERY_STATE][QUERY_STATE] = -2;

    currentState = LOGIN_STATE;
  }

  public void changeState(int transition) {
    currentState = nextState[currentState][transition];
    if (currentState == -2) {
      System.out.println("Error has occurred");
      terminate();
    }
    if (currentState == -1)
      terminate();
    states[currentState].run();
  }

  private void terminate() {
    System.out.println(" Goodbye \n ");
    System.exit(0);
  }

  public static WareContext instance() {
    if (context == null) {
      System.out.println("calling constructor");
      context = new WareContext();
    }
    return context;
  }

  public void process() {
    states[currentState].run();
  }

  public static void run() {
    WareContext.instance().process();
  }
}
