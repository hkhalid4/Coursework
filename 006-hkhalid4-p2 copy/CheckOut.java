import java.util.NoSuchElementException;
/**
 * This class implements the interface queue.
 * It contains a StorageList to use as its internal storage.
 * @author Huda Khalid
 *
 */
public class CheckOut implements Queue<Item> {
	/**
	 * Maximum allowed items on the checkout belt.
	 */
	public static final int MAX_CAPACITY = 15;

    //this is your internal storage
    /**
     * itemList is the linkedList which is then implemented into a queue with the methods below.
     */
    public StorageList<Item> itemList;

    //you may _NOT_ add any additional instance variables, use the list above
    //you should not need any additional private helper methods, but you
    //may add them if you like

    /**
     * Constructor for the checkOut class.
     * Initialize itemList of storageList type Item.
     */
    public CheckOut() {
    	//any initialization goes here
    	itemList = new StorageList<Item>();
    }

    //override all the required methods
    //all methods must be O(1)
    //all required methods can be written in 3 lines or less of code


    // --------------------------------------------------------
    // testing code goes here... edit this as much as you want
    // --------------------------------------------------------

    /**
     * Main method where all your testing code goes.
     * @param args array of strings
     */
    public static void main(String[] args) {
    	CheckOut cart2 = new CheckOut();
    	Cart cart1 = new Cart();
    	cart2.enqueue(new Item("apple",3.50));
    	cart2.enqueue(new Item("grape",2.50));
    	cart2.enqueue(new Item("wine",50));
    	System.out.printf("The size of cart2 is %d\n", cart2.size());
    	cart2.printList();
    	cart2.dequeue();
    	System.out.printf("The size of cart2 is %d\n", cart2.size());
    	cart2.printList();
    }

    // --------------------------------------------------------
    // DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
    // --------------------------------------------------------
    
    /**
     * toString method that prints out the checkout belt items and whether if it is empty.
     * @return the string item
     */
    public String toString() {
        String string = "";
        for (Item i: itemList) {
            string = i.getItemName() + " " + string;
        }
        if (string == "") {
            return "Checkout Belt: " + "Empty\n";
        } else {
            return "Check-out Belt: " + string + "\n";
        }
    }

    /**
     * Prints and adds the item total.
     * @return the receipt total
     */
    public String makeReceipt() {
        Double total = 0.0;
        String string = "\n";
        for (Item i: itemList) {
            string = " " + i.toString() + " \n" + string;
            total += i.getItemPrice();
        }
        return "\n Receipt: \n" + string +
            " Receipt Total:  " + "$" + total + "\n\n";
    }
    
    /**
     * Prints the list in the checkout belt.
     */
    public void printList() {
    	//Item value;
    	//value = cartList.get(0);
    	for(int i = 0; i < itemList.size(); i++) {
    		//value = cartList.get(i);
    		System.out.printf("%s\n",itemList.get(i));
    	}	
    }
	
	/**
	 * adds/appends item at the end of the list and makes sure the items are less than 15.
	 * @param value is the value to be added to the checkout belt
	 * @return true if the item is added to the list
	 */
	public boolean enqueue(Item value) {
		//adds/appends item at the end of the list and makes sure the items are less than 15
		if((itemList.add(value)) == true && (itemList.size() <= MAX_CAPACITY)) {
			return true;
		}
		return false;
	}
	
	/**
	 * removes items at the head (position 0) because of how a queue works.
	 * @return the item that is dequeued from the list
	 */
	public Item dequeue() {
		//removes items at the head (position 0) because of how a queue works 
		if(itemList.isEmpty()) throw new NoSuchElementException("itemList is empty\n");
		return itemList.remove(0);
		
	}

	/**
	 * Returns the size of the list.
	 * @return return the amount of elements in the list
	 */
	public int size() {
		return itemList.size();
	}
	
	/**
	 * calls the isEmpty method from storageList to see if the list is empty.
	 * @return true if head is null or if the list is empty
	 */
	public boolean isEmpty() {
		return itemList.isEmpty();
	}

	/**
	 * calls the clear method in storageList to clear out the list.
	 */
	public void clear() {
		itemList.clear();
		
	}

}