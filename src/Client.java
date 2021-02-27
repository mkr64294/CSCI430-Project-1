import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String MEMBER_STRING = "C";
  private String cId;
  private String cName; 
  private String cAddress;
  private double credit;
  
  public Client(String cName, String cAddress) {
    this.cName = cName;  
    this.cAddress = cAddress;
    this.credit = 0.00; 
    cId = CLIENT_STRING + (ClientIDServer.instance()).getcId();     
  }

    public Client(){};
    
    public int getcId(){
      return cId;
    }

    public String getcName(){
      return cName;
    }

    public String getcAddress(){
      return cAddress;
    }

    public double getCredit(){
      return credit;
    }

    public void changeAddress(String newAddress){
      this.cAddress = newAddress;
    }
  
    public void addCredit(double amount) {
      this.credit += amount;
    }
    
    public double makePayment(double amount) {
      return this.credit -= amount;
    }
    
    public void removeCredit(double amount) {
      this.credit -= amount;
    }
    
    public void changeName(String newName){
      this.cName = newName;
    }

    public String toString() {
      String string = " \n Client name:  " + cName + " \n address : " + cAddress + " \n id : " + cId;
      return string;
    }
}



  









  