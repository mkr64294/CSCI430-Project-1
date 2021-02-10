// Mario Quintero


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

// views the list of products that are in the suppler's list of products
    public void viewProductList(){

        for (int i = 0; i < pList.size(); i++) { 
  
            System.out.println(pList.get(i));
        } 

    }


}

