package GUIsrc;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class ClientStateGUI extends WareStateGUI {

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  private static ClientStateGUI instance;
  private static final int EXIT = 0;
  private static final int CLIENT_DETAILS = 1;
  private static final int MODIFY_CART = 2;
  private static final int GET_TRANSACTIONS = 3;
  private static final int SHOW_PRODUCTS = 4;
  private static final int SHOW_WAITLIST = 5;
  private static final int HELP = 6;

  private static JButton cartOptions;
  private static JButton viewWaitlist;
  private static JButton viewProducts;
  private static JButton viewTransactions;

  private static JTextArea tabText;
  private static JTextArea clientInfoText;

  private static JPanel panel;

  private static JScrollPane scroll;

  private static JFrame wareFrame;

  private ClientStateGUI() {
    super();

    wareFrame = new JFrame("Client");
    wareFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    panel = new JPanel();
    wareFrame.add(panel);
    panel.setLayout(null);

    cartOptions = new JButton("Shopping Cart Options");
    cartOptions.setBounds(220, 50, 80, 25);
    panel.add(cartOptions);
    cartOptions.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

      }
    });

    viewWaitlist = new JButton("View Waitlist");
    viewWaitlist.setBounds(10, 245, 80, 25);
    panel.add(viewWaitlist);
    viewWaitlist.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

      }
    });

    viewProducts = new JButton("View Products");
    viewProducts.setBounds(90, 245, 80, 25);
    panel.add(viewProducts);
    viewProducts.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

      }
    });

    viewTransactions = new JButton("View Transactions");
    viewTransactions.setBounds(170, 245, 80, 25);
    panel.add(viewTransactions);
    viewTransactions.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

      }
    });

    tabText = new JTextArea();
    tabText.setBounds(10, 270, 500, 200);
    panel.add(tabText);

    clientInfoText = new JTextArea();
    clientInfoText.setBounds(10, 10, 200, 80);
    panel.add(clientInfoText);

    scroll = new JScrollPane(tabText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    panel.add(scroll);

    wareFrame.setVisible(true);

  }

  public static ClientStateGUI instance() {
    if (instance == null) {
      instance = new ClientStateGUI();
    }
    return instance;
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

  private void getTransactions() {
    System.out.println("Getting latest Transaction");
    int oID = 0;
    String found = "";
    if ((found.equals(""))) {
      System.out.println("No Transactions found");
    } else {
      while (!(found.equals("not found"))) {
        System.out.println(warehouse.getInvoice(Integer.parseInt(context.getUser()), oID));
        found = warehouse.getInvoice(Integer.parseInt(context.getUser()), oID);
        oID++;
      }
    }
  }

  private void help() {
    System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
    System.out.println(EXIT + " to Exit");
    System.out.println(CLIENT_DETAILS + " to view client details");
    System.out.println(MODIFY_CART + " to modify client cart ");
    System.out.println(GET_TRANSACTIONS + " to show transaction list of a client");
    System.out.println(SHOW_PRODUCTS + " to  print products");
    System.out.println(SHOW_WAITLIST + " to show client's waitlist");
    System.out.println(HELP + " for help");
  }

  public void logout() {
    if ((context).getLogin() == WareContextGUI.IsManager) {
      System.out.println(" \n\nReturning to Clerk");
      (context).changeState(WareContextGUI.SALES_STATE);

    } else if (context.getLogin() == WareContextGUI.IsSales) {
      System.out.println(" \n\nReturning to Clerk");
      (context).changeState(WareContextGUI.SALES_STATE);

    } else if (context.getLogin() == WareContextGUI.IsClient) {
      (context).changeState(WareContextGUI.CLIENT_STATE);

    } else {
      (context).changeState(WareContextGUI.LOGIN_STATE);
    }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
      case CLIENT_DETAILS: {
        clientDetails();
        break;
      }
      case MODIFY_CART: {
        (context).changeState(WareContextGUI.CART_STATE);
        break;
      }
      case GET_TRANSACTIONS: {
        getTransactions();
        break;
      }
      case SHOW_PRODUCTS: {
        showProducts();
        break;
      }
      case SHOW_WAITLIST: {
        showWaitlist();
        break;
      }
      case HELP: {
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