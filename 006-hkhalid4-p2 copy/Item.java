// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
// --------------------------------------------------------

import java.text.DecimalFormat;

/**
 * This class creates the items for sale in the store.
 * @author hudakhalid
 *
 */
public class Item {
	
	/**
	 * the name of the item.
	 */
	private String itemName;
	
    /**
     * the price in type double of the item.
     */
    private double itemPrice;
    
    /**
     * sets the format of the double price.
     */
    DecimalFormat dformat = new DecimalFormat("#.00");

    /**
     * empty constructor which just creates the item with no name or price.
     */
    public Item() {}

    /**
     * constructor for the item class which creates the item.
     * @param i is the item being passed in 
     */
    public Item(Item i) {
        this.itemName = i.itemName;
        this.itemPrice = i.itemPrice;
    }

    /**
     * constructor which creates an item with the name and itemPrice being passed.
     * @param name - name of the new item
     * @param itemPrice - price of the new item
     */
    public Item(String name, double itemPrice) {
        this.itemName = name;
        this.itemPrice = itemPrice;
    }
    
    /**
     * Prints out the item name and price.
     * @return string of item name and price
     */
    public String toString() {
        return ((this.itemName)) + " " +
            "$" + (dformat.format(this.itemPrice));
    }

    /**
     * A getter method for item name.
     * @return the name of the item
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * A getter method for the item price.
     *
     * @return the price of the item
     */
    public double getItemPrice() {
        return this.itemPrice;
    }

    /**
     * Any test code you want to add.
     * @param args array of strings
     */
    public static void main(String[] args) {

    }

}