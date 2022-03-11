import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
 * Reads in a list of integer key values from an input file and inserts them into a binary tree called btree1.
 * Prints the tree using the bstPrintInorder method.
 * @author hudakhalid
 *
 */
public class P3 {

	/**
	 * empty constructor for the P3 class.
	 */
	public P3() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Main method where all the action happens of inserting/removing the nodes and bst.
	 * @param args in terminal where we will pass in the name of the input file
	 * @throws FileNotFoundException if file not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		BinaryTree<Integer> btree1 = new BinaryTree<Integer>();
		BinaryNode<Integer> root = null;
		BinaryNode<Integer> bnode = null;
		//input fileName
		//String file = args[0];
		File fileName = new File("inputFile.txt");
		try {
			Scanner sc = new Scanner(fileName);
			String num = sc.nextLine();
			int key = 0;
			//if statement to initialize, set, and insert the root into the BST
			if(sc.hasNextLine()) {
				key = Integer.parseInt(num);
				root = new BinaryNode<Integer>(key);
				btree1.bstInsertR(root);
				btree1.setRoot(root);
			}
			//while loop that creates nodes and inserts them into the BST in the respective order
			while(sc.hasNextLine()) {
				num = sc.nextLine();
				key = Integer.parseInt(num);
				bnode = new BinaryNode<Integer>(key);
				btree1.bstInsertR(bnode);
			}
			btree1.bstPrintInorderR(root);
			
			//System.out.printf("Size of bst: %d\n",btree1.getSize());
			//System.out.printf("Height of bst: %d\n",btree1.getHeight());
			//System.out.println(btree1.search(50));
			//BinaryNode<Integer> node = btree1.bstSearchI(50);
			//System.out.print(btree1.bstSearchI(50));
			//System.out.println(btree1.removeKeyR(34).getValue());
			//btree1.bstPrintInorderR(root);
			System.out.println(btree1.bstFindMaxR(root).getValue());
			
			sc.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
