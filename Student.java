import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import java.util.Scanner;
import org.apache.ibatis.jdbc.ScriptRunner;

public class Student{
    static Connection con;
    static Statement stmt;

    public static void main(String argv[])
    {
    	Scanner input = new Scanner(System.in);
    	connectToDatabase();

    	int userinput = -1;

    	do {
    		System.out.println("Press 1 to view the contents of a specific table.");
    		System.out.println("Press 2 to search Transactions table by Transaction ID.");
    		System.out.println("Press 3 to search by input attributes and output attributes.");
    		System.out.println("Press 4 to exit.");
    		
    		userinput = input.nextInt();
    		
    		if (userinput == 1) {
    			int input1;
    			int input2;
    			int input3;
    			int input4;
    			System.out.println("Show Product table? (0 - NO, 1 - YES)");
    			input1 = input.nextInt();
        		System.out.println("Show Customer table? (0 - NO, 1 - YES)");
        		input2 = input.nextInt();
        		System.out.println("Show Transactions table? (0 - NO, 1 - YES)");
        		input3 = input.nextInt();
        		System.out.println("Show Transactions_Contains table? (0 - NO, 1 - YES)");
        		input4 = input.nextInt();
        		
        		if (input1 == 0 && input2 == 0 && input3 == 0 && input4 == 0) {
        			System.out.println("There must be at least one input.  Please try again");
        		}
        		
        		if (input1 == 1) {
        			String query = "SELECT * FROM Product";
        			try {
						Statement S = con.createStatement();
						ResultSet R = S.executeQuery(query);
						
						System.out.println("\n\nPRODUCT: \n\n");
						
						
						while (R.next()) {
							String upc = R.getString("UPC");
							String brand = R.getString("brand");
							String productName = R.getString("product_name");
							String productDescription = R.getString("product_description");
							String category = R.getString("category");
							int markedPrice = R.getInt("marked_price");
							int quantity = R.getInt("quantity");
							
							System.out.printf("%s || %s || %s || %s || %s || %d || %d\n", upc, brand, productName, productDescription, category, markedPrice, quantity);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
        			
        			
        		}
        		if (input2 == 1) {
        			String query = "SELECT * FROM Customer";
        			try {
						Statement S = con.createStatement();
						ResultSet R = S.executeQuery(query);
						
						System.out.println("\n\nCUSTOMER: \n\n");
						
						while (R.next()) {
							int customerId = R.getInt("customer_ID");
							String firstName = R.getString("first_name");
							String lastName = R.getString("last_name");
							int age = R.getInt("age");
							String gender = R.getString("gender");
							int zipCode = R.getInt("zip_code");
							
							System.out.printf("%d || %s || %s || %d || %s || %d\n", customerId, firstName, lastName, age, gender, zipCode);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
        		}
        		if (input3 == 1) {
        			String query = "SELECT * FROM Transactions";
        			try {
						Statement S = con.createStatement();
						ResultSet R = S.executeQuery(query);
						
						System.out.println("\n\nTRANSACTIONS: \n\n");
						
						while (R.next()) {
							int transactionId = R.getInt("transaction_ID");
							int customerId = R.getInt("customer_ID");
							String transactionDate = R.getString("transaction_date");
							String paymentMethod = R.getString("payment_method");
							int total = R.getInt("total");
							
							System.out.printf("%d || %d || %s || %s || %d\n", transactionId, customerId, transactionDate, paymentMethod, total);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
        		}
        		if (input4 == 1) {
        			String query = "SELECT * FROM Transaction_Contains";
        			try {
						Statement S = con.createStatement();
						ResultSet R = S.executeQuery(query);
						
						System.out.println("\n\nTRANSACTION_CONTAINS: \n\n");
						
						while (R.next()) {
							int transactionId = R.getInt("transaction_ID");
							String upc = R.getString("UPC");
							int quantity = R.getInt("quantity");
							
							System.out.printf("%d || %s || %d\n\n", transactionId, upc, quantity);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
        		}
        		
        		
        		
    		}
    		
    		else if (userinput == 2) {
    			System.out.println("Enter Transaction ID to search by: \n");
    			int transactionId1 = input.nextInt();
    			String TID = Integer.toString(transactionId1);
    			
    			String query = "SELECT * FROM Transactions WHERE Transactions.transaction_ID = " + TID;
    			
    			try {
					Statement S = con.createStatement();
					ResultSet R = S.executeQuery(query);
					
					if (!R.next()) {
						System.out.println("Transaction ID is invalid.");
					}
					else {
						R = S.executeQuery(query);
						System.out.println("\n\nTRANSACTION: \n");

						while (R.next()) {
							int transactionId = R.getInt("transaction_ID");
							int customerId = R.getInt("customer_ID");
							String transactionDate = R.getString("transaction_date");
							String paymentMethod = R.getString("payment_method");
							int total = R.getInt("total");

							System.out.printf("%d || %d || %s || %s || %d\n", transactionId, customerId, transactionDate, paymentMethod, total);
						}

						query = "SELECT quantity FROM Transaction_Contains WHERE Transaction_Contains.transaction_ID = " + TID;
						R = S.executeQuery(query);
						double averageQuantity = 0;
						int count = 0;


						while (R.next()) {
							int quantity = R.getInt("quantity");
							averageQuantity += quantity; 
							count++;
						}

						averageQuantity /= count;
						System.out.printf("Average Quantity: %.2f\n\n", averageQuantity);

					}
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
    			
    			
    		}
    		
    		else if (userinput == 3) {
    			
    			String queryBuilder;
    			int cidInputAnswer = -1;
    			int totalInputAnswer = -1;
    			int upcInputAnswer = -1;
    			int quantityInputAnswer = -1;  
    			int cidInput = -1;
    			double totalInput = -1;
    			int upcInput = -1;
    			int quantityInput = -1;
    			int tidOutput = -1;
    			int cidOutput = -1;
    			int dateOutput = -1;
    			int paymentOutput = -1;
    			int totalOutput = -1;
    			int upcOutput = -1;
    			int quantityOutput = -1;
    			int lastAndFlag = 0;
    			
    			
    			System.out.println("Select which INPUT fields should be included in the query: ");
    			System.out.println("Customer ID? (0 - NO, 1 - YES) ");
    			cidInputAnswer = input.nextInt();
    			
    			System.out.println("Total? (0 - NO, 1 - YES) ");
    			totalInputAnswer = input.nextInt();
    			
    			System.out.println("UPC? (0 - NO, 1 - YES) ");
    			upcInputAnswer = input.nextInt();
    			
    			System.out.println("Quantity? (0 - NO, 1 - YES) ");
    			quantityInputAnswer = input.nextInt();
    			
    			if (cidInputAnswer == 1 || totalInputAnswer == 1 || upcInputAnswer == 1 || quantityInputAnswer == 1) {
    			

    				if (cidInputAnswer == 1) {
    					lastAndFlag = 1;
    					System.out.println("Enter Customer ID: ");
    					cidInput = input.nextInt();
    				}

    				if (totalInputAnswer == 1) {
    					lastAndFlag = 2;
    					System.out.println("Enter Total: ");
    					totalInput = input.nextInt();
    				}

    				if (upcInputAnswer == 1) {
    					lastAndFlag = 3;
    					System.out.println("Enter UPC: ");
    					upcInput = input.nextInt();
    				}

    				if (quantityInputAnswer == 1) {
    					lastAndFlag = 4;
    					System.out.println("Enter Quantity: ");
    					quantityInput = input.nextInt();  			
    				}



    				System.out.println("Select which OUTPUT fields should be included in the query: ");
    				System.out.println("Transaction ID? (0 - NO, 1 - YES) ");
    				tidOutput = input.nextInt();

    				System.out.println("Customer ID? (0 - NO, 1 - YES) ");
    				cidOutput = input.nextInt();

    				System.out.println("Date? (0 - NO, 1 - YES) ");
    				dateOutput = input.nextInt();

    				System.out.println("Payment Method? (0 - NO, 1 - YES) ");
    				paymentOutput = input.nextInt();

    				System.out.println("Total? (0 - NO, 1 - YES) ");
    				totalOutput = input.nextInt();

    				System.out.println("UPC? (0 - NO, 1 - YES) ");
    				upcOutput = input.nextInt();

    				System.out.println("Quantity? (0 - NO, 1 - YES) ");
    				quantityOutput = input.nextInt();


    				queryBuilder = "SELECT ";
    				
    				if (tidOutput == 1 || cidOutput == 1 || dateOutput == 1 || paymentOutput == 1 || totalOutput == 1 || upcOutput == 1 || quantityOutput == 1) {

    					boolean first = false;

    					if (tidOutput == 1) {
    						queryBuilder += "Transactions.transaction_id";
    						first = true;
    					}

    					if (cidOutput == 1) {
    						if (!first) {
    							queryBuilder += "customer_id";
    							first = true;
    						}
    						else {
    							queryBuilder += ", customer_id";
    						}

    					}

    					if (dateOutput == 1) {
    						if (!first) {
    							queryBuilder += "transaction_date";
    							first = true;
    						}
    						else {
    							queryBuilder += ", transaction_date";
    						}

    					}

    					if (paymentOutput == 1) {
    						if (!first) {
    							queryBuilder += "payment_method";
    							first = true;
    						}
    						else {
    							queryBuilder += ", payment_method";
    						}

    					}

    					if (totalOutput == 1) {
    						if (!first) {
    							queryBuilder += "total";
    							first = true;
    						}
    						else {
    							queryBuilder += ", total";
    						}

    					}

    					if (upcOutput == 1) {
    						if (!first) {
    							queryBuilder += "UPC";
    							first = true;
    						}
    						else {
    							queryBuilder += ", UPC";
    						}

    					}

    					if (quantityOutput == 1) {
    						if (!first) {
    							queryBuilder += "quantity";
    							first = true;
    						}
    						else {
    							queryBuilder += ", quantity";
    						}

    					}

    					queryBuilder += " FROM Transactions, Transaction_Contains WHERE Transactions.transaction_id = Transaction_Contains.transaction_id AND";    			

    					if (cidInputAnswer == 1) {
    						if (lastAndFlag == 1) {
    							queryBuilder += " customer_id = " + cidInput;
    						}
    						else {
    							queryBuilder += " customer_id = " + cidInput + " AND";
    						}

    					}
    					if (totalInputAnswer == 1) {
    						if (lastAndFlag == 2) {
    							queryBuilder += " total = " + totalInput;
    						}
    						else {
    							queryBuilder += " total = " + totalInput + " AND";
    						}
    					}
    					if (upcInputAnswer == 1) {
    						if (lastAndFlag == 3) {
    							queryBuilder += " UPC = " + upcInput;
    						}
    						else {
    							queryBuilder += " UPC = " + upcInput + " AND";
    						}
    					}
    					if (quantityInputAnswer == 1) {
    						if (lastAndFlag == 4) {
    							queryBuilder += " quantity = " + quantityInput;
    						}
    					}


    					try {
    						Statement S = con.createStatement();
    						ResultSet R = S.executeQuery(queryBuilder);
    						ResultSetMetaData metaData = R.getMetaData();
    						int columnsNum = metaData.getColumnCount();

    						if (!R.next()) {
    							System.out.println("No results found.");
    						}
    						else {
    							R = S.executeQuery(queryBuilder);
    							System.out.println("\n\nTRANSACTIONS: \n");
    						}

    						boolean firstRun = false;

    						while (R.next()) {

    							for(int i = 1; i <= columnsNum; i++) {
    								if(!firstRun) {
    									for(int j = 1; j <= columnsNum; j++) {
    										System.out.print(metaData.getColumnName(j) + "   ");
    									}
    									firstRun = true;
    									System.out.println("\n");
    								}

    								String columnValue = R.getString(i);
    								System.out.print(columnValue + "        ||        ");
    							}

    							System.out.println("\n");


    						}
    					}catch (SQLException e) {
    						e.printStackTrace();
    					}
    			
    				} else {
    					System.out.println("At least one output should be specified.  Please try again.");
    				}
    			}  else {
    				System.out.println("At least one input should be specified.  Please try again.");
    			}
    		
    		}
    		
    		else if (userinput == 4) {
				break;
			}
    		
    		else {
    			System.out.println("Invalid input given. Please select from the valid menu options.");
    		}

    	
    	
    	
    	} while (userinput != 4);
    	
    	input.close();
    	
    	System.out.println("Program terminating.");
    	
    	
    	
    }

    public static void connectToDatabase()
    {
	String driverPrefixURL="jdbc:oracle:thin:@";
	String jdbc_url="artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

    
	
        // IMPORTANT: DO NOT PUT YOUR LOGIN INFORMATION HERE. INSTEAD, PROMPT USER FOR HIS/HER LOGIN/PASSWD
        String username="xxxxx";
        String password="xxxxx";
        
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Oracle username: ");
        username = input.next();
        System.out.println("Enter Oracle password: ");
        password = input.next();
        
        String scriptPath;
        
        System.out.println("Enter path to oracle.sql file: ");
        scriptPath = input.next();
        

	
        try{
	    //Register Oracle driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }

       try{
            System.out.println(driverPrefixURL+jdbc_url);
            con=DriverManager.getConnection(driverPrefixURL+jdbc_url, username, password);
            DatabaseMetaData dbmd=con.getMetaData();
            stmt=con.createStatement();

            System.out.println("Connected.");

            if(dbmd==null){
                System.out.println("No database meta data");
            }
            else {
                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
                System.out.println("Database Driver Name: "+dbmd.getDriverName());
                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
                
                ScriptRunner scriptRunner = new ScriptRunner(con);
                Reader reader = new BufferedReader(new FileReader(scriptPath));
                
                scriptRunner.runScript(reader);
            }
        }catch( Exception e) {e.printStackTrace();}

    }// End of connectToDatabase()
}// End of class

