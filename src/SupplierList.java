
// Mario Quintero
// SupplierList Class


import java.util.*;
import java.io.*;

public class SupplierList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Supplier> suppliers = new LinkedList<Supplier>();
  private static SupplierList SupplierList;
  SupplierList() {
  }
  public static SupplierList instance() {
    if (SupplierList == null) {
      return (SupplierList = new SupplierList());
    } else {
      return SupplierList;
    }
  }

  public boolean insertSupplier(Supplier supplier) {
    suppliers.add(supplier);
    return true;
  }

  // An Iterator that will go through each Supplier in Supplier list
  public Iterator<Supplier> getSupplier(){
     return suppliers.iterator();
  }
  
  // Will Print to screen the information from the Suppliers
  public String toString() {
    return suppliers.toString();
  }
}
