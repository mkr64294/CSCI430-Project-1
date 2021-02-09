import java.util;
import java.util.LinkedList;

public class Supplier{

    private String sName;
    private int sId;
    private LinkedList<String> pList;


    private int searchList(){


    }

// constructor for the supplier taking in the supplier name and id
    public Supplier(String sName, int sId){

        this.sName = sName;
        this.sId = sId;
        this.pList = new LinkedList<int>();

    }

// changing the supplier's name
    public void changeSName(String newName){

        this.sName = newName;
        return;
    }
// adding an item to the product list
    public void addItem(String pName){

        pList.add(pName);
        return;
    }
// removes item from the product list
    public boolean removeItem(String pName){
        

    }
// gets the supplier's name
    public String getSName(){

        return sName;
    }

    public int getSId(){

        return sId;
    }

// views the list of products 
    public void viewProductList(){


    }

