//package GUIsrc;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class SalesStateGUI extends WareStateGUI {

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  // private static Warehouse warehouse;
  // private WareContext context;
  private static SalesStateGUI instance;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int CLIENT_QUERY = 2;
  private static final int SHOW_PRODUCTS = 3;
  private static final int SHOW_WAITLIST = 4;
  private static final int RECEIVE_SHIPMENT = 5;
  private static final int BECOME_CLIENT = 6;
  private static final int HELP = 7;

  private static JLabel cNameTag;
  private static JLabel cAddressTag;
  private static JLabel pIDTag;
  private static JLabel sIDTag;
  private static JLabel pQuantityTag;
  private static JLabel cIDTag;
  private static JLabel cIDorderTag;

  private static JButton addNewClient;
  private static JButton viewAsClient;
  private static JButton queryClient;
  private static JButton productShipment;
  private static JButton fulfillOrder;
  private static JButton viewWaitlist;
  private static JButton viewProducts;
  private static JButton back;

  private static JTextField cNameText;
  private static JTextField cAddressText;
  private static JTextField pIDText;
  private static JTextField sIDText;
  private static JTextField pQuantityText;
  private static JTextField cIDText;
  private static JTextField cIDorderText;

  private static JTextArea tabText;

  private static JPanel panel;

  private static JScrollPane scroll;

  private static JFrame wareFrame;

  private SalesStateGUI() {
    super();

  }

  public static SalesStateGUI instance() {
    if (instance == null) {
      instance = new SalesStateGUI();
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

  private void addClient() {

    System.out.println("Adding Client");

    String cName = getToken("What is the name of this client?  : ");

    String cAddress = getToken("What is this client's address?   : ");
    int cId = warehouse.addClient(cName, cAddress);
    System.out.println(
        "Client " + cName + " at address " + cAddress + " sucessfully added.\nThis client has an ID of " + cId + ".\n");

  }

  private void showWaitlist() {

    System.out.println("Getting Product's waitlist");
    int pID = Integer.parseInt(getToken("Enter the product ID of the product you want to view"));
    int sID = Integer
        .parseInt(getToken("Enter the supplier ID of which supplier you want to see product waitlist for"));
    System.out.println(warehouse.showProductWaitlist(pID, sID));
  }

  private void showProducts() {
    System.out.println("Showing Products");
    System.out.println(warehouse.showProductList());
  }

  private void recieveShipment() {
    System.out.println("Recieving Shipment");

    int pID = Integer.parseInt(getToken("Enter the ID of the product recieved"));
    int sID = Integer.parseInt(getToken("Enter who we got that product from"));
    int qty = Integer.parseInt(getToken("Enter how many of that product we recieved"));

    System.out.println(warehouse.showProductWaitlist(pID, sID));

    System.out.println("Would you like to fulfill a waitlist order?\n" + "Enter 1 for yes\n Enter 2 for no");
    String confirm = getToken("Enter your choice");

    if (confirm.equals("1")) {
      int cID = Integer.parseInt(getToken("Enter the ID of which client in the waitlist you want to fulfill"));
      warehouse.removeFromWaitlist(cID, pID, sID, qty);
      System.out.println("Waitlist order fulfilled");
    } else if (confirm.equals("2")) {
      warehouse.addToStock(sID, pID, qty);
      System.out.println("Added to stock");
    } else {
      System.out.println("Not a valid option");
    }

  }

  private void help() {
    System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
    System.out.println(EXIT + " to Exit");
    System.out.println(ADD_CLIENT + " to add a client to the system");
    System.out.println(CLIENT_QUERY + " to query the client list");
    System.out.println(SHOW_PRODUCTS + " to print products");
    System.out.println(SHOW_WAITLIST + " to show products's waitlist");
    System.out.println(RECEIVE_SHIPMENT + " to accept an product shipment");
    System.out.println(BECOME_CLIENT + " to use system as a client");
    System.out.println(HELP + " for help");
  }

  private void becomeClient() {
    String userID = getToken("Please input the user id: ");
    if (warehouse.isClient(Integer.parseInt(userID))) {
      System.out.println("\n\nBecoming Client");
      (context).setUser(userID);
      wareFrame.setVisible(false);
      wareFrame.dispose();
      (context).changeState(WareContextGUI.CLIENT_STATE);
    } else {
      System.out.println("Invalid user id.");
    }
  }

  public void logout() {

    if ((context).getLogin() == WareContextGUI.IsManager) {
      System.out.println("\n\nReturning to Manager");
      wareFrame.setVisible(false);
      wareFrame.dispose();
      (context).changeState(WareContextGUI.MANAGER_STATE);
    } else {
      wareFrame.setVisible(false);
      wareFrame.dispose();
      (context).changeState(WareContextGUI.SALES_STATE);
    }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
      case ADD_CLIENT: {
        addClient();
        break;
      }
      case CLIENT_QUERY: {
        (context).changeState(WareContextGUI.QUERY_STATE);
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
      case RECEIVE_SHIPMENT: {
        recieveShipment();
        break;
      }
      case BECOME_CLIENT: {
        becomeClient();
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
    context.setChangeStateFlag(false);
    wareFrame = new JFrame("Sales Clerk");
    wareFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if(context.getChangeStateFlag() == false){
          System.exit(0);
        }
      }
    });
    panel = new JPanel();

    panel.setLayout(null);

    tabText = new JTextArea();
    tabText.setBounds(10, 245, 500, 200);
    tabText.setEditable(false);
    scroll = new JScrollPane(tabText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    panel.add(scroll);
    panel.add(tabText);

    cNameTag = new JLabel("Client Name:");
    cNameTag.setBounds(10, 10, 80, 25);
    panel.add(cNameTag);

    cNameText = new JTextField(20);
    cNameText.setBounds(100, 10, 80, 25);
    panel.add(cNameText);

    cAddressTag = new JLabel("Client Addr:");
    cAddressTag.setBounds(10, 50, 80, 25);
    panel.add(cAddressTag);

    cAddressText = new JTextField(100);
    cAddressText.setBounds(100, 50, 200, 25);
    panel.add(cAddressText);

    addNewClient = new JButton("Add Client");
    addNewClient.setBounds(10, 80, 150, 25);
    panel.add(addNewClient);
    addNewClient.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        String cName = cNameText.getText();
        String cAddress = cAddressText.getText();

        int client = warehouse.addClient(cName, cAddress);

        tabText.setEditable(true);
        tabText.setText("New Client Added\n" + warehouse.showClientDetails(client));
        tabText.setEditable(false);

        cNameText.setText("");
        cAddressText.setText("");

        // creates new client with this name and address

      }
    });

    pIDTag = new JLabel("Product ID:");
    pIDTag.setBounds(310, 10, 80, 25);
    panel.add(pIDTag);

    pIDText = new JTextField(20);
    pIDText.setBounds(400, 10, 80, 25);
    panel.add(pIDText);

    sIDTag = new JLabel("Supplier ID:");
    sIDTag.setBounds(310, 40, 80, 25);
    panel.add(sIDTag);

    sIDText = new JTextField(20);
    sIDText.setBounds(400, 40, 80, 25);
    panel.add(sIDText);

    pQuantityTag = new JLabel("Product Qty:");
    pQuantityTag.setBounds(310, 70, 80, 25);
    panel.add(pQuantityTag);

    pQuantityText = new JTextField(20);
    pQuantityText.setBounds(400, 70, 80, 25);
    panel.add(pQuantityText);

    productShipment = new JButton("Accept Shipment");
    productShipment.setBounds(310, 100, 150, 25);
    panel.add(productShipment);
    productShipment.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        int pID = Integer.parseInt(pIDText.getText());
        int sID = Integer.parseInt(sIDText.getText());
        int pQTY = Integer.parseInt(pQuantityText.getText());

        warehouse.addToStock(sID, pID, pQTY);

        tabText.setEditable(true);
        tabText.setText(warehouse.showProductWaitlist(pID, sID));
        tabText.setEditable(false);

        pIDText.setText("");
        pQuantityText.setText("");

        // checks to see if product exists
        // if it does not, clear the pID and pQTY fields
        // if it does, then add product and its quantity to the inventory

      }
    });

    cIDorderTag = new JLabel("Client ID (To Fullfil Waitlist Order):");
    cIDorderTag.setBounds(310, 140, 220, 25);
    panel.add(cIDorderTag);

    cIDorderText = new JTextField(20);
    cIDorderText.setBounds(530, 140, 80, 25);
    panel.add(cIDorderText);

    fulfillOrder = new JButton("Fulfill Waitlist Order");
    fulfillOrder.setBounds(310, 170, 200, 25);
    panel.add(fulfillOrder);
    fulfillOrder.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        int pID = Integer.parseInt(pIDText.getText());
        int sID = Integer.parseInt(sIDText.getText());
        int pQTY = Integer.parseInt(pQuantityText.getText());
        int cID = Integer.parseInt(cIDorderText.getText());
        warehouse.removeFromWaitlist(cID, pID, sID, pQTY);

        tabText.setEditable(true);
        tabText.setText(warehouse.showProductWaitlist(pID, sID));
        tabText.setEditable(false);

        pIDText.setText("");
        pQuantityText.setText("");
        cIDorderText.setText("");

      }
    });

    cIDTag = new JLabel("Client ID:");
    cIDTag.setBounds(10, 130, 80, 25);
    panel.add(cIDTag);

    cIDText = new JTextField(20);
    cIDText.setBounds(100, 130, 80, 25);
    panel.add(cIDText);

    viewAsClient = new JButton("View As Client");
    viewAsClient.setBounds(10, 160, 150, 25);
    panel.add(viewAsClient);
    viewAsClient.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        String userName = cIDText.getText();
        if (warehouse.isClient(Integer.parseInt(userName))) {
          (context).setUser(userName);
          wareFrame.setVisible(false);
          wareFrame.dispose();
          (context).changeState(WareContextGUI.CLIENT_STATE);
        }

      }
    });

    viewWaitlist = new JButton("View Waitlist");
    viewWaitlist.setBounds(10, 220, 150, 25);
    panel.add(viewWaitlist);
    viewWaitlist.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        int pID = Integer.parseInt(pIDText.getText());
        int sID = Integer.parseInt(sIDText.getText());

        tabText.setEditable(true);
        tabText.setText(warehouse.showProductWaitlist(pID, sID));
        tabText.setEditable(false);

        // print waitlist in the JTextArea tabText

      }
    });

    viewProducts = new JButton("View Products");
    viewProducts.setBounds(160, 220, 150, 25);
    panel.add(viewProducts);
    viewProducts.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        tabText.setEditable(true);
        tabText.setText(warehouse.showProductList());
        tabText.setEditable(false);

        // prints the list of products stored in the warehouse in the JTextArea tabText

      }
    });

    queryClient = new JButton("Query Client");
    queryClient.setBounds(10, 450, 150, 25);
    panel.add(queryClient);
    queryClient.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        wareFrame.setVisible(false);
        wareFrame.dispose();
        (context).changeState(WareContextGUI.QUERY_STATE);

      }
    });
    back = new JButton("Back");
    back.setBounds(10, 600, 80, 25);
    panel.add(back);
    back.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        if (((context).getLogin()) == (WareContextGUI.SALES_STATE)) {
          wareFrame.setVisible(false);
          wareFrame.dispose();
          (context).changeState(WareContextGUI.LOGIN_STATE);
        } else if (((context).getLogin()) == (WareContextGUI.MANAGER_STATE)) {
          wareFrame.setVisible(false);
          wareFrame.dispose();
          (context).changeState(WareContextGUI.MANAGER_STATE);
        }
      }
    });
    wareFrame.add(panel);
    wareFrame.pack();
    wareFrame.setVisible(true);
    wareFrame.setExtendedState(wareFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  }
}