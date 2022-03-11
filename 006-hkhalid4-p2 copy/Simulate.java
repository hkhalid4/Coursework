import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Class which runs the simulation and brings all the code together.
 * @author hudakhalid
 *
 */
public class Simulate {
	
	/**
	 * Main method which runs the whole simulation, prints and collects data.
	 * @param args array of strings.
	 * @throws e runtimeException if runtime is at large
	 */
	public static void main(String[] args) {
		//create an object of the shopper class to implement the methods
		Shopper shopper = new Shopper();
        System.out.println("\nA shopper is shopping at Santiago's Store");
        Scanner in = new Scanner(System.in);

        //loop to keep the simulation running and ask for user input
        while (true) {
        	System.out.println("\n" + shopper);
        	char choice = doMenu( in );
        	try {
        		switch (choice) {
	        		case 's':
	        			//select items to put in the cart
	        			shopper.pushSelectedItemFromInventoryToCart();
	            		break;
            		case 'c':
            			//move an item from the cart onto the checkout belt
            			shopper.moveItemFromCartToCheckOutBelt();
                        break;
                    case 'r':
                    	//print a receipt
                    	shopper.printReceipt();
                        break;
                    case 't':
                    	//take an item off the checkout belt
                    	shopper.takeItemOffCheckOutBelt();
                        break;
                    case 'x':
                    	//exit
                    	in .close();
                        System.exit(0);
                    default:
                        System.out.println("\n            " +
                            "Invalid Section, choose from the list below\n");
                }
            } catch (RuntimeException e) {
                System.out.println("\n" + e.getMessage());
            }
        }
    }

    /**
     * Prints the menu and collects the users input for what selection they made from the list.
     * @param in passes in the user input 
     * @return char of the users input
     * @throws InputMisMatchException if the user enters an invalid selection
     */
    public static char doMenu(Scanner in ) {
        while (true) {
            try {
            	//prints the menu
            	System.out.println("\nThe shopper can:");
                System.out.println("        s) Select items to put in the cart");
                System.out.println("        c) Move an item from the cart onto the checkout belt");
                System.out.println("        r) Print a receipt");
                System.out.println("        t) Take an item off of the checkout belt");
                System.out.println("        x) Exit");
                System.out.print("\n Enter a character: ");
                //collects the char from the user input
                char choice = in .next().charAt(0); in .nextLine();

                //check to see if it is a valid char
                if (choice < 'a' || choice > 'z') {
                    System.out.println("\nCharacter out of range, choose from the list below: \n");
                    continue;
                }

                return choice;
            } catch (InputMismatchException e) {
                System.out.println("\n Error: Invalid selection"); in .nextLine();
            }
        }
    }
}