/* Author: Isaiah Ryman
 * Date: 2/8/2021
 * Class: CSCI430
 * Description:
 */

package GUIsrc;

import java.io.*;

public class Product implements Serializable {
    private String pName; // unique identifier not to be changed
    private String description;
    private int pID;

    public Product(String pName, int pID) { // the pName should be referenced
        // by the product list before
        // constructing to check for duplicates
        this.pID = pID;
        this.pName = pName;
        this.description = "no description.";
    }

    public Product(String pName, String pdescription, int pID) { // the pName should be referenced
        // by the product list before
        this.pID = pID; // constructing to check for duplicates
        this.pName = pName;
        this.description = pdescription;
    }

    public int getPID() {
        return pID;
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