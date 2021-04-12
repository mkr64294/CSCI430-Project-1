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

  private static JLabel cIDL;

  private static JTextField cIDF;

  private static JButton cButton;
  private static JButton balanceButton;
  private static JButton transactionButton;

  private static JTextArea tabText;

  private static JPanel panel;

  private static JScrollPane scroll;

  private static JFrame wareFrame;

  private int cID = -1;

  private QueryStateGUI() {
    super();

    wareFrame = new JFrame("Query");
    wareFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    panel = new JPanel();
    wareFrame.add(panel);
    panel.setLayout(null);

    tabText = new JTextArea();
    tabText.setBounds(10, 270, 500, 200);
    panel.add(tabText);

    scroll = new JScrollPane(tabText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    panel.add(scroll);

    cIDL = new JLabel("Client ID:");
    cIDL.setBounds(10, 10, 80, 25);
    panel.add(cIDL);

    cIDF = new JTextField(8);
    cIDL.setBounds(100, 10, 80, 25);
    panel.add(cIDL);

    cButton = new JButton("View Client");
    cButton.setBounds(10, 50, 80, 25);
    panel.add(cButton);
    cButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        cID = Integer.parseInt(cIDF.getText());

        // checks to see if client with current cID exists
        // if client does not exist, print "Client does not exist"
        // if client exists, print the client's blanace in the JTextArea tabText

      }
    });

    balanceButton = new JButton("Outstanding Balance");
    balanceButton.setBounds(10, 50, 120, 25);
    panel.add(balanceButton);
    balanceButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        // checks to see if client with current cID exists
        // if client does not exist, print "Client does not exist"
        // if client exists, print the client's blanace in the JTextArea tabText

      }
    });

    transactionButton = new JButton("Number of Transactions");
    transactionButton.setBounds(130, 50, 120, 25);
    panel.add(transactionButton);
    transactionButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        // checks to see if client with current cID exists
        // if client does not exist, print "Client does not exist"
        // if client exists, print the client's list of transactions in the JTextArea
        // tabText

      }
    });

    tabText = new JTextArea();
    tabText.setBounds(10, 270, 500, 200);
    panel.add(tabText);

    scroll = new JScrollPane(tabText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    panel.add(scroll);

    wareFrame.setVisible(true);

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
    process();
  }
}
