package GUIsrc;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class QueryStateGUI extends WareStateGUI {

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  private static QueryStateGUI instance;
  private static final int EXIT = 0;
  private static final int DISPLAY_CLIENT = 1;
  private static final int DISPLAY_OUTSTANDING_BALANCE = 2;
  private static final int DISPLAY_NO_TRANSACTION = 3;
  private static final int HELP = 4;

  private static JButton cButton;
  private static JButton balanceButton;
  private static JButton transactionButton;
  private static JButton back;

  private static JTextArea tabText;

  private static JPanel panel;

  private static JScrollPane scroll;

  private static JFrame wareFrame;

  private QueryStateGUI() {
    super();

  }

  public static QueryStateGUI instance() {
    if (instance == null) {
      instance = new QueryStateGUI();
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

  public void logout() {
    (context).changeState(WareContextGUI.SALES_STATE);
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
      case DISPLAY_CLIENT: {
        showClients();
        break;
      }
      case DISPLAY_OUTSTANDING_BALANCE: {
        showDueClients();
        break;
      }
      case DISPLAY_NO_TRANSACTION: {
        showNoTransactions();
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
    // process();

    wareFrame = new JFrame("Query");
    wareFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        // context.changeState(WareContextGUI.SALES_STATE);
      }
    });
    panel = new JPanel();

    panel.setLayout(null);

    tabText = new JTextArea();
    tabText.setBounds(10, 40, 500, 200);
    tabText.setEditable(false);
    scroll = new JScrollPane(tabText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    panel.add(scroll);
    panel.add(tabText);

    cButton = new JButton("View All Clients");
    cButton.setBounds(10, 10, 175, 25);
    panel.add(cButton);
    cButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        tabText.setEditable(true);
        tabText.setText(warehouse.clientsToString());
        if (tabText.getText() == "[]") {
          tabText.setText("No clients exist");
        }
        tabText.setEditable(false);
      }
    });

    balanceButton = new JButton("Outstanding Balance");
    balanceButton.setBounds(185, 10, 175, 25);
    panel.add(balanceButton);
    balanceButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        tabText.setEditable(true);
        tabText.setText(warehouse.showClientsWithCredit());
        if (tabText.getText() == "") {
          tabText.setText("No clients exist");
        }
        tabText.setEditable(false);
        // get a list of clients with a remaing balance and print to text field

      }
    });

    transactionButton = new JButton("No Transactions");
    transactionButton.setBounds(360, 10, 175, 25);
    panel.add(transactionButton);
    transactionButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        tabText.setEditable(true);
        tabText.setText(warehouse.showClientsWithNoTransactions());
        if (tabText.getText() == "") {
          tabText.setText("No clients exist");
        }
        tabText.setEditable(false);
        // get a list of clients with a no transactions and print to text field

      }
    });
    back = new JButton("Back");
    back.setBounds(10, 600, 80, 25);
    panel.add(back);
    back.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        wareFrame.setVisible(false);
        wareFrame.dispose();
        context.changeState(WareContextGUI.SALES_STATE);
        // go to previous state
      }
    });
    wareFrame.add(panel);
    wareFrame.pack();
    wareFrame.setVisible(true);
  }
}
