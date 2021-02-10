

public class SupplierTester {
    public static void main(String[] args) {
  
        Supplier s1 = new Supplier("Joe", 3346);

        System.out.println("The ID should be 3346. ID: " + s1.getSId());

        System.out.println("The Name should be Joe. Name: " + s1.getSName());

        System.out.println("The product list should say No Product is Added");
        s1.viewProductList();

        s1.changeSName("Mario");

        System.out.println("The Name now should be Mario. Name: " + s1.getSName());

        s1.addItem("Item1");
        s1.addItem("Item2");
        s1.addItem("Item3");

        System.out.println("The product list should now say Item1, Item2, Item3");
       s1.viewProductList();

        boolean isRemoved;

       isRemoved = s1.removeItem("Item4");
       System.out.println("A failure to remove Item4 should have happened\n Success? " + isRemoved );

       isRemoved = s1.removeItem("Item2");
       System.out.println("A Success to remove Item2 should have happened\n Success? " + isRemoved );


       System.out.println("The product list after removing Item2 should be Item1, Item3");
       s1.viewProductList();

       System.out.println("\nIf everything matched up then Supplier Methods are working");

       }
}
