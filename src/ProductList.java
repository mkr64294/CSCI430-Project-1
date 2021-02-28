import java.util.*;

public class ProductList {
    public class SupplierQuantity {
        Supplier supID;
        double price;
        int inStock;
        Queue<Integer> clientWaitList;

        public SupplierQuantity(Supplier sup, double price, int stock) {// constructor
            supID = sup;
            this.price = price;
            inStock = stock;
            clientWaitList = new LinkedList<Integer>();
        }

        public SupplierQuantity(Supplier sup, double price) {// constructor
            supID = sup;
            this.price = price;
            inStock = 0;
            clientWaitList = new LinkedList<Integer>();
        }
    }

    public class ProductSupplier {
        Product product;
        LinkedList<SupplierQuantity> suppliers;

        public ProductSupplier(String pName) { // constructor
            product = new Product(pName);
            suppliers = new LinkedList<SupplierQuantity>();
        }

        public ProductSupplier(String pName, String pDescription) { // constructor
            product = new Product(pName, pDescription);
            suppliers = new LinkedList<SupplierQuantity>();
        }
    }

    private LinkedList<ProductSupplier> products; // this is the combination of everything
    // we need to know of the product

    public ProductList() { // default constructor
        products = new LinkedList<ProductSupplier>();
    }

    public boolean isProduct(String pName) { // product is in the listing
        int i;
        for (i = 0; i < products.size(); i++) {
            if (products.get(i).product.getPName() == pName) {
                return true;
            }
        }
        return false;
    }

    public double getPrice(String pName, Supplier sup) { // returns 0 if not found
        int i, j;
        for (i = 0; i < products.size(); i++) {
            if (products.get(i).product.getPName() == pName) {
                for (j = 0; j < products.get(i).suppliers.size(); j++) {
                    if (products.get(i).suppliers.get(j).supID == sup) {
                        return products.get(i).suppliers.get(j).price;
                    }
                }
            }
        }
        return 0;
    }

    public boolean isSupplier(Supplier sup, String pName) { // does the supplier supply the object?
        int i, j;
        for (i = 0; i < products.size(); i++) {
            if (products.get(i).product.getPName() == pName) {
                for (j = 0; j < products.get(i).suppliers.size(); j++) {
                    if (products.get(i).suppliers.get(j).supID == sup) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int indexProduct(String pName) { // returns -1 if not found
        int i;
        for (i = 0; i < products.size(); i++) {
            if (products.get(i).product.getPName() == pName) {
                return i;
            }
        }
        return -1;
    }

    public boolean addSupplier(Supplier sup, String pName, double sPrice, int stock) { // true means was not already in
                                                                                       // list
        SupplierQuantity sm = new SupplierQuantity(sup, sPrice, stock);
        if (!(this.isSupplier(sup, pName))) {
            products.get(indexProduct(pName)).suppliers.add(sm);
            return true;
        }
        return false;
    }

    public boolean addSupplier(Supplier sup, String pName, double sPrice) { // true means was not already in list
        SupplierQuantity sm = new SupplierQuantity(sup, sPrice);
        if (!(this.isSupplier(sup, pName))) {
            products.get(indexProduct(pName)).suppliers.add(sm);
            return true;
        }
        return false;
    }

    public boolean removeSupplier(String pName, Supplier sup) { // true means was in list and was removed;
        int indP = indexProduct(pName);
        if ((this.isSupplier(sup, pName))) {
            for (int i = 0; i < products.get(indP).suppliers.size(); i++) {
                if (products.get(indP).suppliers.get(i).supID == sup) {
                    products.get(indP).suppliers.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public int numSuppliers(String pName) { // returns number of suppliers for the given product
        int indP = indexProduct(pName);
        if (indP != -1) {
            return products.get(indP).suppliers.size();
        }
        return 0;
    }

    public boolean setPrice(String pName, Supplier sup, double newPrice) { // returns 0 if not found
        int i = indexProduct(pName), j;
        if (i != (-1)) {
            for (j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    products.get(i).suppliers.get(j).price = newPrice;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setDescription(String pName, String newDescription) { // sets a product description
        int i = indexProduct(pName);
        if (i != -1) {
            products.get(i).product.setDescription(newDescription);
            return true;
        }
        return false;
    }

    public String getDescription(String pName) {
        int i = indexProduct(pName);
        if (i != -1) {
            return (products.get(i).product.getDescription());
        }
        return "product not found.\n";

    }

    public boolean addProduct(String pName) { // adds a product the the product list
        int i = indexProduct(pName);
        ProductSupplier e = new ProductSupplier(pName);
        if (i != -1) {
            return false;
        }
        products.add(e);
        return true;
    }

    public boolean addProduct(String pName, String pDescription) { // adds a product to the product list
                                                                   // with a description
        int i = indexProduct(pName);
        ProductSupplier e = new ProductSupplier(pName, pDescription);
        if (i != -1) {
            return false;
        }
        products.add(e);
        return true;
    }

    public boolean removeProduct(String pName) { // removes a product from the product list
        int indP = this.indexProduct(pName);
        if (indP != -1) {
            products.remove(indP);
            return true;
        }
        return false;
    }

    public int getStock(Supplier sup, String pName) { // returns -9999999 if supplier does not exist
        int i = indexProduct(pName);
        if (isSupplier(sup, pName) && (i != (-1))) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    return products.get(i).suppliers.get(j).inStock;
                }
            }
        }
        return -9999999;

    }

    public boolean addToStock(Supplier sup, String pName, int amtToAdd) { // adds an amount of product to stock
        // for a given supplier
        int i = indexProduct(pName);
        if (isSupplier(sup, pName) && (i != (-1))) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    products.get(i).suppliers.get(j).inStock += amtToAdd;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeFromStock(Supplier sup, String pName, int amtToRemove) { // adds an amount of product
        // to stock for a given supplier
        int i = indexProduct(pName);
        if (isSupplier(sup, pName) && (i != (-1))) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    products.get(i).suppliers.get(j).inStock -= amtToRemove;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setStock(Supplier sup, String pName, int amtToSet) { // sets the stock for a product from a supplier
        int i = indexProduct(pName);
        if (isSupplier(sup, pName) && (i != (-1))) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    products.get(i).suppliers.get(j).inStock = amtToSet;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addToWaitlist(int clientID, String pName, Supplier sup) { // adds clients to a suppliers product
                                                                             // waitlist
        int i = indexProduct(pName);
        if (i == -1) {
            return false;
        }
        if (isSupplier(sup, pName)) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    products.get(i).suppliers.get(j).clientWaitList.add(clientID);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeFromWaitlist(int clientID, String pName, Supplier sup) { // removes clients from a suppliers
                                                                                  // product waitlist
        int i = indexProduct(pName);
        if (i == -1) {
            return false;
        }
        if (isSupplier(sup, pName)) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    products.get(i).suppliers.get(j).clientWaitList.remove(clientID);
                    return true;
                }
            }
        }
        return false;
    }

    public int popWaitlist(Supplier sup, String pName) { // returns -1 if product not found
        // -2 if supplier not found
        // otherwise returns client ID
        int i = indexProduct(pName);
        if (i == -1) {
            return -1;
        }
        if (isSupplier(sup, pName)) {
            for (int j = 0; j < products.get(i).suppliers.size(); j++) {
                if (products.get(i).suppliers.get(j).supID == sup) {
                    return products.get(i).suppliers.get(j).clientWaitList.remove();
                }
            }
        }
        return -2;
    }

}
