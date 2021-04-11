import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class LoginStateGUI extends WareStateGUI{
  private static JLabel clientJL;
  private static JLabel salesJL;
  private static JLabel managerJL;
  private static JLabel password1JL;
  private static JLabel password2JL;
  private static JLabel clientIDJL;
  private static JLabel sUserJL;
  private static JLabel mUserJL;

  private static JTextField userClient;
  private static JTextField userSales;
  private static JTextField userManager;

  private static JPasswordField sPass;
  private static JPasswordField mPass;

  private static JButton clientLogin;
  private static JButton salesLogin;
  private static JButton managerLogin;
  private static JButton back;

  private static JPanel panel;

  private static final int MANAGER_LOGIN = 0;
  private static final int CLERK_LOGIN = 1;
  private static final int CLIENT_LOGIN = 2;
  private static final int EXIT = 3;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  // private WareContext context;
  private static LoginStateGUI instance;

  private LoginStateGUI() {
    super();

    clientLogin.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {

        String userName = userClient.getText();
        if (warehouse.isClient(Integer.parseInt(userName))) {
          (context).setLogin(WareContextGUI.IsClient);
          (context).setUser(userName);
          (context).changeState(2);
        }
        
      }
    });
    salesLogin.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
          String userName = userSales.getText();  
          char password[] = sPass.getPassword(); 
          if(userName == "username" && password.toString() == "password"){
              (context).setLogin(WareContextGUI.IsSales);
              (context).changeState(1);
          }
          else
          {
            userSales.setText("");
            sPass.setText("");
          }
      }
    });

    managerLogin.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
          String userName = userManager.getText();  
          char password[] = mPass.getPassword(); 

          if(userName == "username" && password.toString() == "password"){
            (context).setLogin(WareContextGUI.IsManager);
            (context).changeState(0);
          }
          else
          {
            userManager.setText("");
            mPass.setText("");
          }

      }
    });
    back.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
          //go to previous state
      }
    });

    panel = new JPanel();
    (WareContextGUI.wareFrame).add(panel);
    panel.setLayout(null);
    clientJL = new JLabel("Client ID:");
    clientJL.setBounds(10, 10, 80, 25);
    panel.add(clientJL);

    userClient = new JTextField(6);
    userClient.setBounds(100, 10, 100, 25);
    panel.add(userClient);
    //userClient.setDocument(new JTextFieldLimit(4));
    //userClient.jTextFieldNumKeyTyped(java.awt.event.KeyEvent.KEY_TYPED);

    clientLogin = new JButton("Login");
    clientLogin.setBounds(10, 40, 80, 25);
    panel.add(clientLogin);

    salesJL = new JLabel("Sales ID:");
    salesJL.setBounds(10, 70, 80, 25);
    panel.add(salesJL);

    userSales = new JTextField(20);
    userSales.setBounds(100, 70, 100, 25);
    panel.add(userSales);

    password1JL = new JLabel("Password:");
    password1JL.setBounds(10, 100, 80, 25);
    panel.add(password1JL);

    sPass = new JPasswordField(20);
    sPass.setBounds(100, 100, 100, 25);
    panel.add(sPass);
    
    salesLogin = new JButton("Login");
    salesLogin.setBounds(10, 130, 80, 25);
    panel.add(salesLogin);
    
    managerJL = new JLabel("Manager ID:");
    managerJL.setBounds(10, 160, 80, 25);
    panel.add(managerJL);

    userManager = new JTextField(20);
    userManager.setBounds(100, 160, 100, 25);
    panel.add(userManager);

    password2JL = new JLabel("Password:");
    password2JL.setBounds(10, 190, 80, 25);
    panel.add(password2JL);

    mPass = new JPasswordField(20);
    mPass.setBounds(100, 100, 190, 25);
    panel.add(mPass);
    
    salesLogin = new JButton("Login");
    salesLogin.setBounds(10, 220, 80, 25);
    panel.add(salesLogin);

    (WareContextGUI.wareFrame).setVisible(true);

        // context = WareContext.instance();
  }

  public static LoginStateGUI instance() {
    if (instance == null) {
      instance = new LoginStateGUI();
    }
    return instance;
  }

  public void run(){
    instance();
  }

  /*private void jTextFieldNumKeyTyped(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
      evt.consume();
    }
  }*/

  class JTextFieldLimit extends PlainDocument {
    private int limit;

    JTextFieldLimit(int limit) {
      super();
      this.limit = limit;
    }

    JTextFieldLimit(int limit, boolean upper) {
      super();
      this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (str == null)
        return;
      if ((getLength() + str.length()) <= limit) {
        super.insertString(offset, str, attr);
      }
    }

    // once you've added the JTextField to the GUI, you need to run this line of
    // textfieldname.setDocument(new JTextFieldLimit(*insert the number of characters here*));    // textfieldname.setDocument(new JTextFieldLimit(*insert the number of
    // characters here*));
  }
}
