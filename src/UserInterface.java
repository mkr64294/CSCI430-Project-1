
/**
 * Author: Mark Rice Date: 2/8/2021 Class: CSCI430 Description: UI Class that
 * runs all the functions from the client, product, and supplier classes written
 * by the other member of my group
 */

class UserInterface {

  public static void main(String[] args) {

    // initialize some clients, suppliers, and products
    Client c1 = new Client(13579, "Tom Jones");
    Client c2 = new Client(24680, "Gerald Thompson");
    Supplier s1 = new Supplier("John's Shop Manufacturer", 97531);
    Supplier s2 = new Supplier("Bob & Joe's Electronics", 86420);

    // These aren't necessary since the ProductList class created these products for
    // me, but I'll include them here for reference
    /*
     * Product p1 = new Product("Phillips DX467 Large Screwdriver"); Product p2 =
     * new Product("GVT7000 Double-Sided Razor Blades"
     * ,"Five-inch long retractable razor blade with rubber handle");
     */

    // initialize and populate ClientList, ProductList, and SupplierList
    ClientList cList = new ClientList();
    cList.insertClient(c1);
    cList.insertClient(c2);

    ProductList pList = new ProductList();
    pList.addProduct("Phillips DX467 Large Screwdriver");
    pList.addProduct("GVT7000 Double-Sided Razor Blades", "Five-inch long retractable razor blade with rubber handle");

    // SupplierList has not been created, but when it is, this is what it would
    // say...
    /*
     * SupplierList sList = new SupplierList(); sList.insertClient(s1);
     * sList.insertClient(s2);
     */

    // In this section, I'll run all the available client functions
    c1.changeAddress("1234 56th St. Alexandria, MN 56308");
    c1.addCredit(5600.00);
    System.out.println(c1.toString() + " \n Credit : $" + c1.getCredit());
    c1.removeCredit(2200);
    c1.changeName("Terry Ryan");
    // To test the client getters
    System.out.println(c1.getcName() + ", " + c1.getcId() + ", " + c1.getcAddress() + ", $" + c1.getCredit());
    // Test ClientList toString
    System.out.println(cList.toString());

    // Now I'll test the supplier functions
    System.out.println("Supplier name : " + s1.getSName() + "\nSupplier ID : " + s1.getSId());
    s1.changeSName("Kevin's Shop Manufacturer");
    s1.addItem("Phillips DX467 Large Screwdriver");
    s1.viewProductList();
    s1.removeItem("Phillips DX467 Large Screwdriver"); // returns a boolean true
    s1.viewProductList();

    // Now I'll test the product functions (there's a lot here)

    System.out.println(pList.isProduct("Phillips DX467 Large Screwdriver") + "     (should be true)"); // returns a
                                                                                                       // boolean true
    System.out.println(pList.isProduct("GVT7000 Double-Sided Razor Blades") + "     (should be true)"); // returns a
                                                                                                        // boolean true
    System.out.println(pList.isProduct("Screwdriver") + "     (should be false)"); // returns a boolean false

    System.out.println(pList.addSupplier(555, "Phillips DX467 Large Screwdriver", 9.99, 150) + "     (should be true)"); // returns
                                                                                                                         // a
                                                                                                                         // boolean
                                                                                                                         // true
    System.out.println(pList.addSupplier(876, "Phillips DX467 Large Screwdriver", 8.79) + "     (should be true)"); // returns
                                                                                                                    // a
                                                                                                                    // boolean
                                                                                                                    // true
    System.out.println(pList.addSupplier(876, "Phillips DX467 Large Screwdriver", 8.39) + "     (should be false)"); // returns
                                                                                                                     // a
                                                                                                                     // boolean
                                                                                                                     // false

    System.out.println(pList.setPrice("Phillips DX467 Large Screwdriver", 555, 9.39) + "     (should be true)"); // returns
                                                                                                                 // a
                                                                                                                 // boolean
                                                                                                                 // true
    System.out.println(pList.getPrice("Phillips DX467 Large Screwdriver", 555) + "     (should be 9.39)"); // returns a
                                                                                                           // double
                                                                                                           // 9.39

    System.out.println(pList.isSupplier(555, "Phillips DX467 Large Screwdriver") + "     (should be true)"); // returns
                                                                                                             // a
                                                                                                             // boolean
                                                                                                             // true
    System.out.println(pList.isSupplier(555, "GVT7000 Double-Sided Razor Blades") + "     (should be false)"); // returns
                                                                                                               // a
                                                                                                               // boolean
                                                                                                               // false
    System.out.println(pList.indexProduct("Phillips DX467 Large Screwdriver") + "     (should be 0)"); // returns an int
                                                                                                       // 0

    System.out.println(pList.numSuppliers("Phillips DX467 Large Screwdriver") + "     (should be 2)"); // returns an int
                                                                                                       // 2
    System.out.println(pList.setDescription("Phillips DX467 Large Screwdriver",
        "A six-inch long heavy-huty Phillips screwdriver with up to 42 lbs. of torque") + "     (should be true)"); // returns
                                                                                                                    // a
                                                                                                                    // boolean
                                                                                                                    // true
    System.out.println(pList.getDescription("Phillips DX467 Large Screwdriver")); // returns a String "A six-inch long
                                                                                  // heavy-huty Phillips screwdriver
                                                                                  // with up to 42 lbs. of torque"

    System.out.println(pList.getStock(555, "Phillips DX467 Large Screwdriver") + "     (should be 150)"); // returns an
                                                                                                          // int 150
    System.out.println(pList.getStock(876, "Phillips DX467 Large Screwdriver") + "     (should be 0)"); // returns an
                                                                                                        // int 0
    System.out.println(pList.addToStock(876, "Phillips DX467 Large Screwdriver", 75) + "     (should be true)"); // returns
                                                                                                                 // a
                                                                                                                 // boolean
                                                                                                                 // true
    System.out.println(pList.removeFromStock(555, "Phillips DX467 Large Screwdriver", 37) + "     (should be true)"); // returns
                                                                                                                      // a
                                                                                                                      // boolean
                                                                                                                      // true
    System.out.println(pList.getStock(555, "Phillips DX467 Large Screwdriver") + "     (should be 113)"); // returns an
                                                                                                          // int 113
    System.out.println(pList.setStock(555, "Phillips DX467 Large Screwdriver", 200) + "     (should be true)"); // returns
                                                                                                                // a
                                                                                                                // boolean
                                                                                                                // true

    System.out.println(pList.addToWaitlist(56, "Phillips DX467 Large Screwdriver", 876) + "     (should be true)"); // returns
                                                                                                                    // a
                                                                                                                    // boolean
                                                                                                                    // true
    System.out.println(pList.addToWaitlist(12, "Phillips DX467 Large Screwdriver", 876) + "     (should be true)"); // returns
                                                                                                                    // a
                                                                                                                    // boolean
                                                                                                                    // true
    System.out.println(pList.addToWaitlist(87, "Phillips DX467 Large Screwdriver", 876) + "     (should be true)"); // returns
                                                                                                                    // a
                                                                                                                    // boolean
                                                                                                                    // true
    System.out.println(pList.removeFromWaitlist(56, "Phillips DX467 Large Screwdriver", 876) + "     (should be true)"); // returns
                                                                                                                         // a
                                                                                                                         // boolean
    // true
    System.out.println(pList.popWaitlist(876, "Phillips DX467 Large Screwdriver") + "     (should be 12)"); // returns
                                                                                                            // an int 12

    System.out.println(pList.removeSupplier("Phillips DX467 Large Screwdriver", 222) + "     (should be false)"); // returns
                                                                                                                  // a
                                                                                                                  // boolean
                                                                                                                  // false
    System.out.println(pList.removeSupplier("Phillips DX467 Large Screwdriver", 876) + "     (should be true)"); // returns
                                                                                                                 // a
                                                                                                                 // boolean
                                                                                                                 // True

    System.out.println(pList.removeProduct("Screwdriver") + "     (should be false)"); // returns a boolean false
    System.out.println(pList.removeProduct("Phillips DX467 Large Screwdriver") + "     (should be true)"); // returns a
                                                                                                           // boolean
                                                                                                           // true
    System.out.println(pList.removeProduct("Phillips DX467 Large Screwdriver") + "     (should be false)"); // returns a
                                                                                                            // boolean
                                                                                                            // false
    System.out.println(pList.addProduct("Phillips DX467 Large Screwdriver") + "     (should be true)"); // returns a
                                                                                                        // boolean true,
                                                                                                        // adds the
    // screwdriver back to the list of
    // products

  }
}