public class ProductTester {
    public static void main(String[] args){
        ProductList pl = new ProductList();
        Product p = new Product("Shoes12331", "Generic shoes");
        pl.addProduct("Shoes12331", "Generic shoes");
        System.out.println("The Product name should be Shoes12331. ID: " + p.getPName());
        System.out.println("The Product description should be Generic Shoes. Description: " + p.getDescription());
        pl.addSupplier(12345, "Shoes12331", 59.99, 7);
        System.out.println("Amount in stock should be 7: " + pl.getStock(12345, "Shoes12331"));
        pl.removeFromStock(12345, "Shoes12331", 2);
        System.out.println("Amount in stock should be 5: " + pl.getStock(12345, "Shoes12331"));
        pl.addToStock(12345, "Shoes12331", 2);
        System.out.println("Amount in stock should be 7: " + pl.getStock(12345, "Shoes12331"));
        pl.addSupplier(1234, "Shoes12331", 45.00, 2);
        System.out.println("Number of suppliers should be 2: " + pl.numSuppliers("Shoes12331"));
        if(pl.removeSupplier("Shoes12331", 12345)){
			System.out.println("Number of suppliers should be 1: " + pl.numSuppliers("Shoes12331"));
        }
        if(pl.isProduct("Shoes12331")){
            System.out.println("Shoes12331 is a product");
        }
        else{
            System.out.println("Shoes12331 is not a product");
        }
        if(pl.isProduct("Anvil122")){
            System.out.println("Anvil122 is a product");
        }
        else{
            System.out.println("Anvil122 is not a product");
        }
        System.out.println("Price of Shoes12331 from supplier 1234 is " + pl.getPrice("Shoes12331", 1234));
        if(pl.setPrice("Shoes12331", 1234, 50.00)){
            System.out.printf("Shoes12331 from supplier 1234 now costs %.2f\n", pl.getPrice("Shoes12331", 1234));
        }
        else{
            System.out.println("failed to change price");
        }
        if(pl.setDescription("Shoes12331", "Less generic")){
            System.out.println("New description added to Shoes12331: " + pl.getDescription("Shoes12331"));
        }
        else{
            System.out.println("Failed to update description of Shoes12331");
        }
        if(pl.addToWaitlist(12, "Shoes12331", 1234)){
            System.out.println("added client 12 to waitlist for shoes sold by supplier 1234");
        }
        else{
            System.out.println("failed to add client to waitlist");
        }
        if(pl.removeFromWaitlist(12, "Shoes12331", 1234)){
            System.out.println("removed client 12 from waitlist for shoes sold by supplier 1234");
        }
        else{
            System.out.println("failed to remove client from waitlist");
        }
        if(pl.addToWaitlist(15, "Shoes12331", 1234)){
            System.out.println("added client 15 to waitlist for shoes sold by supplier 1234");
        }
        else{
            System.out.println("failed to add client to waitlist");
        }
        System.out.println("client number " + pl.popWaitlist(1234, "Shoes12331") + " removed from waitlist");

    }
}
