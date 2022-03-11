// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
// --------------------------------------------------------

/**
 * Interface for queue and the methods below we implement in the checkout class.
 * @author hudakhalid
 *
 * @param <T> type item that we will initialize in classes: checkout and shopper
 */
interface Queue<T> {
	
	/**
	 * adds/appends item at the end of the list and makes sure the items are less than 15.
	 * @param value is the value to be added to the checkout belt
	 * @return true if the item is added to the list
	 */
	public boolean enqueue(T value);
    
	/**
	 * removes items at the head (position 0) because of how a queue works.
	 * @return the item that is dequeued from the list
	 */
	public T dequeue();
    
	/**
	 * Returns the size of the list.
	 * @return return the amount of elements in the list
	 */
	public int size();
    
	/**
	 * calls the isEmpty method from storageList to see if the list is empty.
	 * @return true if head is null or if the list is empty
	 */
	public boolean isEmpty();
    
	/**
	 * calls the clear method in storageList to clear out the list.
	 */
	public void clear();
}