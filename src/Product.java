
/** Author: Isaiah Ryman
  * Date: 2/8/2021
  * Class: CSCI430
  * Description: 
  */

import java.io.*;

public class Product implements Serializable {
    private String pName; // unique identifier not to be changed
    private String description;

    public Product(String pName) { // the pName should be referenced
                                   // by the product list before
                                   // constructing to check for duplicates
        this.pName = pName;
        this.description = "no description.";
    }

    public Product(String pName, String pdescription) { // the pName should be referenced
                                                        // by the product list before
                                                        // constructing to check for duplicates
        this.pName = pName;
        this.description = pdescription;
    }

    public String getPName() { // returns product name
        return this.pName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
        return;
    }
}
