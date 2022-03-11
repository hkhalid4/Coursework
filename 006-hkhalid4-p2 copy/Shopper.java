import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class simulates the actions of a shopper.
 * The shopper moves items between the inventory shelf, the cart(linked stack), and the checkout belt(linked queue).
 * This class will use methods in the Cart and Checkout classes.
 * @author Huda Khalid
 *
 */
public class Shopper {

	/**
	 * inventoryList is a StorageList created for you below.
	 * use the method(s) below to select an item from inventoryList.
	 * push the item onto the cart.
	 * if there is no item,  throw a NoSuchElementException.
	 * with the message "Error: Item not found to move from inventory to cart".
	 */
	public void pushSelectedItemFromInventoryToCart() {
    	//push an item onto the cart stack which does not affect the inventory list 
    	//if inventory list is ever to be empty catch the exception and throw an error message
    	try {
    		Item item1 = selectItemFromInventory();
        	cart.push(item1);
    	}catch(NoSuchElementException n) {
    		System.out.println("Error: Item not found to move from inventory to cart\n");
    	}
    }

	/**
     * pop an item out off the cart and enqueue it onto the checkout belt.
     * if there are no Items in the cart, throw a NoSuchElementException.
     * with the message "Error: item not found to move from cart to checkout".
     * 
     */
	public void moveItemFromCartToCheckOutBelt() {
		//pop an item from the top/end of the list and enqueue it onto the checkout belt
		//if the cart/list is empty then catch the exception and throw an error message
		try {
			Item item1 = cart.pop();
        	checkout.enqueue(item1);
    	}catch(NoSuchElementException n) {
    		System.out.println("Error: Item not found to move from cart to checkout\n");
    	}
    }

    /**
     * dequeue an item from the checkout belt to take home.
     * if there are no items on the checkout belt, throw a NoSuchElementException.
     * with the message "Error: item not found to take off checkout belt".
     */
    public void takeItemOffCheckOutBelt() {
    	//dequeue at the front of the list, if there is nothing in the list catch the exception
    	try {
    		checkout.dequeue();
    	}catch(NoSuchElementException n) {
    		System.out.println("Error: Item not found to take off checkout belt\n");
    	}
    	
    }


    /**
     * Main method which ties all the parts together and runs the simulation.
     * It calls all the methods above to run the simulation of a grocery store and print out the data.
     * @param args array of strings
     */
    public static void main(String[] args) {
    	/*System.out.println("Welcome to Huda's Grocery Store\n");
    	Shopper shopper1 = new Shopper();
    	boolean repeatLoop = true;
    	do {
    		System.out.printf("%s\n",shopper1.toString());
    		shopper1.printMenu();
    		Scanner sd = new Scanner(System.in);
    		System.out.print("Enter your character: \n");
    		char choice = sd.next().charAt(0);
    		switch(choice) {
             	//select items to put in the cart
             	case 's':
             		shopper1.pushSelectedItemFromInventoryToCart();
             		break;
             	//move an item from the cart onto the checkout belt
             	case 'c':
             		shopper1.moveItemFromCartToCheckOutBelt();
             		break;
             	//print a receipt
             	case 'r':
             		shopper1.printReceipt();
             		break;
             	//take an item off the checkout belt
             	case 't':
             		shopper1.takeItemOffCheckOutBelt();
             		break;
             	//exit
             	case 'x':
             		repeatLoop = false;
             		break;
             		
    		}
    	}while(repeatLoop == true);*/
    	Simulate runProgram = new Simulate();
    	runProgram.main(args);
    	
    }

    // --------------------------------------------------------
    // DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
    // --------------------------------------------------------
    /**
     * creates an object of type scanner to get user input.
     */
    Scanner in = new Scanner(System.in);
    
    /**
     * inventoryList is the linkedList where we add all the items into.
     */
    private final StorageList<Item> inventoryList = new StorageList<>();
    
    /**
     * cart is the linkedStack where we push and pop items depending on users choice.
     */
    private final Cart cart = new Cart();
    
    /**
     * checkout is the LinkedQueue where we enqueue and dequeue items from the checkout belt.
     */
    private final CheckOut checkout = new CheckOut();

    /**
     * constructor of the shopper class which call the method to create the inventory.
     */
    public Shopper() {
        makeInventory();
    }

    /**
     * returns the position of the Item name being passed in and searched in the inventory List.
     * @param name is the name being searched in the list.
     * @return the position of where the item is in the list.
     */
    public int indexOf(String name) {
        int index = 0;
        for (Item i: this.inventoryList) {
            if (i.getItemName().equals(name)) {
                return index;
            }
            index = index + 1;
        }
        return -1;
    }

    /**
     * method that creates the linkedList of inventory and is called in the constructor.
     */
    public void makeInventory() {
        inventoryList.add(new Item("apple", 0.50));
        inventoryList.add(new Item("bread", 3.00));
        inventoryList.add(new Item("carrots", 1.25));
        inventoryList.add(new Item("donuts", 0.25));
        inventoryList.add(new Item("eggs", 4.00));
        inventoryList.add(new Item("fish", 5.00));
        inventoryList.add(new Item("grapes", 1.00));
        inventoryList.add(new Item("hummus", 2.00));
    }

    /**
     * method in which the inventory list is printed.
     */
    public void printInventory() {
        String string = " ";
        string += "\nInventory of items on the shelf: \n";
        for (Item item: inventoryList) {
            string += " \n                 " + item;
        }
        string += "\n\n";
        System.out.print(string);
        return;
    }

    /**
     * toString method which calls the toString methods of the cart and checkout class.
     * it displays the items in cart and checkout and whether if it is empty or not.
     * @return the strings of the toString methods of checkout and cart class
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("-------------\n");
        sb.append(this.cart.toString());
        sb.append("-------------\n");
        sb.append(this.checkout.toString());
        sb.append("-------------\n");

        return sb.toString();
    }

    /**
     * method which collects data from the user based on what item the user chose.
     * @return the item from the inventory list in which the user chose.
     */
    public Item selectItemFromInventory() {
        while (true) {
            try {
                printInventory();
                System.out.print("\n Select an item by first letter: ");
                char c = in .next().charAt(0);
                if (c < 'a' || 'h' < c) {
                    System.out.println("\n Error: selection must be from 'a' to 'h' \n");
                } else {
                    Item i = getItemByFirstChar(c);
                    return i;
                }
            } catch (NoSuchElementException e) {
                throw new RuntimeException(" Error: in selectItemFromInventory()" +
                    "\n No such element\n");
            }
        }
    }

    /**
     * This method is called in the selectItemFromInventory() to return the actual item the user chose.
     * 
     * @param c is the first character of the item being passed in which the user chose.
     * @return the actual item that the user chose to be pushed onto the stack cart.
     */
    public Item getItemByFirstChar(char c) {
        try {
            Item tmpItem = new Item();
            for (Item i: this.inventoryList) {
                if (i.getItemName().charAt(0) == c) {
                    tmpItem = i;
                }
            }
            return tmpItem;
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Error: Invalid selection, choose again");
        }
    }

    /**
     * calls the makeReceipt() method in the checkout class to print the receipt.
     */
    public void printReceipt() {
        System.out.print(checkout.makeReceipt());
    }
}