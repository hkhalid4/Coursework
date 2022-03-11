import java.util.NoSuchElementException;
/**
 * This class implements the interface Stack.
 * It contains a storageList to use as its internal storage.
 * @author hudakhalid
 *
 */
public class Cart implements Stack<Item> {
	//this is your internal storage

	/**
	 * The implemented linkedStack of type StorageList.
	 */
	public StorageList<Item> cartList;

    //you may _NOT_ add any additional instance variables, use the list above!
    //you should not need any additional private helper methods, but you
    //may add them if you like

    /**
     * constructor for the cart class where we initialize the cart stack.
     */
    public Cart() {
    	//any initialization goes here
    	cartList = new StorageList<Item>();
    }

    //override all the required methods
    //all methods must be O(1)
    //all methods can be written in 3 lines or less of code

    // --------------------------------------------------------
    // testing code goes here... edit this as much as you want!
    // --------------------------------------------------------
    /**
     * Main method where all your testing code goes.
     * @param args array of strings
     */
    public static void main(String[] args) {
    	Cart cart1 = new Cart();
    	cart1.push(new Item("apple",3.50));
    	cart1.push(new Item("grape",2.50));
    	cart1.push(new Item("wine",50));
    	cart1.printList();
    	cart1.pop();
    	System.out.printf("The size of cart1 is %d\n", cart1.size());
    	cart1.printList();
    }
    

    // --------------------------------------------------------
    // DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
    // --------------------------------------------------------
    /**
     * toString method that prints out the cart items and whether if it is empty.
     * @return String of items
     */
    public String toString() {
        String string = "";
        boolean first = true;
        for (Item i: cartList) {
            if (first) {
                string = "Cart: " + i.getItemName();
                first = false;
            } else {
                string = string + "\n      " + i.getItemName();
            }
        }
        string = string + "\n\n";
        if (cartList.isEmpty()) {
            return "Cart:" + "Empty\n";
        } else {
            return string;
        }
    }

    /**
     * prints the linkedStack/cart items.
     */
    public void printList() {
    	//Item value;
    	//value = cartList.get(0);
    	for(int i = 0; i < cartList.size(); i++) {
    		//value = cartList.get(i);
    		System.out.printf("%s\n",cartList.get(i));
    	}
    		
    }
	
	/**
	 * pushes/adds items to the stack by calling the add method in StorageList.
	 * @param value the value to be added to the cart
	 * @return true if the item got added successfully
	 */
	public boolean push(Item value) {
		// TODO Auto-generated method stub
		if(cartList.add(value) == true) {
			return true;
		}
		return false;
	}

	
	/**
	 * Pops/deletes the item off the cart by calling remove from storageList.
	 * @return the item that was popped or deleted
	 */
	public Item pop() {
		if(cartList.isEmpty()) throw new NoSuchElementException("cartList is empty\n");
		return cartList.remove(cartList.size()-1);
	}

	
	/**
	 * calls the size method from storageList to see the amount of elements in the cart.
	 * @return the number of elements in the cart
	 */
	public int size() {
		return cartList.size();
	}

	
	/**
	 * calls the isEmpty method in storageList to see if it is empty or not.
	 * @return true if the cart is empty or null
	 */
	public boolean isEmpty() {
		return cartList.isEmpty();

	}

	/**
	 * calls the clear method from the storageList class to clear and empty the cart.
	 */
	public void clear() {
		cartList.clear();
		
	}
}