
// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson

import java.io.Console;
import java.sql.*; // Enable SQL processing
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class JDBC {
	 static DatabaseMetaData metadata = null;

	// public static void insertPerson(int type)
	// {
	//
	// switch (type) {
	// case 1:
	// insertPerson("movies");
	// break;
	// case 2:
	// insertPerson("stars");
	// break;
	// case 3:
	// insertPerson("stars_in_movies");
	// break;
	// case 4:
	// insertPerson("genres");
	// break;
	// case 5:
	// insertPerson("genres_in_movies");
	// break;
	// case 6:
	// insertPerson("creditcards");
	// break;
	// case 7:
	// insertPerson("customers");
	// break;
	// case 8:
	// insertPerson("sales");
	// break;
	// default:
	// System.out.println("\nPick a valid number...");
	// break;
	// }
	// }
	// public static void insertPerson(String type)
	// {
	// System.out.println("Attempting insert into "+type);
	//
	// }
	public static String getMovies(String inp, Connection connection) throws SQLException {
		Statement select = connection.createStatement();
		String more = getFN(inp);
		try {
			ResultSet result = select
					.executeQuery("SELECT sm.movie_id, m.title, m.year, m.director, m.banner_url, m.trailer_url "
							+ " FROM movies m, stars s, " + "stars_in_movies sm "
							+ "WHERE s.id=sm.star_id AND m.id=sm.movie_id " + more + "ORDER BY sm.movie_id;");
			StringBuilder output = new StringBuilder();

			while (result.next()) {
				output.append("Movie Id: " + result.getInt(1) + "\n");
				output.append("Title: " + result.getString(2) + "\n");
				output.append("Year: " + result.getString(3) + "\n");
				output.append("Director: " + result.getString(4) + "\n");
				output.append("Banner URL: " + result.getString(5) + "\n");
				output.append("Trailer URL: " + result.getString(5) + "\n");
				output.append("=======================\n");

			}
			String outputStr = output.toString();
			if (outputStr.length() > 0 && outputStr != null)
				return "Movies appeared in\n=====================\n" + outputStr;
			else
				return ("\n=============================\nThe person could not be found\n=============================\n");

		} catch (SQLException e) {
			System.out.println(
					"\n=============================\nThe person could not be found\n=============================\n");
			return "";
		}

	}

	private static String getFN(String inp) {
		String out = "";
		if (inp.matches("^-?\\d+$")) {
			// System.out.println("......"+inp);
			out = "AND s.id=" + inp + " ";
		} else {
			String[] splt = inp.split(" ");

			if (splt.length == 0)
				out = "";
			else if (splt.length == 1)
				out = "AND (s.first_name= '" + splt[0] + "' OR s.last_name= '" + splt[0] + "') ";
			else if (splt.length == 2)
				out = "AND s.first_name= '" + splt[0] + "' AND s.last_name= '" + splt[1] + "' ";
			else {
				// for(int i=1;i<splt.length-1;++i)
				// {
				// more+="AND
				// (s.first_name="+Arrays.toString(Arrays.copyOfRange(splt,0,i))
				// +
				// " AND
				// s.last_name="+Arrays.toString(Arrays.copyOfRange(splt,i,splt.length))
				// + ") OR";
				// }
				System.out.println("Something went wrong. Please enter the First Name and Last Name Sepeartely: ");
				Scanner a = new Scanner(System.in);
				System.out.println("First Name: ");
				String first = a.nextLine();

				System.out.println("Last Name: ");
				String last = a.nextLine();

				out = "AND s.first_name='" + first + "' AND s.last_name='" + last + "' ";

			}

		}
		return out;
	}
	private static void insertCustomer(Connection connection) throws SQLException {
		/*
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(50) DEFAULT '' NOT NULL,
	last_name VARCHAR(50) DEFAULT '' NOT NULL,
	cc_id VARCHAR(20) DEFAULT '' NOT NULL,
	address VARCHAR(200) DEFAULT '' NOT NULL,
	email VARCHAR(50) DEFAULT '' NOT NULL,
	password VARCHAR(20) DEFAULT '' NOT NULL,
	FOREIGN KEY(cc_id) REFERENCES creditcards(id)
		 */
		boolean lookingForName = true;
		Scanner sc = new Scanner(System.in);
		
		ArrayList<String> cols=new ArrayList<String>();
		ArrayList<Object> inputs=new ArrayList<Object>();
			
		cols.add("first_name");
		cols.add("last_name");
		while (lookingForName) {
			System.out.println("Enter the Customer's Name <First> <Last>: ");
			
			String inp = sc.nextLine();
			String[] splt = inp.split(" ");

			String fn = "";
			String ln = "";
			
			
			if (splt.length == 0)
				System.out.println("This name is empty...");
			else if (splt.length == 1) {
				lookingForName = false;
				ln = splt[0];
			} else if (splt.length == 2)
			{
				fn = splt[0];
				ln= splt[1];
				lookingForName = false;
			}
			else
			{
				
				fn = splt[0];
				ln=splt[1];
				for(int i=2; i<splt.length;++i)
					ln+=" "+splt[i];
				
				lookingForName = false;
				System.out.println("Defaulting to... \n<First Name>: " + fn +"\n<Last Name>: "+ln);
			}
			
			
			inputs.add(fn);
			inputs.add(ln);
			
		}
		//END OF NAME
		
		
		
		
		
		
		boolean gettingCC=true;
		String cc_id="";
		while (gettingCC)
		{
			cc_id="";
		   try{
			   System.out.print("Enter the Credit-Card Number: ");
		    	cc_id=sc.nextLine();
		    	//cc_id.replaceAll("[^A-Za-z0-9]", "");
		    	
		    	if(cc_id.equals(""))
		    		throw new BadCCNumberException("BAD CC NUMBER!!! ...");
		    	Statement select = connection.createStatement();
		    	ResultSet result = select.executeQuery("SELECT COUNT(*) FROM creditcards WHERE id= '"+ cc_id +"';");
		    	
		    	result.next();
		    	System.out.println("The number of cards found: "+result.getInt(1));
		    	if(result.getInt(1)==0)
		    		throw new BadCCNumberException("BAD CC NUMBER!!! ...");
		    	else
		    	{
		    		gettingCC=false;
		    	}
		   }
		   catch(BadCCNumberException e)
		   {
			  System.out.println("The Credit Card Entered was either not in the DB or not a real CC Number");
			  cc_id="";
		   }
		   catch (SQLException e) {
			   System.out.println("The Credit Card Entered was either not in the DB or not a real CC Number");
			   cc_id="";
		   }
		}
		inputs.add(cc_id);
        cols.add("cc_id");
		
        boolean needAddress = true;
        while(needAddress)
        {
		    System.out.print("Enter address <Not Optional>: ");
			String address=sc.nextLine();
			if(address!=null && !address.equals("") && address.length()>0 && address.length()<=200)
			{
				inputs.add(address);
				cols.add("address");
				needAddress=false;
			}
			else
			{
				System.out.println("Invalid Address! Re-Enter it...");
			}
			
        }
        
        boolean needEmail = true;
        while(needEmail)
        {
		    System.out.print("Enter Customer Email <Not Optional>: ");
			String email=sc.nextLine();
			if(email!=null && !email.equals("") && email.length()>0 && email.length()<=50)
			{
				inputs.add(email);
				cols.add("email");
				needEmail=false;
			}
			else
			{
				System.out.println("Invalid Email! Re-Enter it...");
			}
			
        }
        boolean needPassword = true;
        while(needPassword)
        {
		    System.out.print("Enter Customer Password <Not Optional>: ");
			String password=sc.nextLine();
			if(password!=null && !password.equals("") && password.length()>0 && password.length()<=50)
			{
				inputs.add(password);
				cols.add("password");
				needPassword=false;
			}
			else
			{
				System.out.println("Invalid password! Re-Enter it...");
			}
			
        }
		
		
		insert(connection, "customers", cols.toArray(new String[cols.size()]), inputs.toArray(new Object[cols.size()]));
		
	}
	public static void insertStar(Connection connection) throws SQLException {
		/*
		 * first_name VARCHAR(50) DEFAULT '' NOT NULL, last_name VARCHAR(50)
		 * DEFAULT '' NOT NULL, dob DATE, photo_url VARCHAR(200)
		 */
		boolean lookingForName = true;
		Scanner sc = new Scanner(System.in);
		
		ArrayList<String> cols=new ArrayList<String>();
		
		cols.add("first_name");
		cols.add("last_name");
		
		
		ArrayList<Object> inputs=new ArrayList<Object>();
		
		
		
		while (lookingForName) {
			System.out.println("Enter the Star's Name <First> <Last>: ");
			
			String inp = sc.nextLine();
			String[] splt = inp.split(" ");

			String fn = "";
			String ln = "";
			
			
			if (splt.length == 0)
				System.out.println("This name is empty...");
			else if (splt.length == 1) {
				lookingForName = false;
				ln = splt[0];
			} else if (splt.length == 2)
			{
				fn = splt[0];
				ln= splt[1];
				lookingForName = false;
			}
			else
			{
				
				fn = splt[0];
				ln=splt[1];
				for(int i=2; i<splt.length;++i)
					ln+=" "+splt[i];
				
				lookingForName = false;
				System.out.println("Defaulting to... \n<First Name>: " + fn +"\n<Last Name>: "+ln);
			}
			
			
			inputs.add(fn);
			inputs.add(ln);
			
		}
		//END OF NAME
		
		
		
		
		
		System.out.print("Enter Date of Birth (DD/MM/YYYY)<Optional. Press Enter to skip>: ");
		
		String date="";
		java.sql.Date sqlDate = null;
	    java.util.Date theDate = null;
	    try {
	        theDate = new SimpleDateFormat("ddMMyyyy").parse(sc.nextLine().replaceAll("/", ""));
	        sqlDate= new java.sql.Date(theDate.getTime());
	        date=sqlDate.toString();
	        inputs.add(date);
	        cols.add("dob");
			
	        
	    } catch (ParseException e) {
	        System.out.println("Improper Format or Empty... Skipping");
	    }
	    System.out.print("Enter photo url <Optional. Press Enter to skip>: ");
		String photoUrl=sc.nextLine();
		if(photoUrl!=null && photoUrl!="" && photoUrl.length()>0)
		{
			inputs.add(photoUrl);
			cols.add("photo_url");
		}
		else
		{
			System.out.println("Null or Empty URL... Skipping");
		}
		
		insert(connection, "stars", cols.toArray(new String[cols.size()]), inputs.toArray(new Object[cols.size()]));
	}
	
	private static void deleteCustomer(Connection connection) throws SQLException {
		Scanner sc=new Scanner(System.in);
		boolean gettingCC=true;
		String cc_id="";
		while (gettingCC)
		{
			cc_id="";
			Statement select = null;
			ResultSet results = null;
		   try{
			   System.out.print("Enter the Credit-Card Number of Customer(s) to remove: ");
		    	
				cc_id=sc.nextLine();
		    	//cc_id.replaceAll("[^A-Za-z0-9]", "");
		    	
		    	if(cc_id.equals(""))
		    		throw new BadCCNumberException("BAD CC NUMBER!!! ...");
		    	select = connection.createStatement();
		    	result = select.executeQuery("SELECT COUNT(*) FROM creditcards WHERE id="+ cc_id +";");
		    	
		    	result.next();
		    	System.out.println("The number of cards found: "+result.getInt(1));
		    	if(result.getInt(1)==0)
		    		throw new BadCCNumberException("BAD CC NUMBER!!! ...");
		    	else
		    	{
		    		result=select.executeQuery("SELECT id,first_name,last_name FROM creditcards WHERE id="+ cc_id +";");
		    		System.out.println("Customers Removed:");
		    		while (result.next()) {
						System.out.println("Id = " + result.getInt(1));
						System.out.println("Name = " + result.getString(2) +" "+ result.getString(3));
						
						System.out.println();

					}
		    		gettingCC=false;
		    	}
		   }
		   catch(BadCCNumberException e)
		   {
			  System.out.println("The Credit Card Entered was either not in the DB or not a real CC Number");
			  cc_id="";
		   }
		   catch (SQLException e) {
			   System.out.println("The Credit Card Entered was either not in the DB or not a real CC Number");
			   cc_id="";
		   }
		   finally{
		   if(select != null)
			   select.close();
		   if(result != null)
			   result.close();
		   }
		}
		
		delete(connection, "customers", new String[]{"cc_id="+"'"+ cc_id+"'"});
	}
	public static void executeSQLCommand(Connection connection, String SQLCommand) throws SQLException {
		String[] command = SQLCommand.split(" ", 2);
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		int rowsEffected;
		
		try {
			stmt = connection.createStatement();
			if(command.length >= 1)
			{
				if(command[0].toLowerCase().equals("select"))
				{
					rs = stmt.executeQuery(SQLCommand);
					rsmd = rs.getMetaData();
					int numOfCols = rsmd.getColumnCount();
					while (rs.next()) {
				        for (int i = 1; i <= numOfCols; i++) {
				            if (i > 1) System.out.print(",  \t");
				            String columnValue = rs.getString(i);
				            System.out.print(rsmd.getColumnName(i) + " " + columnValue);
				        }
				        System.out.println("");
				    }
				}
				else
				{
					rowsEffected = stmt.executeUpdate(SQLCommand);
					System.out.println("Rows effected: " + rowsEffected);
				}
			}
		} catch (SQLException e) {
			System.out.println("bad mySQL syntax");
		}
		finally{
			if(stmt != null)
				stmt.close();
			if(rs != null)
				rs.close();
		}
		
	}
	public static String joinCols(String[] inp) {
		StringBuilder sb = new StringBuilder();
		for (String n : inp) {
			if (sb.length() > 0)
				sb.append(',');
			sb.append("").append(n).append("");
		}
		return sb.toString();
	}

	public static String joinObject(Object[] inp) {
		StringBuilder sb = new StringBuilder();
		for (Object n : inp) {
			if (sb.length() > 0)
				sb.append(',');
			if (n instanceof String)
				sb.append("'").append(n).append("'");
			else
				sb.append(n);
		}
		return sb.toString();
	}
	public static int insert(Connection connection, String table_name, String[] cols, Object[] entries)
			throws SQLException {
		try {
			String colName = " ";
			String entry = " ";
			if (cols != null && cols.length != 0) {
				colName = " (" + joinCols(cols) + ")";

			}
			if (entries != null && entries.length != 0) {
				entry = "(" + joinObject(entries) + ")";

			}
			System.out.println(colName);
			System.out.println(entry);
			System.out.println("INSERT INTO " + table_name + colName + " VALUES " + entry + ";");
			Statement insert = connection.createStatement();
			return insert.executeUpdate("INSERT INTO " + table_name + colName + " VALUES " + entry + ";");

		} catch (SQLException e) {
			System.out.println("Improper Entry: " + e);
			return -1;
		}

	}
	public static int delete(Connection connection, String table_name, String[] args)
			throws SQLException {
		try {
			String theArgs = " 1=1 ";
			
			
			if (args != null && args.length != 0) {
				theArgs = args[0];
				for(int i=1;args.length>i;++i)				
					theArgs+= " AND "+args[i];
				

			}
			
			
			System.out.println("DELETE FROM "+table_name+" WHERE "+theArgs+";");
			Statement delete = connection.createStatement();
			return delete.executeUpdate("DELETE FROM "+table_name+" WHERE "+theArgs+";");

		} catch (SQLException e) {
			System.out.println("Improper Entry: " + e);
			return -1;
		}

	}
	public static ArrayList getTablesMetadata() throws SQLException {
		String table[] = { "TABLE" };
		ResultSet rs = null;
		ArrayList<String> tables = null;
		// receive the Type of the object in a String array.
		rs = metadata.getTables(null, null, null, table);
		tables = new ArrayList<String>();
		while (rs.next()) {
			tables.add(rs.getString("TABLE_NAME"));
		}
		return tables;
	}

	/**
	 * Prints in the console the columns metadata, based in the Arraylist of
	 * tables passed as parameter.
	 * 
	 * @param tables
	 * @throws SQLException
	 */
	public static void getColumnsMetadata(ArrayList<String> tables)
			throws SQLException {
		ResultSet rs = null;
		// Print the columns properties of the actual table
		for (String actualTable : tables) {
			rs = metadata.getColumns(null, null, actualTable, null);
			System.out.println(actualTable.toUpperCase());
			while (rs.next()) {
				System.out.println(rs.getString("COLUMN_NAME") + " "
						+ rs.getString("TYPE_NAME") + " "
						+ rs.getString("COLUMN_SIZE"));
			}
			System.out.println("\n");
		}

	}
	public static void main(String[] arg) throws Exception {
		String dbName = "moviedb";
		if (arg.length == 0) {
			System.out.println("No Command Arguments Given... Defaulting to moviedb");
		} else {
			dbName = arg[0];
		}
		// Incorporate mySQL driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		Connection connection = null;

		boolean loggedIn = false;
		byte tries = -0;
		final byte MAX_TRIES = 3;
		Scanner reader = new Scanner(System.in);
		while (true) {
			// This will try to login
			loggedIn = false;
			System.out.println("=== Fabflix Database Editor ===");
			while (!loggedIn) {

				if (++tries > MAX_TRIES) {
					System.out.println("Too many login attempts. Locked out for 10 seconds.");
					Thread.sleep(1000 * 10);
					tries = 0;

				}

				
				try {
					Console cnsl = null;
					String user = null;
					String pass = null;
					try {
						// creates a console object
						cnsl = System.console();

						// if console is not null
						if (cnsl != null) {

							// read line from the user input
							user = cnsl.readLine("Name: ");

							// read password into the char array
							pass = new String(cnsl.readPassword("Enter Password: "));

						} else {
							System.out.println("NO CONSOLE FOUND DEFAULTING TO SCANNER...");
							System.out.println("User Name: ");

							user = reader.next();
							reader.nextLine();
							System.out.println("Password: ");

							pass = reader.nextLine();
						}
					} catch (Exception ex) {

						System.out.println("ERROR");
					}

					// Connect to the test database
					connection = DriverManager.getConnection("jdbc:mysql:///" + dbName, user, pass);
					loggedIn = true;

				} catch (SQLException e) {
					System.out.println("Invalid Login: " + e.getMessage());
					System.out.println("Attempts Left: " + (MAX_TRIES - tries) + "\n");

				}
			}
			// This is the point where a successful login occurred.
			System.out.println("Login Successfull...");
			int input = -1;
			while (loggedIn) {

				System.out.println("\n=== Menu ===");
				System.out.println("[1] Find movies starred in");
				System.out.println("[2] Insert a new star into the database");
				System.out.println("[3] Insert a customer into the database");
				System.out.println("[4] Delete a customer from the database.");
				System.out.println("[5] Provide the metadata of the database.");
				System.out.println("[6] Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command.");
				System.out.println("[7] Log Out.");
				System.out.println("[8] Exit Program.");
				System.out.println("Pick a Number [1 to 8]: ");
				input = reader.nextInt();
				reader.nextLine();
				switch (input) {
				case 1:
					System.out.println("Enter Name or ID of Star: ");

					String inp = reader.nextLine();

					System.out.println(getMovies(inp, connection).toString());
					break;
				case 2:
					/*
					insert(connection, "customers",
							new String[] { "first_name", "last_name", "cc_id", "address", "email", "password" },
							new Object[] { "Ben", "Stined", "872002", "123 Alpine way. Montebello, CA, 91456",
									"Bstine@mail.com", "password2" });
									*/
					insertStar(connection);
					break;
				case 3:
					insertCustomer(connection);
					break;
				case 4:
					deleteCustomer(connection);
					break;
				case 5:
					 metadata = connection.getMetaData();
					getColumnsMetadata(getTablesMetadata());
					break;
				case 6:
					System.out.println("Enter command all on one line: \n");
					String SQLCommand = reader.nextLine();
					executeSQLCommand(connection, SQLCommand);
					
					break;
				case 7:
					System.out.print("Logging off...");
					loggedIn = false;
					System.out.print("Done.\n");
					break;
				case 8:
					System.out.println("Shutting Down...");
					System.exit(0);
					break;
				default:
					System.out.println("\nPick a valid number...");
					break;
				}
			}
			//
			// Create an execute an SQL statement to select all of table"Stars"
			// records
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("Select * from stars");

			// Get metatdata from stars; print # of attributes in table
			System.out.println("The results of the query");
			ResultSetMetaData metadata = result.getMetaData();
			System.out.println("There are " + metadata.getColumnCount() + "columns");

			// Print type of each attribute
			for (int i = 1; i <= metadata.getColumnCount(); i++)
				System.out.println("Type of column " + i + " is " + metadata.getColumnTypeName(i));

			// print table's contents, field by field
			while (result.next()) {
				System.out.println("Id = " + result.getInt(1));
				System.out.println("Name = " + result.getString(2) + result.getString(3));
				System.out.println("DOB = " + result.getString(4));
				System.out.println("photoURL = " + result.getString(5));
				System.out.println();

			}
		}
	}



	
}
