import java.util.Collection;
import java.lang.RuntimeException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
/**
 * This class is a singly linked list.
 * It implements Java's List interface and will implement the methods from the interface.
 * Also, in addition to those methods, I have created two more methods.
 * 
 * @author Huda Khalid
 *
 * @param <T> is using generics and where the type Item will go when initializing
 */
public class StorageList<T> implements List<T> {
	//for more information on these methods
	//read the documentation of the list interface
	//here: https://docs.oracle.com/javase/8/docs/api/java/util/List.html
	//keep in mind that we are doing a _linked_ list but this documentation
	//is for general lists (linked lists, array lists, plus others)
	//NOTE: the documentation above is not optional, it tells you things
	//like what exceptions to throw
	
	/**
	 * Inner static class called node.
	 * This class represents the nodes that we will connect/link in the linked list.
	 * @author hudakhalid
	 * @param <T> The node will be of type Item that we implement like the items from inventoryList
	 */
	private static class Node<T> {
		//you may NOT change these instance variables and/or
		//add any additional instance variables here
		//(so you may not doubly link your list)
		
		/**
		 * data of the node.
		 */
		T data;
        
        /**
         * the pointer/reference to the next node.
         */
        Node<T> next;

        //you may add more methods here... and they may be public
        //note: a constructor _is_ a method (just a special type of method)
        //note: you don't have to add anything, this will work as is
    }

    /**
     * this is the Node that is at the very beginning of the list.
     */
    private Node<T> head = null;
    /**
     * the node at the very end of the list that we have to keep track of.
     */
    private Node<T> tail = null;
    /**
     * the size of the list which will increment and decrement depending on add/remove.
     */
    private int size;
    //You _MUST_ use head and tail defined above
    //These variables will be used for grading tests
    //If you rename or change them, your code will 
    //not pass the unit tests
    
    //you may add more private methods and instance variables here if you want
    //you may add additional private helper functions as well
    //no new protected or public variables or methods

    /**
     * StorageList constructor where we initialize head and tail to null, size=0 because there is nothing in the list.
     */
    public StorageList() {
    	//initialize anything you want here...
    	head = null;
    	tail = null;
    	size = 0;
    }
    
    /**
    * Returns the number of elements in the list.
    * @return the number of elements in this list
    */
    public int size() {
    	//O(1)
    	return size;
    }

    /**
     * Returns true if this list contains no elements.
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
    	//O(1)
    	if(head == null) {
        	return true;
        }
        return false;   
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list.
     * Or -1 if this list does not contain the element.
     * @param o element to search for
     * @return the position of the object where it is found
     */
    public int indexOf(Object o) {
        int index = 0;
        Node<T> current = head;
        for(int i = 0; i < size;i++) {
        	if((current.data).equals(o)) {
        		return index;
        	}
        	else {
        		index++;
            	current = current.next;
        	}
        }
        return -1; //if not found
        //O(n)
        //yes, nulls are allowed to be searched for
    }

    /**
     * Returns true if this list contains the specified element.
     * More formally, returns true if and only if this list contains at least one element e.
     * @param o element who presence in this is to be tested
     * @return true if the list contains the object
     */
    public boolean contains(Object o) {
        Node<T> current = head;
        while(current != tail) {
        	if((current.data).equals(o)) {
        		return true;
        	}
        	else {
        		current = current.next;
        	}
        }
        return false;
        //O(n)
        //yes, nulls are allowed to be searched for
    }

    /**
     * Appends the specified element to the end of the list. 
     * @param e element to be appended to this list
     * @return true if the element is added
     */
    public boolean add(T e) {
        //throw new UnsupportedOperationException("Not supported yet. Replace this line with your implementation.");
        Node<T> newNode = new Node<T>();
        newNode.data = e;
        newNode.next = null;
        //1st case if head is null, therefore tail is null
        if(isEmpty()) {
        	//if head is null then put the head as newNode
        	head = newNode;
        	tail = head;
        	head.next = null;
        	tail.next = null;
        	size++;
        	return true;
        }
        //2nd case if there is only a head and no tail (tail is null)
        else if(head != null && tail == null) {
        	head.next = newNode;
        	tail = newNode;
        	tail.next = null;
        	size++;
        	return true;
        }
        //3rd case if we already have a list just in case loop through the list to the end of the list
        Node<T> current = head;
        while(current != tail) {
        	current = current.next;
        }
        if(current == tail) {
    		tail.next = newNode;
    		tail = newNode;
            size++;
            return true;
    	}
        return false;
        
        //nulls are allowed to be added
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * @param index is the position to the element needs to be added
     * @param element is the item needed to be added
     */
    public void add(int index, T element) {
        //O(n)
        if(index < 0 || index > size) {
        	throw new IndexOutOfBoundsException("Index out of bounds\nTry Again!\n");
        }
        Node<T> newNode = new Node<T>();
        newNode.data = element;
        Node<T> current = head;
        if(isEmpty() || index == size) {
        	//if head is null then put the head as newNode
        	add(newNode.data);
        }
        //if the index being passed in as at position zero make it a new head
        else if(index == 0) {
        	newNode.next = head;
        	head = newNode;
        	size++;
        }
        //looping to the position right before the index
        for(int i = 0; i <= index-1; i++) {
        	//if i is the position before the index then add 
        	if(i == index-1) {
        		newNode.next = current.next;
        		current.next = newNode;
        		size++;
        	}
        	current = current.next;
        }
        //nulls are allowed to be added
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * @param index - the index of the element to be removed
     * @return returns the element that was removed from the list
     */
    public T remove(int index) {
    	Node<T> current = head;
    	Node<T> removeNode = null;
    	if(index < 0 || index > size-1) throw new IndexOutOfBoundsException("Index is out of bounds\nTry Again!\n");
    	//if index is zero then change the head and remove it
    	if(isEmpty()) throw new UnsupportedOperationException("List is empty\nTry Again\n");
    	if(index == 0) {
        	removeNode = head;
        	head = head.next;
        	removeNode.next = null;
        	size--;
        	return removeNode.data;
        }
    	//if index is equal to the size then remove tail
    	else if(index == size) {
    		for(int i = 0; i <= size-1; i++) {
    			if(i == (size-1)) {
    				removeNode = tail;
    				current.next = null;
    				tail = current;
    				size--;
    				return removeNode.data;
    			}
    			current = current.next;
    		}
    	}
    	for(int i = 0; i <= index-1; i++) {
    		if(i == (index-1)) {
    			removeNode = current.next;
    			current.next = removeNode.next;
    			removeNode.next = null;
    			size--;
    			return removeNode.data;
    		}
    		current = current.next;
    	}
    	return null;
    	//O(n)
    }

    /**
     * Removes the occurrence of the specified element from the list, if it is present.
     * If this list does not contain the element, it is unchanged.
     * @param o - element to be removed from this list, if present
     * @return returns true if this list contained the specified element
     */
    public boolean remove(Object o) {
        //O(n)
        Node<T> current = head;
        Node<T> removeNode = null;
        if(isEmpty()) throw new UnsupportedOperationException("List is empty\nTry Again\n");
        if(contains(o) == true) {
        	int index = indexOf(o);
        	if(index == 0) {
            	removeNode = head;
            	head = head.next;
            	removeNode.next = null;
            	size--;
            	return true;
            }
        	//if index is equal to the size then remove tail
        	else if(index == size) {
        		for(int i = 0; i <= size-1; i++) {
        			if(i == (size-1)) {
        				removeNode = tail;
        				current.next = null;
        				tail = current;
        				size--;
        				return true;
        			}
        			current = current.next;
        		}
        	}
        	for(int i = 0; i <= index-1; i++) {
        		if(i == (index-1)) {
        			removeNode = current.next;
        			current.next = removeNode.next;
        			removeNode.next = null;
        			size--;
        			return true;
        		}
        		current = current.next;
        	}
        }
        return false;
        //yes, nulls are allowed to removed
    }

    /**
     * Removes all the elements from this list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        //O(1) <-- not a typo... think about it!
        head = null;
    }

    /**
     * iterates through the linkedList and prints the data of each node.
     */
    public void printList(){
    	Node<T> current = head;
    	while(current != null) {
    		System.out.printf("%s\n",current.data);
    		current = current.next;
    	}
    }
    
    /**
     * Returns the element at the specified position in this list.
     * @param index of the element to return
     * @return the element at the specified position in this list
     */
    public T get(int index) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException("Index is less than 0\n");
    	Node<T> current = head;
    	for(int i = 0; i <= index; i++) {
    		if(i == index) {
    			return current.data;
    		}
    		current = current.next;	
    	}
    	return null;
    	//O(n)
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index of the element to replace
     * @param element to be stored at the specified position
     * @return the element previously at the specified position
     */
    public T set(int index, T element) {
    	if(index < 0 || index > size) throw new IndexOutOfBoundsException("Index is out of bounds\n");
    	Node<T> current = head;
    	Node<T> oldElement = null;
    	for(int i = 0;i <= index; i++) {
    		if(i == index) {
    			oldElement = current;
    			current.data = element;	
    			
    		}
    		current = current.next;
    	}
    	
    	return oldElement.data;
    	//O(n)
    	
    }

    /**
     * Removes a subSetList fromIndex to toIndex (inclusive).
     * @param fromIndex position from where to start the copy
     * @param toIndex position to where the copy will end
     * @return the new list with the items fromIndex to toIndex
     */
    public StorageList<T> subSetList(int fromIndex, int toIndex) {
        StorageList<T> myList = new StorageList<T>();
        //throws IndexOutOfBoundsException if fromIndex _or_ toIndex is invalid
        if(fromIndex < 0 || fromIndex > size)throw new IndexOutOfBoundsException("fromIndex is out of bounds!\n");
        if(toIndex < 0 || toIndex > size)throw new IndexOutOfBoundsException("fromIndex is out of bounds!\n");
        for(int i = fromIndex; i <= toIndex; i++) {
        	myList.add(remove(fromIndex));
        }
        return myList;
        //removes a "subSetList" from fromIndex to toIndex (inclusive)
        //return the subSetList as a new StorageList
        //O(n)
    }

    /**
     * Returns a copy of the list with the elements in reverse order.
     * @return new list without altering the original list backwards
     */
    public StorageList<T> backwardsCopy() {
        StorageList<T> backward = new  StorageList<T>();
        for(int i = 0; i < size; i++) { 
        	//adds at position 0 so it keeps getting pushed forward and the tail will be the new head 
        	backward.add(0,get(i));
        }
        //returns a copy of the list with the elements in reverse order 
        //does not alter the original list
        return backward;
        //O(n)
    }


    /**
     * Iterator class which is implemented into the linkedList to iterate through the list.
     * @return class which returns an iterator on the list
     */
    public Iterator<T> iterator() {
        //this method is outlined for you... just fill out next() and hasNext()
        //NO ADDITIONAL ANYTHING (METHODS, VARIABLES, ETC.) INSIDE THE ANONYMOUS CLASS
        //You may NOT override remove() or any other iterator methods
        return new Iterator<T> () {
            //starts at the head
            private Node<T> current = head;

            
            /**
             * method that returns true or false based on if the next node is null or not.
             * @return true or false if the next node is null or not
             */
            public boolean hasNext() {
            	return current != null;
            }

           
            /**
             * This moves the iterator to the next position if current.next!= null.
             * @return the next element
             */
            public T next() {
            	try {
            		T data = current.data;
                	current = current.next;
                	return data;
            	}catch(NoSuchElementException n) {
            		System.out.println("No such element\n");
            	}
            	return null;
            }
        };
    }

    // --------------------------------------------------------
    // testing code goes here... edit this as much as you want!
    // --------------------------------------------------------
    /**
     * Calls the super method of the toString class.
     * @return the string 
     */
    public String toString() {
        //you may edit this to make string representations of your
        //list for testing
        return super.toString();
    }

    /**
     * Main  method where all your testing code goes.
     * @param args array of strings
     */
    public static void main(String[] args) {
    	/*StorageList<Integer> list = new StorageList<Integer>();
    	StorageList<Integer> myList = new StorageList<Integer>();
        System.out.printf("is it empty? %s\n", list.isEmpty());
        list.add(1);
        list.get(0);
        System.out.printf("is it empty? %s\n", list.isEmpty());
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.printList();
        System.out.printf("length : %d\n", list.size());
        myList = list.backwardsCopy();
        System.out.printf("length : %d\n", myList.size());
        myList.printList();*/
    }

    // --------------------------------------------------------
    // DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
    // --------------------------------------------------------


    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * 
     * @return an array containing all of the elements in this list in proper sequence
     */
    public Object[] toArray() {
        Object[] items = new Object[this.size()];
        int i = 0;
        for (T val: this) {
            items[i++] = val;
        }
        return items;
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * @param a - the array into which the elements of this list are to be stored, if it is big enough
     * @param <T> parameter of type object or type Item
     * @return an array containing the elements of this list
     */
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Returns true if this list contains all of the elements of the specified collection.
     * @param c - collection to be checked for containment in this list
     * @return true if this list contains all of the elements of the specified collection
     */
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Appends all of the elements in the specified collection to the end of this list.
     * @param c - collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     */
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Inserts all of the elements in the specified collection into this list at the specified position.
     * @param c - collection containing elements to be added to this list
     * @param index at which to insert the first element from the specified collection
     * @return true if this list changed as a result of the call
     */
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Removes from this list all of its elements that are contained in the specified collection.
     * @param c - collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     * 
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Retains only the elements in this list that are contained in the specified collection.
     * @param c - collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.
     * @param o - element to search for
     * @return the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element
     */
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     * @return  list iterator over the elements in this list (in proper sequence)
     */
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
     * @param index of the first element to be returned from the list iterator (by a call to next)
     * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list
     */
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    /**
     * Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.
     * @param fromIndex - low endpoint (inclusive) of the subList
     * @param toIndex - high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     */
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported.");
    }
}