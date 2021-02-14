import java.util.*;


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


        System.out.println("Now testing SupplierList Class");

        SupplierList sList = new SupplierList();

        Supplier s2 = new Supplier("Peter", 4428);

        sList.insertSupplier(s1);

        sList.insertSupplier(s2);

        System.out.println("Testing toString");
        System.out.println(sList.toString());


        System.out.println("Testing Iterator");
        Iterator<Supplier> allSuppliers = sList.getSupplier();
        while (allSuppliers.hasNext()){
	     Supplier supplier = (Supplier)(allSuppliers.next());
        System.out.println(supplier.toString());
        }

        System.out.println("Adding a single product to all Suppliers");
        
        Iterator<Supplier> addSuppliers = sList.getSupplier();
        while (addSuppliers.hasNext()){
	     Supplier supplier = (Supplier)(addSuppliers.next());
        supplier.addItem("Item 50");
        }
        
        System.out.println("Single Item should be added now all suppliers carry Item 50");
        Iterator<Supplier> newSuppliers = sList.getSupplier();
        while (newSuppliers.hasNext()){
	     Supplier supplier = (Supplier)(newSuppliers.next());
        System.out.println(supplier.toString());
        }
        

       }
}
