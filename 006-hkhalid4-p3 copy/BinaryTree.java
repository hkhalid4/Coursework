/**
 * BinaryTree class which creates the tree with nodes.
 * BST class uses different functions like insert,remove,etc to create  tree.
 * @author hudakhalid
 *
 * @param <T> is the type of generic
 */
public class BinaryTree<T extends Comparable<T>> {
	
	/**
	 * root for the binary tree.
	 */
	private BinaryNode<T> root;
	
	/**
	 * size of the tree.
	 */
	private int size;
	
	/**
	 * height of the tree.
	 */
	private int height;

	/**
	 * constructor with no parameters that sets the root equal to a new BinaryNode with null values.
	 * and sets all other members to zero.
	 */
	public BinaryTree() {
		root = new BinaryNode<T>(null);
		size = 0;
		height = 0;
	}

	/**
	 * setter for the root of the tree.
	 * @param rootSet the new root to be set
	 */
	public void setRoot(BinaryNode<T> rootSet) {
		root = rootSet;
	}
	
	/**
	 * setter for size of the tree.
	 * @param sizeSet the new size of the tree.
	 */
	public void setSize(int sizeSet) {
		size = sizeSet;
	}
	
	/**
	 * setter for the height of the tree.
	 * @param heightSet new height of the tree
	 */
	public void setHeight(int heightSet) {
		height = heightSet;
	}
	
	/**
	 * getter for the root of the tree.
	 * @return returns the root of the tree
	 */
	public BinaryNode<T> getRoot(){
		return root;
	}
	
	/**
	 * getter for the size of the tree.
	 * @return returns the size of the tree
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * getter for the height of the tree.
	 * @return returns the height of the tree
	 */
	public int getHeight() {
		height = bstGetHeight(root);
		return height;
	}
	
	/**
	 * replaces the child from currentChild to newChild.
	 * @param parent the nodes parent being passed in
	 * @param currentChild the current child that will be replaced
	 * @param newChild the new child of the parent 
	 * @return return true if done successfully, false otherwise 
	 */
	public boolean bstReplaceChild(BinaryNode<T> parent, BinaryNode<T> currentChild, BinaryNode<T> newChild) {
		if((parent.getLeft()!=currentChild) && (parent.getRight()!=currentChild)) {
			return false;
		}
		if(parent.getLeft() == currentChild) {
			parent.setLeft(newChild);
		}
		else {
			parent.setRight(newChild);
		}
		if(newChild != null) {
			newChild.setParent(parent);
		}
		return true;
	}
	
	/**
	 * method which calls the iterative function for remove.
	 * @param key the value passed in to be searched for to be then passed in the iterative function
	 * @return returns the node that has been removed or null if not
	 */
	public BinaryNode<T> removeKeyI(T key){
		//first search for the node that has the value of the key
		BinaryNode<T> node = bstSearchI(key);
		//then return remove and return that node
		
		return removeNodeI(node);
	}
	
	/**
	 * removes the node being passed in iteratively.
	 * @param node node which needs to be removed
	 * @return returns the node that  has been removed or null if not
	 */
	public BinaryNode<T> removeNodeI(BinaryNode<T> node) {
		BinaryNode<T> par = null;
		BinaryNode<T> current = root;
		BinaryNode<T> nodeRemoved = null;
		while(current != null) {
			//search node
			if(current.getValue() == node.getValue()) {
				//node found
				if((current.getLeft()==null) && (current.getRight()==null)) {
					//remove leaf
					if(par == null) {
						//node is root
						root = null;
						nodeRemoved = root;
						return nodeRemoved;
					}
					else if(par.getLeft()==current) {
						par.setLeft(null);
						nodeRemoved = current;
						return nodeRemoved;
					}
					else {
						par.setRight(null);
						nodeRemoved = current;
						return nodeRemoved;
					}
				}
				else if((current.getLeft() != null) && (current.getRight()==null)) {
					//remove node with only left child
					if(par == null) {
						//node is root
						nodeRemoved = root;
						root = current.getLeft();
						return nodeRemoved;
						
					}
					else if(par.getLeft()==current) {
						par.setLeft(current.getLeft());
						nodeRemoved = current;
						return nodeRemoved;
					}
					else {
						par.setRight(current.getLeft());
						nodeRemoved = current;
						return nodeRemoved;
						
					}
				}
				else if((current.getLeft() == null) && (current.getRight() != null)) {
					//remove node with only right child
					if(par == null) {
						//node is root
						nodeRemoved = root;
						root = current.getRight();
						return nodeRemoved;
					}
					else if(par.getLeft() == current) {
						par.setLeft(current.getRight());
						nodeRemoved = current;
						return nodeRemoved;
					}
					else {
						par.setRight(current.getRight());
						nodeRemoved = current;
						return nodeRemoved;
					}
				}
				else {
					//remove node with two children
					//find successor(leftmost child or right subtree)
					BinaryNode<T> succ = current.getRight();
					while(succ.getLeft() != null) {
						succ = succ.getLeft();
					}
					T succData;
					//create copy of succ data
					succData = succ.getValue();
					nodeRemoved = succ;
					//remove successor
					removeNodeI(succ);
					current.setValue(succData);
					return nodeRemoved;
					
				}
				//return nodeRemoved; //node found and removed
			}
			else if((current.getValue().compareTo(node.getValue())) < 0) {
				//search right
				par = current;
				current = current.getRight();
			}
			else {
				//search left
				par = current;
				current = current.getLeft();
			}
		}
		return nodeRemoved; //node not found
	}
	
	/**
	 * method which calls the recursive function.
	 * @param key the data in which the node needs to be found
	 * @return returns the node removed from the recursive function
	 */
	public BinaryNode<T> removeKeyR(T key) {
		//first search for the node that has the value of the key
		BinaryNode<T> node = bstSearchI(key);
		//then return remove and return that node
		return removeNodeR(node);
		
	}
	
	/**
	 * removes the node recursively. 
	 * @param node node to be removed
	 * @return returns the node which has been removed
	 */
	public BinaryNode<T> removeNodeR(BinaryNode<T> node){
		if(node == null) {
			return null;
		}
		BinaryNode<T> nodeRemoved = null;
		//case 1: internal node with 2 children
		if((node.getLeft() != null) && (node.getRight() != null)) {
			//find successor
			BinaryNode<T> successor = node.getRight();
			while(successor.getLeft() != null) {
				successor = successor.getLeft();
			}
			//copy value/data from successor to node
			node.setValue(successor.getValue());
			nodeRemoved = successor;
			//recursively remove successor
			removeNodeR(successor);
			return nodeRemoved;
				
		}
		//case 2: root node(with 1 or 0 children)
		else if(node == root) {
			nodeRemoved = root;
			if(node.getLeft() != null) {
				root = node.getLeft();
			}
			else {
				root = node.getRight();
			}
			//make sure the new root, if non-null, has a null parent
			if(root != null) {
				root.setParent(null);
			}
			return nodeRemoved;
		}
		//case 3: internal with left child only
		else if(node.getLeft() != null) {
			bstReplaceChild(node.getParent(),node,node.getLeft());
			nodeRemoved = node;
			return nodeRemoved;
			
		}
		//case 4: internal with right child only or leaf
		else {
			bstReplaceChild(node.getParent(),node,node.getRight());
			nodeRemoved = node;
			return nodeRemoved;
		
		}
	}

	/**
	 * method to get the parent of the node being passed in.
	 * @param node the node being passed in to find the parent
	 * @return returns the parent of the node
	 */
	public BinaryNode<T> bstGetParent(BinaryNode<T> node){
		if(node.getParent() == null) return null;
		if((node.getParent().getLeft()==node) || (node.getParent().getRight()==node)) {
			return node.getParent();
		}
		if((node.getValue().compareTo(node.getParent().getValue())) < 0) {
			return bstGetParent(node.getParent().getLeft());
		}
		return bstGetParent(node.getParent().getRight());
		
	}
	
	/**
	 * traverse to the left iteratively until you find a leaf.
	 * @return returns the minimum node in the tree
	 */
	public BinaryNode<T> bstFindMin(){
		BinaryNode<T> current = root;
		while((current.getLeft() != null) && (current != null)) {
			current = current.getLeft();
		}
		return current;
	}
	
	/**
	 * traverse to the left recursively until you find a leaf.
	 * @param node node that needs to be called in the recursive call to change the case
	 * @return returns the minimum node in the tree
	 */
	public BinaryNode<T> bstFindMinR(BinaryNode<T> node){
		//figure out if we need parameters for recursive call or not 
		BinaryNode<T> current = node;
		if(current.getLeft() == null) {
			return current;
		}
		return bstFindMinR(current.getLeft());
	
	}
	
	/**
	 * traverse to the right recursively until you find a leaf.
	 * @param node recursively to be called on to get to the right most node
	 * @return the maximum node
	 */
	public BinaryNode<T> bstFindMaxR(BinaryNode<T> node){
		//figure out if we need parameters for recursive call or not 
		BinaryNode<T> current = node;
		if(current.getRight() == null) {
			return current;
		}
		return bstFindMaxR(current.getRight());
	
	}
	
	/**
	 * traverse to the right iteratively until you find a leaf.
	 * @return returns the maximum node
	 */
	public BinaryNode<T> bstFindMax(){
		BinaryNode<T> current = root;
		while((current.getRight() != null) && (current != null)) {
			current = current.getRight();
		}
		return current;
	
	}
	
	/**
	 * iterative insert method that inserts the new node in the proper location.
	 * @param node the node to be inserted
	 */
	public void bstInsertI(BinaryNode<T> node) {
		BinaryNode<T> current;
		//if the node being passed in is the root 
		if(root.getValue() == null) {
			root = node;
			node.setParent(null);
			setRoot(root);
			size++;
			return;
		}
		else {
			current = root;
			while(current != null) {
				if((node.getValue().compareTo(current.getValue())) < 0){
					//if the node being passed in is less than the current value then set left
					//and set parent as current
					if(current.getLeft() == null) {
						current.setLeft(node);
						node.setParent(current);
						current = null;
						size++;
					}
					else {
						current = current.getLeft();
					}
				}
				else{
					//if the node being passed in is greater than the current value then set left and 
					//set current as parent
					if(current.getRight() == null) {
						current.setRight(node);
						node.setParent(current);
						current = null;
						size++;
					}
					else {
						current = current.getRight();
					}
				}
				
			}
		}
		
	}
	
	/**
	 * method that calls the recursive function for insert.
	 * @param node node that needs to be searched for and passed into the recursive function
	 */
	public void bstInsertR(BinaryNode<T> node){
		if(root.getValue() == null) {
			root = node;
			root.setParent(null);
			setRoot(root);
			size++;
			return;
		}
		else {
			bstInsertRecursive(root,node);
			return;
		}
	}
	
	/**
	 * Recursive function which recursively inserts the node.
	 * @param nodeParent parent of the node
	 * @param node node to be inserted
	 */
	public void bstInsertRecursive(BinaryNode<T> nodeParent,BinaryNode<T> node) {
		if((node.getValue().compareTo(nodeParent.getValue())) < 0){
			if(nodeParent.getLeft() == null) {
				nodeParent.setLeft(node);
				node.setParent(nodeParent);
				size++;
				return;
			}
			else {
				bstInsertRecursive(nodeParent.getLeft(),node);
				return;
			}
		}
		else {
			if(nodeParent.getRight() == null) {
				nodeParent.setRight(node);
				node.setParent(nodeParent);
				size++;
				return;
			}
			else {
				bstInsertRecursive(nodeParent.getRight(),node);
				return;
			}
		}
	}
	
	
	/**
	 * same pseudocode as search boolean but different return values.
	 * @param key value being passed to be searched for
	 * @return returns the node if found, null otherwise
	 */
	public BinaryNode<T> bstSearchI(T key){	
		BinaryNode<T> current = root;
		if(current.getValue() == null) {
			System.out.println("Tree is empty!\n");
			return null;
		}
		while(current != null) {
			if((key.compareTo(current.getValue())) == 0) {
				return current; //found 
			}
			else if(((key.compareTo(current.getValue()) < 0))) {
				current = current.getLeft();
			}
			else {
				current = current.getRight();
			}
		}
		return null;//not found
	}
	
	/**
	 * returns the first node found matching that key or return null if not found.
	 * @param key the value being passed in to be searched for
	 * @return returns true or false if found or not
	 */
	public boolean search(T key) {
		BinaryNode<T> current = root;
		if(current.getValue() == null) {
			System.out.println("Tree is empty!\n");
			return false;
		}
		while(current != null) {
			if((key.compareTo(current.getValue())) == 0) {
				return true; //found 
			}
			else if(((key.compareTo(current.getValue()) < 0))) {
				current = current.getLeft();
			}
			else {
				current = current.getRight();
			}
		}
		return false;//not found
	}
	
	/**
	 * method to return the height of the node being passed in.
	 * @param node node that needs to be determined of height
	 * @return the height of the node in the tree
	 */
	public int bstGetHeight(BinaryNode<T> node) {
		if(node == null) {
			return -1;
		}
		if(root.getValue() == null) {
			System.out.println("Tree is empty!\n");
			return 0;
		}
		int leftHeight = bstGetHeight(node.getLeft());
		int rightHeight = bstGetHeight(node.getRight());
		return 1 + Math.max(leftHeight, rightHeight);
		
	}
	
	/**
	 * recursive function which prints the tree INOrder traversal.
	 * @param node node at which the traversal starts
	 */
	public void bstPrintInorderR(BinaryNode<T> node) {
		//method to recursively print the tree InOrder traversal
		if(node != null) {
			bstPrintInorderR(node.getLeft());
			System.out.print("" + node.getValue() + "\n");
			bstPrintInorderR(node.getRight());
		}
	}
	
	/**
	 * Main method of the binaryTree class.
	 * @param args string array
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
