// Mario Quintero
// Supplier class


import java.util.LinkedList;

public class Supplier{

    private String sName;
    private int sId;
    private LinkedList<String> pList;

// Private method to look through a list for an entered name
    private int searchList(String item){
        
        
        int position = -1;
        
        for (int i = 0; i < pList.size(); i++) { 
  
            
            String name = pList.get(i); 
  
            
            if (name == item) { 
  
                position = i;
                break; 
            } 
        } 
        return position; 

    } 

// constructor for the supplier taking in the supplier name and id
    public Supplier(String sName, int sId){

        this.sName = sName;
        this.sId = sId;
        this.pList = new LinkedList<String>();

    }

// changing the supplier's name, takes in the new name for the supplier
    public void changeSName(String newName){

        this.sName = newName;
        return;
    }
// adding an item to the product list, takes in a String with name of the product to add
    public void addItem(String pName){

        pList.add(pName);
        return;
    }
// removes item from the product list will return true if successful and false if product could not be found in list
    public boolean removeItem(String pName){
        
        int location = this.searchList(pName);
        boolean isSuccessful;

        if(location == -1){

            isSuccessful = false;
        }
        else{

            pList.remove(location);
            isSuccessful = true;
        }
        
        return isSuccessful;

    }
// gets the supplier's name
    public String getSName(){

        return sName;
    }
// gets the supplier's ID
    public int getSId(){

        return sId;
    }

// **UPDATED** viewProductList now returns a string of either all products
// or saying there are no products
// this is to allow integration with toString() printing all supplier information including 
// products they carry
    public String viewProductList(){
        String products ="";

        if(pList.isEmpty()){
            products = "No Products are added";
            
        }
        else{

            for (int i = 0; i < pList.size(); i++) { 
  
                products = products + " " + pList.get(i);
            } 

            
        }
        return products;
    }

    // Will print all info about this single supplier
    public String toString(){

        String string = "Name: " + sName + " Id: " + sId +
                        "\nProducts: " + viewProductList();
        return string;


    }


}

