

// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
// --------------------------------------------------------

/**
 * Interface which will be implemented by the class cart with the methods below.
 * @author hudakhalid
 *
 * @param <T> initialize of type item in the classes: cart and shopper
 */
interface Stack<T> {
	
	/**
	 * pushes/adds items to the stack by calling the add method in StorageList.
	 * @param value the value to be added to the cart
	 * @return true if the item got added successfully
	 */
	public boolean push(T value);
	
	/**
	 * Pops/deletes the item off the cart by calling remove from storageList.
	 * @return the item that was popped or deleted
	 */
	public T pop();
	
	/**
	 * calls the size method from storageList to see the amount of elements in the cart.
	 * @return the number of elements in the cart
	 */
	public int size();
	
	/**
	 * calls the isEmpty method in storageList to see if it is empty or not.
	 * @return true if the cart is empty or null
	 */
	public boolean isEmpty();
	
	/**
	 * calls the clear method from the storageList class to clear and empty the cart.
	 */
	public void clear();
}