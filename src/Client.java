import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private int cId;
  private String cName; 
  private String cAddress;
  private double credit;
  private static final String MEMBER_STRING = "C";
 
  public Client(int cId, String cName) {
    this.cId= cId;   
    this.cName = cName;        
  }

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



  









  