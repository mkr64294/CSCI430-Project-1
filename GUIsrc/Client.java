import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private int cId;
  private String cName;
  private String cAddress;

  public Client(String cName, String cAddress, int clientID) {
    this.cName = cName;
    this.cAddress = cAddress;
    cId = clientID;
  }

  public int getcId() {
    return cId;
  }

  public String getcName() {
    return cName;
  }

  public String getcAddress() {
    return cAddress;
  }

  public void changeAddress(String newAddress) {
    this.cAddress = newAddress;
  }

  public void changeName(String newName) {
    this.cName = newName;
  }

  public String toString() {
    String string = " \n Client name:  " + cName + " \n address : " + cAddress + " \n id : " + cId;
    return string;
  }

}
