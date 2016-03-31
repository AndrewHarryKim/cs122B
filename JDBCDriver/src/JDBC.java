
// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson

import java.sql.*; // Enable SQL processing
import java.util.Arrays;
import java.util.Scanner;

public class JDBC {
	public static String getMovies(String inp, Connection connection) throws SQLException {
		Statement select = connection.createStatement();
		String more="";
		if(inp.matches("^-?\\d+$"))
		{
			//System.out.println("......"+inp);
			more="AND s.id="+inp + " ";
		}
		else
		{
			String[] splt=inp.split(" ");
			
			if(splt.length==0)
				more="";
			else if(splt.length==1)
				more="AND s.first_name="+splt[0] + " ";
			else if(splt.length==2)
				more="AND s.first_name="+splt[0] + " AND s.last_name="+splt[1] + " ";
			else{
//				for(int i=1;i<splt.length-1;++i)
//				{
//					more+="AND (s.first_name="+Arrays.toString(Arrays.copyOfRange(splt,0,i)) + 
//							" AND s.last_name="+Arrays.toString(Arrays.copyOfRange(splt,i,splt.length))  + ") OR";
//				}
				System.out.println("Something went wrong. Please enter the First Name and Last Name Sepeartely: ");
				Scanner a=new Scanner(System.in);
				System.out.println("First Name: ");
				String first= a.nextLine();
				
				System.out.println("Last Name: ");
				String last= a.nextLine();
				
				more="AND s.first_name='"+first + "' AND s.last_name='"+last + "' ";
				System.out.println(more);
			}
			
		}
		ResultSet result = select.executeQuery(
				"SELECT sm.movie_id, m.title, m.year, m.director, m.banner_url, m.trailer_url "
						+ " FROM movies m, stars s, "+"stars_in_movies sm "
						+"WHERE s.id=sm.star_id AND m.id=sm.movie_id " + more
						+"ORDER BY sm.movie_id;");
		StringBuilder output = new StringBuilder();
		
		while (result.next()) {
			output.append("Movie Id: " + result.getInt(1)+"\n");
			output.append("Title: " + result.getString(2) +"\n");
			output.append("Year: " + result.getString(3)+"\n");
			output.append("Director: " + result.getString(4)+"\n");
			output.append("Banner URL: " + result.getString(5)+"\n");
			output.append("Trailer URL: " + result.getString(5)+"\n");
			output.append("=======================\n");

		}
		
		return "Movies appeared in\n=====================\n"+output.toString();
	}

	public static void main(String[] arg) throws Exception {

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

				reader.reset();
				try {

					System.out.println("User Name: ");

					String user = reader.next();
					reader.nextLine();
					System.out.println("Password: ");
					String pass = reader.nextLine();
					// Connect to the test database
					connection = DriverManager.getConnection("jdbc:mysql:///moviedb", user, pass);
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

					break;
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;
				case 6:

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
