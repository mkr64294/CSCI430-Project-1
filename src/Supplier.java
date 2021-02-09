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

        thi.sName = newName;
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







    private String pName;  //unique identifier
    private int stockQty;  //negative means there are reservations on the item
    private LinkedList<int> sList;  //list of suppliers with the product

    public Product(String pName){   //the pName should be referenced 
                                    //by the global product list before constructing to check for duplicates
        this.pName = pName;
        this.stockQty = 0;
        this.sList = new LinkedList<int>();
    }
    public void nameItem(String newPName){
        this.pName = newPName;
        return;
    }
    public boolean inStock(){
        if(this.stockQty > 0){
            return true
        }
        return false;
    }
    public int getStock(){ //amount in stock
        return this.stockQty;
    }
    public void addStock(int qty){
        this.stockQty += qty;
        return;
    }
    public boolean removeStock(int qty){
        this.stockQty += (qty * -1);
    }