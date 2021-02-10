package src;

public class ProductTester {
    public static void main(String[] args){
        ProductList pl = new ProductList();
        Product p = new Product("Shoes12331", "Generic shoes");
        pl.addProduct("Shoes12331", "Generic shoes");
        System.out.println("The Product name should be Shoes12331. ID: " + p.getPName());
        System.out.println("The Product description should be Generic Shoes. Description: " + p.getPName());
        pl.addSupplier(12345, "Shoes12331", 59.99, 7);
        System.out.println("Amount in stock should be 7: " + pl.getStock(12345, "Shoes12331"));
        pl.removeFromStock(12345, "Shoes12331", 2);
        System.out.println("Amount in stock should be 5: " + pl.getStock(12345, "Shoes12331"));
        pl.addToStock(12345, "Shoes12331", 2);
        System.out.println("Amount in stock should be 7: " + pl.getStock(12345, "Shoes12331"));
        pl.addSupplier(1234, "Shoes12331", 45.00, 2);
        System.out.println("Number of suppliers should be 2: " + pl.numSuppliers("Shoes12331"));
        

    }
}
