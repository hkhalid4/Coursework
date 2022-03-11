/**
 * BinaryNode class that sets all the values and attributes of a node.
 * This class creates a node and then later adds that node to the BST.
 * @author hudakhalid
 *
 * @param <T> is the type generic 
 */
public class BinaryNode<T extends Comparable<T>> {
	
	/**
	 * the nodes parent.
	 */
	private BinaryNode<T> parent;
    /**
     * the nodes left child.
     */
    private BinaryNode<T> left;
    /**
     * the nodes right child.
     */
    private BinaryNode<T> right;
    /**
     * the data in the node.
     */
    private T value;
    
    /**
     * Constructor for setting all members to null while passing in the data.
     * @param key the data to be stored in the null
     */
    public BinaryNode(T key){
        parent = null;
        left = null;
        right = null;
        value = key;
    }
    
    /**
     * constructor to set all members to null.
     */
    public BinaryNode(){
        parent = null;
        left = null;
        right = null;
        value = null;
    }
    
    /**
     * method to set the node's parent.
     * @param node the new parent being passed in
     */
    public void setParent(BinaryNode<T> node){
    	parent = node;
    }
    
    /**
     * method to set the node's left child.
     * @param node new left child node
     */
    public void setLeft(BinaryNode<T> node){
        left = node;
    }
    
    /**
     * method to set the node's right child.
     * @param node the new right child
     */
    public void setRight(BinaryNode<T> node){
        right = node;
    }
    
    /**
     * method to set the data of the node.
     * @param key the data for the node being passed in
     */
    public void setValue(T key){
        value = key;
    }
    
    /**
     * getter for node parent.
     * @return the parent of the node
     */
    public BinaryNode<T> getParent(){
        return parent;
    }
    
    /**
     * getter for node left child.
     * @return left child of the node
     */
    public BinaryNode<T> getLeft(){
        return left;
    }
    
    /**
     * getter for nodes right child.
     * @return T right child of the node
     */
    public BinaryNode<T> getRight(){
        return right;
    }
    
    /**
     * getter for data of node.
     * @return T returns data of node
     */
    public T getValue(){
        return value;
    }
    
    /**
     * Main method for this class.
     *
     * @param args string array
     */
    public static void main(String[] args) {
    	
    }  
}