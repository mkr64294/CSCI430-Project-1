package src;
import java.util.*;

public class ProductList {
    public class SupplierMini{
        int supID;
        double price;
        int inStock;
        Queue<Integer> clientWaitList;
        public SupplierMini(int sID,double price,int stock){
            supID = sID;
            this.price = price;
            inStock = stock;
            clientWaitList = new LinkedList<Integer>();
        }
        public SupplierMini(int sID,double price){
            supID = sID;
            this.price = price;
            inStock = 0;
            clientWaitList = new LinkedList<Integer>();
        }
    }
    public class Entry{
        Product product;
        LinkedList<SupplierMini> suppliers;
        public Entry(String pName){
            product = new Product(pName);
            suppliers = new LinkedList<SupplierMini>();
        }
        public Entry(String pName, String pDescription){
            product = new Product(pName, pDescription);
            suppliers = new LinkedList<SupplierMini>();
        }
    }
    private LinkedList<Entry> products;
    public ProductList(){
        products = new LinkedList<Entry>();
    }
    public boolean isProduct(String pName){
        int i;
        for(i = 0; i < products.size(); i++){
            if(products.get(i).product.getPName() == pName){
                return true;
            }
        }
        return false;
    }
    public double getPrice(String pName, int sID){ //returns 0 if not found
        int i, j;
        for(i = 0; i < products.size(); i++){
            if(products.get(i).product.getPName() == pName){
                for(j = 0; j < products.get(i).suppliers.size(); j++){
                    if(products.get(i).suppliers.get(j).supID == sID){
                        return products.get(i).suppliers.get(j).price;
                    }
                }
            }
        }
        return 0;
    }
    public boolean isSupplier(int sID, String pName){
        int i, j;
        for(i = 0; i < products.size(); i++){
            if(products.get(i).product.getPName() == pName){
                for(j = 0; j < products.get(i).suppliers.size(); j++){
                    if(products.get(i).suppliers.get(j).supID == sID){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int indexProduct(String pName){ //returns -1 if not found
        int i;
        for(i = 0; i < products.size(); i++){
            if(products.get(i).product.getPName() == pName){
                return i;
            }
        }
        return -1;
    }
    public boolean addSupplier(int sID,String pName,double sPrice, int stock){ //true means was not already in list
        SupplierMini sm = new SupplierMini(sID,sPrice,stock);
        if(!(this.isSupplier(sID, pName))){
            products.get(indexProduct(pName)).suppliers.add(sm);
            return true;
        }
        return false;
    }
    public boolean addSupplier(int sID,String pName,double sPrice){ //true means was not already in list
        SupplierMini sm = new SupplierMini(sID,sPrice);
        if(!(this.isSupplier(sID, pName))){
            products.get(indexProduct(pName)).suppliers.add(sm);
            return true;
        }
        return false;
    }
    public boolean removeSupplier(String pName, int sID){ //true means was in list and was removed;
        int indP = indexProduct(pName);
        if((this.isSupplier(sID, pName))){
            for(int i = 0; i < products.get(indP).suppliers.size(); i++){
                if(products.get(indP).suppliers.get(i).supID == sID){
                    products.get(indP).suppliers.remove(i);
                    return true;
                }
            }            
        }
        return false;
    }
    public int numSuppliers(String pName){
        int indP = indexProduct(pName);
        if(indP != -1){
            return products.get(indP).suppliers.size();
        }
        return 0;
    }
    public boolean setPrice(String pName, int sID, double newPrice){ //returns 0 if not found
        int i = indexProduct(pName), j;
        if(i != (-1)){
            for(j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    products.get(i).suppliers.get(j).price = newPrice;
                    return true;                    
                }
            }
        }
        return false;
    }
    public boolean setDescription(String pName, String newDescription){
        int i = indexProduct(pName);
        if(i != -1){
            products.get(i).product.setDescription(newDescription);
            return true;
        }
        return false;
    }
    public String getDescription(String pName){
        int i = indexProduct(pName);
        if(i != -1){
            return (products.get(i).product.getDescription());
        }
        return "product not found.\n";
        
    }
    public boolean addProduct(String pName){
        int i = indexProduct(pName);
        Entry e = new Entry(pName);
        if(i != -1){
            return false;
        }
        products.add(e);
        return true;
    }
    public boolean addProduct(String pName, String pDescription){
        int i = indexProduct(pName);
        Entry e = new Entry(pName, pDescription);
        if(i != -1){
            return false;
        }
        products.add(e);
        return true;
    }
    public boolean removeProduct(String pName){
        int indP = this.indexProduct(pName);
        if(indP != -1){
            products.remove(indP);
            return true;
        }
        return false;
    }
    public int getStock(int sID, String pName){ //returns -9999999 if supplier does not exist
        int i = indexProduct(pName);
        if(isSupplier(sID, pName) && (i !=(-1))){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    return products.get(i).suppliers.get(j).inStock;
                }
            }
        }
        return -9999999;

    }
    public boolean addToStock(int sID, String pName, int amtToAdd){
        int i = indexProduct(pName);
        if(isSupplier(sID, pName) && (i !=(-1))){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    products.get(i).suppliers.get(j).inStock += amtToAdd;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean removeFromStock(int sID, String pName, int amtToRemove){
        int i = indexProduct(pName);
        if(isSupplier(sID, pName) && (i !=(-1))){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    products.get(i).suppliers.get(j).inStock -= amtToRemove;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean setStock(int sID, String pName, int amtToSet){
        int i = indexProduct(pName);
        if(isSupplier(sID, pName) && (i !=(-1))){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    products.get(i).suppliers.get(j).inStock = amtToSet;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean addToWaitlist(int clientID, String pName, int sID){
        int i = indexProduct(pName);
        if(i == -1){
            return false;
        }
        if(isSupplier(sID, pName)){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    products.get(i).suppliers.get(j).clientWaitList.add(clientID);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean removeFromWaitlist(int clientID, String pName, int sID){
        int i = indexProduct(pName);
        if(i == -1){
            return false;
        }
        if(isSupplier(sID, pName)){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    products.get(i).suppliers.get(j).clientWaitList.remove(clientID);
                    return true;
                }
            }
        }
        return false;
    }
    public int popWaitlist(int sID, String pName){  //returns -1 if product not found
                                                    //        -2 if supplier not found
                                                    //otherwise returns client ID
        int i = indexProduct(pName);
        if(i == -1){
            return -1;
        }
        if(isSupplier(sID, pName)){
            for(int j = 0; j < products.get(i).suppliers.size(); j++){
                if(products.get(i).suppliers.get(j).supID == sID){
                    return products.get(i).suppliers.get(j).clientWaitList.remove();
                }
            }
        }
        return -2;
    }

    
}
