// TO DO: add your implementation and JavaDoc
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;
import java.util.Scanner;

/**
 * Menu uses the MyArray class.
 * The user interface is a menu that displays a list and prompts the user to choose an option.
 * The methods in the Menu class will call the methods in MyArray class to create dynamic arrays and compute metrics. 
 * The template for the Menu class has a stub method for each menu option. 
 * @author Huda Khalid
 */
class Menu {
	/**
	 * PlainArray where all records are loaded at the beginning of program.
	 */
	private SurveyRecord[] plainArray;
    /**
     * dynamic array myArray1 will have all the records loaded into after option 1.
     */
    private MyArray<SurveyRecord> myArray1;
    /**
     * dynamic array myArray2 will have records, deleted, inserted, swapped, etc. after option 3.
     */
    private MyArray<SurveyRecord> myArray2;
    /**
     * dynamic array of type Double where we loop through myArray1 to increment each position by 1 where we see the room.
     */
    private MyArray<Double> arrayTotRooms;
    /**
     * dynamic array with each position as an array of records for each specified decade.
     */
    private MyArray<MyArray<SurveyRecord>> arraysByDecade;
    /**
     * dynamic array of type Integer which has a total of bikeCounts from each record from arraysByDecade.
     */
    private MyArray<Integer> arrayBikeCounts;
    /**
     * dynamic array where you clone myArray1 and delete all the records that are not condos.
     */
    private MyArray<SurveyRecord> arrayCondoYes;
    /**
     * integer to increment by 1 every time getBike() equals '1'.
     */
    private int bikeCount;
    /**
     * add up all the total number of people from arrayCondoYes and divide by the amount of condos to get the avgPersonsPerCondo.
     */
    private Double avgPersonsPerCondo;
    
    /**
     * Load a plain Java array of survey record objects, plainArray, into a dynamic array called myArray1.
     * Print out test: the size of myArray1 = 16688.
     */
    public void option1() {
    	//try the code for option1 but catch a NullPointerException if plainArray is empty or null and continue in the loop
    	try {
    		int i;
    		//load all the records from plainArray into the dynamic array myArray1
    		myArray1 = new MyArray<SurveyRecord>(plainArray.length);
        	for(i = 0; i < plainArray.length; i++) {
        		myArray1.add(i,plainArray[i]);
        	}
        	System.out.printf("MyArray1 size: %s\n",myArray1.size());
    	}catch(NullPointerException e) {
    		System.out.println("PlainArray is empty!\nTry Again!\n");
    		System.out.println("  stub for option 1 \n");
    	}
        
    }
    /**
     * Print first five records of myArray1.
     */
    public void option2() {
    	//check to see if myArray1 is empty
    	//try the code for option2 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
        	int i;
        	//loop through myArray 5 times to print out first 5 records
        	for(i = 0; i < 5; i++) {
        		myArray1.get(i).printRecord();
        	}
            System.out.println("  stub for option 2 \n");
    	}catch(NullPointerException n) {
    		System.out.println("MyArray1 is empty!\nTry Again\n");
    		System.out.println("  stub for option 2 \n");
    	}
    }
    /**
     * Create a dynamic array called myArray2 with the following control numbers.
     * Control Numbers: 11029175, 11069423, 11011113, 11013828, 11067039.
     * Print the final array to the screen.
	 */
    public void option3() {
    	//check to see if myArray1 is empty
    	//try the code for option3 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
    		String str1 = "'11029175'";
        	String str2 = "'11069423'";
        	String str3 = "'11011113'";
        	String str4 = "'11013828'";
        	String str5 = "'11067039'";
        	myArray2 = new MyArray<SurveyRecord>(5);
        	int i;
        	//loop through myArray to see if the string equals the controlNumber for each record
        	for(i = 0 ; i < myArray1.size(); i++) {
        		String str6 = myArray1.get(i).getControl();
        		if(str1.equals(str6)) {
        			//correct way of doing it with adding another ' '
        			//change the rest of the else if statements
        			myArray2.append(myArray1.get(i));
        		}
        		else if(str2.equals(str6)) {
        			myArray2.append(myArray1.get(i));
        		}
        		else if(str3.equals(str6)) {
        			myArray2.append(myArray1.get(i));
        		}
        		else if(str4.equals(str6)) {
        			myArray2.append(myArray1.get(i));
        		}
        		else if(str5.equals(str6)) {
        			myArray2.append(myArray1.get(i));
        		}
        		
        	}
        	//after the records are added print myArray2
        	System.out.printf("MyArray2:\n");
        	for(int j = 0; j < myArray2.size(); j++) {
        		myArray2.get(j).printRecord();
        	}
            System.out.println("  stub for option 3 \n");
    		
    	}catch(NullPointerException n) {
    		System.out.println("MyArray1 is empty!\nTry Again\n");
    		System.out.println("  stub for option 3 \n");
    	}
    	
    }
    /**
     * In myArray2, insert record 11008724 at index 3.
     * Print the final array to the screen.
     */
    public void option4() {
    	//check to see if myArray2 is empty
    	//try the code for option4 but catch a NullPointerException if myArray2 is empty or null and continue in the loop
    	try {
    		String str1 = "'11008724'";
    		//loop through myArray1 to find the record and add at position 3
    		for(int i = 0; i < myArray1.size(); i++) {
        		if(str1.equals(myArray1.get(i).getControl())){
        			myArray2.add(3, myArray1.get(i));
        		}
        	}
        	//print out myArray2
        	if(myArray2.get(3).getControl().equals(str1)) {
        		for(int j = 0; j < myArray2.size(); j ++) {
            		myArray2.get(j).printRecord();
            	}
        	}
        	else {
        		System.out.println("Insert of record 11008724 unsuccessful! Try Again\n");
        		return;
        	}
            System.out.println("  stub for option 4 \n");
    		
    	}catch(NullPointerException n) {
    		System.out.println("MyArray2 is empty!\nTry Again\n");
    		System.out.println("  stub for option 4 \n");
    	}
    	
    }
    /**
     * In myArray2, append record 11063694.
     * Print the final array to the screen.
     * use append() function of MyArray.class.
     */
    public void option5() {
    	//try the code for option5 but catch a NullPointerException if myArray2 is empty or null and continue in the loop
    	try {
        	String str1 = "'11063694'";
        	for(int i = 0; i < myArray1.size(); i++) {
        		if(str1.equals(myArray1.get(i).getControl())){
        			myArray2.append(myArray1.get(i));
        		}
        	}
        	for(int j = 0; j < myArray2.size(); j ++) {
        		myArray2.get(j).printRecord();
        	}
            System.out.println("  stub for option 5 \n");
    	}catch(NullPointerException n) {
    		System.out.println("MyArray2 is empty!\nTry Again\n");
    		System.out.println("  stub for option 5 \n");
    	}
    }
    /**
     * In myArray2, delete record 11011113.
     * Print the final array to the screen.
     * Use delete() function of MyArray.class.
     */
    public void option6() {
    	//if myArray2 is null or empty print out error message and continue through loop
    	if(myArray2 != null){
    		String str1 = "'11011113'";
    		//loop through myArray2 to find the record and delete it
    		for(int i = 0; i < myArray2.size(); i++) {
    			if(str1.equals(myArray2.get(i).getControl())){
        			myArray2.delete(i);
        		}
        	}
        	//loop through myArray2 to print out the records.
        	for(int j = 0; j < myArray2.size(); j ++) {
        		myArray2.get(j).printRecord();
        	}
        	System.out.println("  stub for option 6 \n");
    	}
    	else{
    		System.out.println("Deletion of record 11011113 unsuccessful! Try Again\n");
    		System.out.println("  stub for option 6 \n");
    		return;
    	}
        
    }
    /**
     * In myArray2, swap record 11069423 with record 11067039.
     * Use swap() and firstIndexOf() functions of MyArray.class.
     * Print the final array to the screen.
     */
    public void option7() {
    	//if myArray2 is null or empty print out error message and continue through loop
    	if(myArray2 != null) {
    		String str1 = "'11069423'";
        	String str2 = "'11067039'";
        	int firstIndex = 0;
        	int secIndex = 0;
        	for(int i = 0; i < myArray2.size(); i++) {
        		//loop through myArray2 to find the record and the index and store it
        		if(str1.equals(myArray2.get(i).getControl())) {
        			firstIndex = myArray2.firstIndexOf(myArray2.get(i));
        			
        		}
        		//loop through myArray2 to find the second record and the index and store it
        		else if(str2.equals(myArray2.get(i).getControl())) {
        			secIndex = myArray2.firstIndexOf(myArray2.get(i));
        		}
        	}
        	//once found the indeces then make the swap and print out myArray2
        	if(myArray2.swap(firstIndex,secIndex)) {
        		for(int j = 0; j < myArray2.size(); j ++) {
            		myArray2.get(j).printRecord();
            	}
        		System.out.println("  stub for option 7 \n");
        	}
        	else {
        		System.out.println("Swap unsuccessful!");
        		System.out.println("  stub for option 7 \n");
        	}
    	}
    	else{
    		System.out.println("MyArray2 is empty\nSwap unsuccessful! Try Again\n");
    		System.out.println("  stub for option 7 \n");
    		return;
    	}
        
    }
    /**
     * Create a dynamic array called arrayTotRooms where size = 28, index values will be 0 - 27.
     * Each element will store the count of records where totRooms equals the index value.
     * For example, if the number of records where totRooms = 8 is six, then arrayTotRooms[8] = 6 .
     * For categories 1 – 26, compute the average total rooms across all units and store the result in arrayTotRooms[0].
     * Test: arrayTotRooms[0] should round to 5.57.
     */
    public void option8() {
    	//try the code for option8 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
    		//loop through arrayTotRooms and set all positions to zero
    		arrayTotRooms = new MyArray<Double>(28);
    		for(int k = 0; k < arrayTotRooms.capacity(); k++) {
        		arrayTotRooms.add(k, 0.0);
        	}
        	double average = 0;
        	double rooms = 0;
        	for(int i = 0; i <  myArray1.size(); i++) {
        		for(int j = 0; j < arrayTotRooms.capacity(); j++) {
        			//convert the index into totRooms to compare to getTotalRooms of myArray1
        			String totRooms = Integer.toString(j);
        			if((totRooms).equals(myArray1.get(i).getTotalRooms())) {
        				//if the index equals the number of rooms a record has increment that position in arrayTotRooms by 1
        				rooms = arrayTotRooms.get(j);
        				arrayTotRooms.replace(j,++rooms);
        				break;
        			}
        		}
        		
        	}
        	//add all the totalRooms and multiply by the number of each position to get each type of room
        	for(int j = 1; j < 27; j ++) {
        		average += j * arrayTotRooms.get(j);
        		
        	}
        	//get the average by dividing by the size of myArray1 and replace it at position 0
        	average /= myArray1.size();
        	arrayTotRooms.replace(0, average);
        	for(int j = 0; j < arrayTotRooms.size(); j ++) {
        		System.out.println(arrayTotRooms.get(j));
        	}
        	if(arrayTotRooms == null) {
        		System.out.println("Error computing units for each value of totRooms!\n Try Again\n");
        		return;
        	}
            System.out.println("  stub for option 8 \n");
    	}catch(NullPointerException e) {
    		System.out.println("MyArray1 is empty\nTryAgain!\n");
    		System.out.println("  stub for option 8 \n");
    	}
    }
    /**
     * Compute a count of bike records.
     * Create an int called bikeCount that stores the number of records where bike = ‘1’.
     * Test for myArray1, bikeCount = 75.
     */
    public void option9() {
    	//try the code for option9 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
    		bikeCount = 0;
    		//loop through myArray1 and increment bikeCount by 1 if getBike() equal '1'
    		for(int i = 0; i < myArray1.size(); i++) {
    			if(myArray1.get(i).getBike().equals("'1'")) {
        			bikeCount += 1;
        		}
        	}
        	System.out.println(bikeCount);
    	}catch(NullPointerException e) {
    		System.out.println("MyArray1 is empty\nTry Again!\n");
    		System.out.println("  stub for option 9 \n");
    	}
    }
    /**
     * Create an array of arrays called arraysByDecade using the variable yrBuilt.
     * There will be eleven sub-arrays, one for each value in yrBuilt.
     * To test, the size and capacity of the sub-array for 1970 is 2553 and 4096 respectively.
     * Test: the sum of the sizes of the sub-arrays should match the size of myArray1.
     */
    public void option10() {
    	//try the code for option10 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
    		arraysByDecade = new MyArray<MyArray<SurveyRecord>>(11);
        	String year = "2010";
        	int totalSum = 0;
        	MyArray<SurveyRecord> yearArray;
        	//loop through and add 2010 records in an array and add the whole record into arraysbydecade at position 0
        	for(int j = 0; j < arraysByDecade.capacity(); j++) {
        		yearArray = new MyArray<SurveyRecord>();
            	for(int i = 0; i < myArray1.size(); i++) {
            		if(year.equals(myArray1.get(i).getYrBuilt())){
            			yearArray.append(myArray1.get(i));
            		}
            		
            	}
            	//converts year to integer to subtract 10 years 
            	int yearInt = Integer.parseInt(year);
            	//adding total for the sub-arrays which to equal to myArray1.size()
            	totalSum += yearArray.size();
            	arraysByDecade.append(yearArray);
            	yearInt -= 10;
            	//once subtracted convert back to string and compare
            	year = Integer.toString(yearInt);
        	}
        	//for loop to iterate through myArray1 to find yrBuilt = 1919 and add
        	yearArray = new MyArray<SurveyRecord>();
        	for(int k = 0; k < myArray1.size(); k++){
        		if(("1919").equals(myArray1.get(k).getYrBuilt())) {
        			yearArray.append(myArray1.get(k));
            	}	
        	}
        	
        	totalSum += yearArray.size();
        	arraysByDecade.append(yearArray);
        	//print statement to print out the sum of subarrays
        	System.out.println(totalSum);
            System.out.println("  stub for option 10 \n");
    	}catch(NullPointerException e) {
    		System.out.println("MyArray1 is empty\nTry Again!\n");
    		System.out.println("  stub for option 10 \n");
    	}
    	
    }
    /** 
     * Create an array of bike record counts by decade.
     * Compute bikeCount for each sub-array in arraysByDecade.
     * Test: make sure the total across all the sub-arrays matches the result for option 9 above.
     */
    public void option11() {
    	//try the code for option11 but catch a NullPointerException if arraysByDecade is empty or null and continue in the loop
    	try {
    		arrayBikeCounts = new MyArray<Integer>(11);
        	MyArray<SurveyRecord> yearArray;
        	int totalBike = 0;
        	//for loop to loop through every year and increment totalBike by 1 where getBike() equals '1'
        	for(int i = 0; i < arraysByDecade.size(); i++) {
        		yearArray = arraysByDecade.get(i);
        		for(int j = 0; j < yearArray.size(); j++){
        			if((yearArray.get(j).getBike()).equals("'1'")){
        				totalBike += 1;
        			}
        		}
        		//appends the number of totalBikes from each year into arrayBikeCounts
        		arrayBikeCounts.append(totalBike);
        		//set the count equals to zero for the next year
        		totalBike = 0;
        	}
        	System.out.println("ArrayBikeCounts: \n");
        	//print out the arrayBikeCounts to see how many bikes per year
        	for(int i = 0; i < arrayBikeCounts.size(); i++) {
        		System.out.println(arrayBikeCounts.get(i));
        		totalBike += arrayBikeCounts.get(i);
        	}
        	System.out.println("Total of ArrayBikeCounts: \n");
        	System.out.println(totalBike);
            System.out.println("  stub for option 11 \n");
    	}catch(NullPointerException e) {
    		System.out.println("ArraysByDecade is empty!\nTry Again\n");
    		System.out.println("  stub for option 11 \n");
    	}
    }
    /**
     * Create a dynamic array called arrayCondoYes that stores the number of records where condo = ‘1’ .
     * Clone myArray1 and delete the condos.
     * Test: the size of arrayCondoYes = 1385, the capacity = 4172.
     * Create an array of condo units.
	 */
    public void option12() {
    	//try the code for option12 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
    		//clone the myArray1 and set it equal to arrayCondoYear
    		arrayCondoYes = myArray1.clone();
    		//for loop to delete all the record where getCondo() equals '1'
    		for(int i = arrayCondoYes.size()-1; i >= 0; i--) {
    			if((arrayCondoYes.get(i).getCondo().equals("'2'"))){
        			arrayCondoYes.delete(i);
        		}
        	}
        	//print out size and capacity for testing
        	System.out.println(arrayCondoYes.size());
        	System.out.println(arrayCondoYes.capacity());
            System.out.println("  stub for option 12 \n");
    	}catch(NullPointerException e) {
    		System.out.println("MyArray1 is empty\nTry Again!\n");
    		System.out.println("  stub for option 12 \n");
    	}
    }
    /**
     *Compute the average number of people per condo.
     *Use arrayCondoYes and the numPeople to compute a double called avgPersonsPerCondo.
     *Test: avgPersonsPerCondo should round to 1.6.
	 */
    public void option13() {
    	//try the code for option13 but catch a NullPointerException if arrayCondoYes is empty or null and continue in the loop
    	try {
    		avgPersonsPerCondo = 0.0;
        	double numPeople  = 0.0;
        	//loop through arrayCondoYes to add the number of people in each condo
        	for(int i = 0; i < arrayCondoYes.size(); i++) {
        		//convert each number of people to a double
        		numPeople = Integer.parseInt(arrayCondoYes.get(i).getNumPeople());
        		if(numPeople >= 0) {
    	    		avgPersonsPerCondo += numPeople;
        		}
        	}
        	//divide total number of people by the amount of condos to get the avg person per condo
        	avgPersonsPerCondo /= arrayCondoYes.size();
        	System.out.println(avgPersonsPerCondo);
            System.out.println("  stub for option 13 \n");
    	}catch(NullPointerException e) {
    		System.out.println("ArrayCondoYes is empty\nTry Again!\n");
    		System.out.println("  stub for option 13 \n");
    	}
    	
    }
    /**
	 *Sort myArray1 by control number in ascending order, from smallest to largest.
	 */
    public void option14() {
    	//try the code for option14 but catch a NullPointerException if myArray1 is empty or null and continue in the loop
    	try {
    		int n = myArray1.size();
    		//create an array which will copy only the control numbers
    		MyArray<Integer> controlArray = new MyArray<Integer>();
    		String control;
        	int controlInt;
        	//loop through, convert and add all control numbers to integers and use replaceAll to replace the single quotes
        	for(int i = 0; i < n; i++) {
        		control = myArray1.get(i).getControl();
        		control = control.replaceAll("'", "");
        		controlInt = Integer.parseInt(control);
        		controlArray.append(controlInt);
        	}
        	//sort the controlArray starting from the last index
        	for(int j = n - 1; j >= 0; j--) {
        		for(int k = n - 1; k >= j; k--) {
        			if(controlArray.get(k) < controlArray.get(j)) {
        				controlArray.swap(j, k);
        			}
        		}
        	}
        	//create a clonedArray to add back all the control numbers with their records in their respected order
        	//also convert all the control numbers to strings and adding their single quotes back
        	MyArray<SurveyRecord> clonedArray = new MyArray<SurveyRecord>();
        	for(int l = 0; l < controlArray.size(); l++) {
        		control = Integer.toString(controlArray.get(l));
        		control = '\'' + control + '\'';
        		//System.out.println(control);
        		for(int m = 0; m < n; m++) {
        			if(myArray1.get(m).getControl().equals(control)) {
        				clonedArray.append(myArray1.get(m));
        			}
        		}
        	}
        	//set myArray to the sorted clonedArray
        	myArray1 = clonedArray;
            System.out.println("  stub for option 14 \n");
    	}catch(NullPointerException e) {
    		System.out.println("MyArray1 is empty\nTry Again!\n");
    		System.out.println("  stub for option 14 \n");
    	}
    	
    }

    /**
     * Main Method to load all the data and call methods from Menu.
     * 
     * @param args is the string from the command line being passed in
     * 
     */
    public static void main(String[] args) {
    	Menu menuObj = new Menu("inputFile.txt");
    	menuObj.init();
    	menuObj.step();
    	menuObj.finish();
    }
    
    /**
     * string that saves the input text file name.
     */
    private String inputFileName;
    
    /**
     * raw is an array of type surveyRecord which helps to loads the data.
     */
    private SurveyRecord[] raw;

    /**
     * This method gets the input file name.
     * For example is get inputFile.txt.
     * 
     * @param inputFileName is the string input file name being passed in
     * 
     */
    public Menu(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * this method calls the loadData method to load all the data into the private variable plainArray.
     * 
     * @return true
     */
    public boolean init() {
        raw = loadData();
        plainArray = makePlainArray(raw);
        return true;
    }
    
    /**
     * This method loops through until repeatLoop is false.
     * Each case calls on the different options depending on what the user has chosen.
     * This method also prints out the menu option for the user
     * 
     * @return repeatLoop
     */
    public boolean step() {
        boolean repeatLoop = true;
        while (repeatLoop) {
        	System.out.println("\n");
            System.out.println(" 1. Load objects into an array called myArray1");
            System.out.println(" 2. Print the first 5 records");
            System.out.println(" 3. Create an array called myArray2 with 5 specified records");
            System.out.println(" 4. In myArray2, insert record 11008724 at index 3");
            System.out.println(" 5. In myArray2, append record 11063694");
            System.out.println(" 6. In myArray2, delete record 11011113 ");              
            System.out.println(" 7. In myArray2, swap record 11069423 with record 11067039");
            System.out.println(" 8. Compute how many units there are for each value of totRooms");
            System.out.println(" 9. Compute a count of bike records");
            System.out.println("10. Create an array containing arrays by decade");               
            System.out.println("11. Create an array of bike record counts by decade");
            System.out.println("12. Create an array of condo units");
            System.out.println("13. Compute the average number of people per condo");
            System.out.println("14. Sort myArray1 by control number");
            System.out.println("15. Exit");
            System.out.println("\n");
            Scanner sd = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            int num = sd.nextInt();
            switch (num) {
                case 1:
                    option1();
                    break;

                case 2:
                    option2();
                    break;

                case 3:
                    option3();
                    break;

                case 4:
                    option4();
                    break;

                case 5:
                    option5();
                    break;

                case 6:
                    option6();
                    break;

                case 7:
                    option7();
                    break;

                case 8:
                    option8();
                    break;

                case 9:
                    option9();
                    break;

                case 10:
                    option10();
                    break;

                case 11:
                    option11();
                    break;

                case 12:
                    option12();
                    break;

                case 13:
                    option13();
                    break;

                case 14:
                    option14();
                    break;

                case 15:
                    repeatLoop = false;
                    break;
            }
        }

        return false;
    }
    /**
     * This method finishes the program when the user enters option15.
     */
    public void finish() {
        System.out.println(" Program finished \n");
    }

    /**
     * This method creates the plainArray and loads all the data with the param arrayIn.
     * 
     * @param arrayIn is the array being passed in with all the data
     * 
     * @return arrayOut 
     */
    public SurveyRecord[] makePlainArray(SurveyRecord[] arrayIn) {
        SurveyRecord[] arrayOut = new SurveyRecord[(arrayIn.length / 4)];
        arrayOut[0] = arrayIn[0];
        int j = 0;
        for (int i = 1; i < arrayIn.length - 1; i = i + 4) {
            arrayOut[j] = arrayIn[i];
            j++;
        }
        return arrayOut;
    }
    /**
     * This method loads the data and returns a record of type SurveyRecord.
     * 
     * @return records
     */
    public SurveyRecord[] loadData() {

        List<String> list = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFileName))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        SurveyRecord[] records = new SurveyRecord[list.size() - 1];
        for (int i = 1; i < list.size() - 1; i++) {
            records[i - 1] = new SurveyRecord(list.get(i));
        }

        Random random = new Random();
        random.setSeed(1);
        for (int i = 0; i < records.length; i++) {
            int index = random.nextInt(records.length);
            SurveyRecord temp = records[index];
            records[index] = records[i];
            records[i] = temp;
        }
        return records;
    }
}
