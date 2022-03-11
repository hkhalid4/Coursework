/**
 * MyArray is a dynamic array class which we will implement in the menu class.
 * We will be creating different types of dynamic arrays of type surveyRecord, Integer, Double, etc.
 * MyArray will contain a simple Java array called data. 
 * Methods below such that MyArray has the functionality of a dynamic array.
 * 
 * @author Huda Khalid
 *
 * @param <T> is used to modify the class using generics.
 *
 */
public class MyArray<T> {
	/**
	 * default capacity of all the arrays will be 2 unless assigned to in a constructor.
	 */
	private static final int DEFAULT_CAPACITY = 2; //default initial capacity / minimum capacity
    /**
     * this is the simple array called data which we will implement below.
     */
    private T[] data; //underlying array, you MUST use this for full credit
    /**
     * integer size is the amount of elements in an array not the capacity.
     */
    private int size;

    
    // ADD MORE PRIVATE MEMBERS HERE IF NEEDED!
    /**
     * this method is a constructor which can be called while creating a dynamic array in the menu class.
     */
    @SuppressWarnings("unchecked")
    public MyArray() {
        //constructor
        //initial capacity of the array should be DEFAULT_CAPACITY
        data = (T[]) new Object[DEFAULT_CAPACITY];

        size = 0;
    }

    /**
     * this method is another constructor which will be used when creating a dynamic array while passing in another array.
     * 
     * @param inputArray is the array being passed in where we set the data and size.
     * 
     */
    @SuppressWarnings("unchecked")
    public MyArray(T[] inputArray) {
    	// constructor
    	// set the data as the data of inputArray
    	data = inputArray;
    	size = 0;
    }

    /**
     * another constructor of the myArray class which will create a dynamic array while passing in the capacity.
     * set the initial capacity of the smart array as initialCapacity.
     * 
     * @param initialCapacity is passed in to set the array to its capacity.
     * 
     */
    @SuppressWarnings("unchecked")
    public MyArray(int initialCapacity) {
    	//throw IllegalArguementException if initialCapacity is smaller than 1
    	if(initialCapacity < 1) throw new IllegalArgumentException("Invalid Capacity");
    	data = (T[]) new Object[initialCapacity];
    	size = 0;
    }
  
    /**
     * report number of elements in the smart array.
     * 
     * @return size
     */
    public int size() {
    	// O(1)
    	return size;
    }
    
    /**
     * report max number of elements before the next expansion.
     * 
     * @return data.length
     */
    public int capacity() {
    	// O(1)
    	return data.length;
    }

    /**
     * if the size is the same as capacity.
     * return true if size if not the same as capacity.
     * 
     * @return false
     *
     */
    public boolean sizeCheck() {
    	if(size == data.length) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * add an element to the end.
     * double capacity if no space available.
     * return true if append was successful.
     * 
     * @param value is the value that needs to be added to the end of the array
     * 
     * @return true
     */
    @SuppressWarnings("unchecked")
    public boolean append(T value) {	 
    	// amortized O(1)
    	if(size == data.length) {
    		ensureCapacity(data.length * 2);	
    	}
    	data[size++] = value;
        return true;
    }

    /**
     * Swap the elements of index1 and index2.
     * return true if swap was successful
     * 
     * @param index1 is index1 that needs to be swapped with index 2
     * 
     * @param index2 is index2 that needs to be swapped with index 2
     * 
     * @return true
     */
    @SuppressWarnings("unchecked")
    public boolean swap(int index1, int index2) {
    	// O(1)		
    	// throw IndexOutOfBoundsException for invalid indices
    	if((index1 < 0 || index1 > size) && (index2 < 0 || index2 > size)) {
    		throw new IndexOutOfBoundsException("Invalid indices");
    	}
    	//create temp to save the value of index1 in order to make a swap
    	T temp = data[index1];
    	data[index1] = data[index2];
    	data[index2] = temp;
    	
        return true;
    }

    /**
     * insert value at index, shift elements if needed.
     * double the capacity if no space is available.
     * this method can also append items as well as insert items.
     * 
     * @param index is the index
     * 
     * @param value is the value we need to add
     * 
     */
    @SuppressWarnings("unchecked")
    public void add(int index, T value) {
    	// throw IndexOutOfBoundsException for invalid index
    	// O(N) where N is the number of elements in the array
    	if(index < 0 || index > size) throw new IndexOutOfBoundsException("Invalid Index");
    	if(size == data.length) {
    		ensureCapacity(data.length * 2);
    	}
    	//append the value if index is the same as the amount of elements in the array
    	if(index == size) {
    		append(value);
    		return;
    	}
    	//shift elements to the right starting from the end
    	int i;
    	for(i = size; i > index; i--) {
    		data[i] = data[i - 1];
    	}
    	data[index] = value;
    	size++;
    
    }
    
    /**
     * return the item at index.
     * throw IndexOutOfBoundsException for invalid index.
     * 
     * @param index is the index
     * 
     * @return data[index]
     */
    public T get(int index) {
    	// return the item at index
    	// throw IndexOutOfBoundsException for invalid index
    	// O(1)
    	if(index < 0 || index >= size) {
    		throw new IndexOutOfBoundsException("Invalid Index");
    	}
    	return data[index];
        
    }
    /**
	 * change item at index to be value.
	 * return old item at index.
	 * throw IndexOutOfBoundsException for invalid index.
	 * 
	 * @param index is the index
	 * 
	 * @param value is the value needed to be replaced with
	 * 
	 * @return oldItem
	 */
    public T replace(int index, T value) {
    	// O(1)
    	//you cannot add new items in this method
    	if(index < 0 || index > size) {
    		throw new IndexOutOfBoundsException("Invalid Index!");
    	}
    	//save the old item so you can replace it with the new value
    	T oldItem = data[index];
    	data[index] = value;
    	return oldItem;
    }

    /**
     * remove and return element at position index.
     * shift elements to remove any gap in the array.
     * throw IndexOutOfBoundsException for invalid index.
     * 
     * @param index is the index passed in order to be deleted.
     * 
     * @return removeElement
     */
    @SuppressWarnings("unchecked")
    public T delete(int index) {
    	if(index < 0 || index > size) {
    		throw new IndexOutOfBoundsException("Invalid Index!");
    	}
    	T removeElement = data[index];
    	//shifting elements over to the left starting from index+1
    
    	for(int i = index; i < size - 1; i++) {
    		data[i] = data[i + 1];
    	}
    	//decreasing size because element is removed
    	size--;
    	// halve capacity if the number of elements falls below 1/4 of the capacity
    	// capacity should NOT go below DEFAULT_CAPACITY
    	// O(N) where N is the number of elements in the list
    	double cap = data.length * .25;
    	if(size < cap) {
    		//if size is less than 1/4 of capacity 
    		//halve the capacity by 2
    		int newCap = (int) (data.length * .5);
    		//check to see if newCap is less that DefaultCap
    		if(newCap < DEFAULT_CAPACITY) {
    			newCap = DEFAULT_CAPACITY;
    		}
    		
    		//resize the array to newCap
    		ensureCapacity(newCap);
    		
    	}
        return removeElement;
    }

    /**
     * return the index of the first occurrence or -1 if not found.
     * loop through the elements to match the value.
     * return the index at which the value is found.
     * 
     * @param value is the value in which the index is needed to be found.
     * 
     * @return index
     */
    public int firstIndexOf(T value) {
    	// O(n)
    	//loop through the elements to match the value
    	//return the index at which the value is found
    	int i;
    	for(i = 0; i < size; i++) {
    		if(data[i].equals(value)) {
    			return i;//if found
    		}
    	}
        return -1;//if not found
    }

    /**
     * change the max number of items allowed before next expansion to newCapacity.
     * return true if newCapacity gets applied; false otherwise.
     * if new capacity is less than 2(defaultCap) or less than size: return false.
     * 
     * @param newCapacity is the newCapacity we have to check and ensure
     * 
     * @return true
     * 
     */
    @SuppressWarnings("unchecked")
    public boolean ensureCapacity(int newCapacity) {
    	// capacity should not be changed if:
    	//   - newCapacity is below DEFAULT_CAPACITY; or 
    	//   - newCapacity is not large enough to accommodate current number of items
    	// O(N) where N is the number of elements in the array	
    	if(newCapacity < DEFAULT_CAPACITY || newCapacity < size) {
    		return false; 
    	}
    	//create new array with new capacity
    	T newArray[] = (T[]) new Object[newCapacity];
    	
    	//copy all elements from old array
    	
    	for(int i = 0; i < size && i <= newCapacity; i++) {
    		newArray[i] = data[i];
    	}
    	
    	//set old array equal to the new array
    	data = newArray;
        return true;
    }

    /**
     * make a copy of all the current values.
     * create array of MyArray class and copy all elements.
     * 
     * @return duplicate
     * 
     */
    public MyArray<T> clone() {
    	//don't forget to set the capacity!
    	//O(n)
    	//create a duplicate array and copy all the values from data
    	MyArray<T> duplicate = new MyArray<T>(data.length);
    	int i;
    	for(i = 0; i < size; i++) {
    		duplicate.data[i] = data[i];
    	}
    	//set the size for duplicate
    	duplicate.size = size;
    	return duplicate;
        
    }

    // --------------------------------------------------------
    // example testing code... edit this as much as you want!
    // --------------------------------------------------------
    
    /**
     * main method which tests all the functionalities of the dynamic array.
     * if you passed all the tests 1-7 move on to menu class.
     * 
     * @param args is the string passed in which the user enters when running the program.
     * 
     */
    public static void main(String args[]) {
        //create a MyArray of integers
        MyArray<Integer> nums = new MyArray<Integer>();
        if ((nums.size() == 0) && (nums.capacity() == 2)) {
            System.out.println("Passed Test 1");
        }

        //append some numbers 
        for (int i = 0; i < 3; i++) {
            nums.add(i, i * 2);
        }

        if (nums.size() == 3 && nums.get(2) == 4 && nums.capacity() == 4) {
            System.out.println("Passed Test 2");
        }

        //create a myArray of strings
        MyArray<String> msg = new MyArray<>();

        //insert some strings
        msg.add(0, "Structures");
        msg.add(0, "Data");
        msg.add(1, "new");
        msg.append("!");

        //replace and checking
        if (msg.get(0).equals("Data") && msg.replace(1, "beautiful").equals("new") &&
            msg.size() == 4 && msg.capacity() == 4) {
            System.out.println("Passed Test 3");
        }

        //change capacity
        if (!msg.ensureCapacity(0) && !msg.ensureCapacity(3) && msg.ensureCapacity(20) &&
            msg.capacity() == 20) {
            System.out.println("Passed Test 4");
        }

        //delete and shrinking
        if (msg.delete(1).equals("beautiful") && msg.get(1).equals("Structures") &&
            msg.size() == 3 && msg.capacity() == 10) {
            System.out.println("Passed Test 5");
        }

        //firstIndexOf and clone
        //remember what == does on objects... not the same as .equals()
        MyArray<String> msgClone = msg.clone();
        if (msgClone != msg && msgClone.get(1) == msg.get(1) &&
            msgClone.size() == msg.size() &&
            msgClone.capacity() == msg.capacity() &&
            msgClone.firstIndexOf("Structures") == 1 &&
            msgClone.firstIndexOf("beautiful") == -1) {
            System.out.println("Passed Test 6");
        }
        //swap
        msgClone.swap(0, 1);
        if (msgClone.get(0) == "Structures" && msgClone.get(1) == "Data") {
            System.out.println("Passed Test 7");
        }

    }

    /**
     * toString method.
     * 
     * @return string
     */
    public String toString() {
        if (size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(get(0));
        for (int i = 1; i < size(); i++) {
            sb.append(", ");
            sb.append(get(i));
        }
        return sb.toString();
    }
}
