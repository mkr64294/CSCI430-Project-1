import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class ShoppingCartButton extends JButton implements ActionListener {
  // private static ShoppingCartButton instance;
  // private JButton userButton;
  public ShoppingCartButton() {
    super("ShoppingCart");
    this.setListener();
  }

  /*
   * public static ShoppingCartButton instance() { if (instance == null) {
   * instance = new ShoppingCartButton(); } return instance; }
   */

  public void setListener() {
    // System.out.println("In ShoppingCartButton setListener\n");
    this.addActionListener(this);
  }

  public void actionPerformed(ActionEvent event) {
    // System.out.println("In ShoppingCart \n");
    (WareContext.instance()).setLogin(WareContext.IsShoppingCart);
    Loginstate.instance().clear();
    (WareContext.instance()).changeState(WareContext.CART_STATE);
  }
}
