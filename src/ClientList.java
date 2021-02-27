
import java.util.*;
import java.io.*;

public class ClientList implements Serializable {
  private static final long serialVersionUID = 1L;
  public LinkedList<Client> clients = new LinkedList<>();
  private static ClientList clientList;
  ClientList() {
  }
  public static ClientList instance() {
    if (clientList == null) {
      return (clientList = new ClientList());
    } else {
      return clientList;
    }
  }

  public boolean insertClient(Client client) {
    clients.add(client);
    return true;
  }

  public Iterator getClient(){
     return clients.iterator();
  }
    
  public double makePayment(String cid, double amount){
     double remCredit = 0; 

     for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
        Client client = (Client) iterator.next();
            if (client.getcId().equals(cid)) {
              remCredit = client.makePayment(amount);
              break;
            }
        }

      return remCredit;
    }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(clientList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) throws ClassNotFoundException, IOException{
    try {
      if (clientList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (clientList == null) {
          clientList = (ClientList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  public String toString() {
    return clients.toString();
  }
}
