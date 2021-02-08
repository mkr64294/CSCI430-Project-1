import java.util;

public class Product{
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

}