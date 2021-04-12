package GUIsrc;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class CartStateGUI extends WareStateGUI {

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  private static CartStateGUI instance;
  private static final int EXIT = 0;
  private static final int HELP = 5;

  private static JLabel pIDlabel;
  private static JLabel pQTYlabel;
  private static JLabel cartlabel;

  private static JTextField pIDfield;
  private static JTextField pQTYfield;
  private static JTextField sIDfield;

  private static JButton addProduct;
  private static JButton updateQuantity;
  private static JButton removeProduct;
  private static JButton back;

  private static JTextArea tabText;

  private static JScrollPane scroll;

  private static JPanel panel;

  private static JFrame wareFrame;

  private CartStateGUI() {
    super();

  }

  public static CartStateGUI instance() {
    if (instance == null) {
      instance = new CartStateGUI();
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

  public void logout() {
    (context).changeState(WareContextGUI.CLIENT_STATE);
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

        while (!warehouse.isProduct(pID)) {
          System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
          pID = Integer.parseInt(getToken("What is the ID of the product you're adding to your cart?  : "));
          if (pID == 0) {
            break;
          }
        }

        int sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));

        while (!warehouse.isSupplierOfProduct(sID, pID)) {
          System.out.print(
              "This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : ");
          sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
          if (sID == 0) {
            break;
          }
        }

        System.out
            .print("The current price of this item is $" + String.format("%.2f", warehouse.getPrice(pID, sID)) + ".");
        int qty = Integer.parseInt(getToken("\nHow many items would you like to buy?  : "));

        while (qty < 0) {
          System.out
              .print("You cannot purchase this number of items. Please enter a valid amount or press 0 to cancel  : ");
          qty = Integer.parseInt(getToken("\nHow many items would you like to buy?  : "));
          if (qty == 0) {
            break;
          }
        }

        float totalPrice = (float) qty * warehouse.getPrice(pID, sID);

        if (warehouse.addToCart((Integer.parseInt(context.getUser())), pID, qty, sID)) {
          System.out.print("Item successfully added to cart.\nThis purchase would cost $"
              + String.format("%.2f", totalPrice) + ".\n\n");
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
            if (warehouse.clearCart(Integer.parseInt(context.getUser()))) {
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

        while (!warehouse.isProduct(pID)) {
          System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
          pID = Integer.parseInt(getToken("What is the ID of the product you're removing from your cart?  : "));
          if (pID == 0) {
            break;
          }
        }
        int sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));

        if (!warehouse.isInCart(Integer.parseInt(context.getUser()), pID, sID)) {
          System.out.print("This item is not in the cart.");
          break;
        }

        System.out.print("The current price of this item is $" + String.format("%.2f", warehouse.getPrice(pID, sID))
            + " and there are " + warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID)
            + " items in the cart.");
        int qty = Integer.parseInt(getToken("\nHow many items would you like to remove?  : "));

        while (qty < 0 || warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID) < qty) {
          sID = Integer.parseInt(
              getToken("You cannot remove this number of items. Please enter a valid amount or press 0 to cancel  : "));
          if (sID == 0) {
            break;
          }
        }

        int oldNumOfItems = warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID);

        if (warehouse.removeFromCart(Integer.parseInt(context.getUser()), pID, qty, sID) && (qty == oldNumOfItems)) {
          System.out.println("Item successfully removed from cart");
        } else if (qty < oldNumOfItems) {
          System.out.println("There are " + warehouse.getCartQuantity(Integer.parseInt(context.getUser()), pID, sID)
              + " items of this product remaining in the cart.");
        } else {
          System.out.print("Unable to remove item from cart.\n");
        }
        break;
      }
      case 4: {
        System.out.println("Need to remove qty from cart");
        int pID = Integer.parseInt(getToken("What is the ID of the product you're editing from your cart?  : "));

        while (!warehouse.isProduct(pID)) {
          System.out.print("This product does not exist. Please enter a valid product ID or press 0 to cancel  : ");
          pID = Integer.parseInt(getToken("What is the ID of the product you're adding to your cart?  : "));
          if (pID == 0) {

            break;
          }
        }

        int sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));

        while (!warehouse.isSupplierOfProduct(sID, pID)) {
          System.out.print(
              "This supplier does not supply this product. Please enter a valid supplier ID or press 0 to cancel  : ");
          sID = Integer.parseInt(getToken("What is the ID of the supplier that supplies this product?  : "));
          if (sID == 0) {

            break;
          }
        }

        System.out
            .print("The current price of this item is $" + String.format("%.2f", warehouse.getPrice(pID, sID)) + ".");
        int qty = Integer.parseInt(getToken("\nWhat is the new quantity you want to buy?  : "));

        while (qty < 0) {
          System.out
              .print("You cannot purchase this number of items. Please enter a valid amount or press 0 to cancel  : ");
          qty = Integer.parseInt(getToken("\nWhat is the new quantity you want to buy?  : "));
          if (qty == 0) {

            break;
          }
        }

        float totalPrice = (float) qty * warehouse.getPrice(pID, sID);

        if (warehouse.setCartQty((Integer.parseInt(context.getUser())), pID, qty, sID)) {
          System.out
              .print("Quantity changed.\nThis purchase now costs $" + String.format("%.2f", totalPrice) + ".\n\n");
        } else {
          System.out.print("Unable to change quantity.\n\n");
        }
        break;
      }

      }

    }
  }

  public void run() {
    // modifyCart();
    wareFrame = new JFrame("Shopping Cart");
    wareFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    panel = new JPanel();

    panel.setLayout(null);

    pIDlabel = new JLabel("Product ID:");
    pIDlabel.setBounds(10, 10, 130, 25);
    panel.add(pIDlabel);

    pQTYlabel = new JLabel("Product QTY:");
    pQTYlabel.setBounds(10, 40, 100, 25);
    panel.add(pQTYlabel);

    cartlabel = new JLabel("Shopping Cart");
    cartlabel.setBounds(10, 100, 100, 25);
    panel.add(cartlabel);

    pIDfield = new JTextField(10);
    pIDfield.setBounds(110, 10, 50, 25);
    panel.add(pIDfield);

    pQTYfield = new JTextField(10);
    pQTYfield.setBounds(110, 40, 50, 25);
    panel.add(pQTYfield);

    tabText = new JTextArea();
    tabText.setBounds(10, 125, 500, 200);
    tabText.setEditable(false);
    scroll = new JScrollPane(tabText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    panel.add(scroll);
    panel.add(tabText);

    addProduct = new JButton("Add to cart");
    addProduct.setBounds(200, 10, 150, 25);
    panel.add(addProduct);
    addProduct.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        int pID = Integer.parseInt(pIDfield.getText());
        int sID = Integer.parseInt(sIDfield.getText());
        int pQTY = Integer.parseInt(pIDfield.getText());
        warehouse.addToCart(Integer.parseInt(context.getUser()), pID, pQTY, sID);
        tabText.setEditable(true);
        tabText.setText(warehouse.showCart(Integer.parseInt(context.getUser())));
        tabText.setEditable(false);

        // adds product to the cart

      }
    });

    updateQuantity = new JButton("Update quantity");
    updateQuantity.setBounds(200, 40, 150, 25);
    panel.add(updateQuantity);
    updateQuantity.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        int pID = Integer.parseInt(pIDfield.getText());
        int pQTY = Integer.parseInt(pIDfield.getText());

        // update's product quantity

      }
    });

    removeProduct = new JButton("Remove from cart");
    removeProduct.setBounds(200, 70, 150, 25);
    panel.add(removeProduct);
    removeProduct.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        String pID = pIDfield.getText();

        // removes product from the cart, no matter the quantity

      }
    });
    back = new JButton("Back");
    back.setBounds(10, 600, 80, 25);
    panel.add(back);
    back.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        // go to previous state
      }
    });
    wareFrame.add(panel);
    wareFrame.pack();
    wareFrame.setVisible(true);
    // logout();
  }
}
