
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import com.mysql.jdbc.Statement;

/*
 * movies, stars, and genres
 * stars_in_movies and genres_in_movies
 */
public class SaxParserMain extends DefaultHandler {

	@SuppressWarnings("rawtypes")
	List myMovies;
	List myBadMovies;
	List catInMovies;
	Set genres;

	List myActors;
	List myBadActors;

	HashMap movieActors;

	private String tempVal;

	// to maintain context
	private Movie tempMovie;
	// to maintain context
	private Star tempStar;

	String dbName = "moviedb";
	String dbUser = "root";
	String dbPW = "pops711";

	// Movie - > Stars
	String movie;
	List actorsIn;

	public SaxParserMain() {
		myMovies = new ArrayList();
		myBadMovies = new ArrayList();
		catInMovies = new ArrayList();
		genres = new HashSet();

		myActors = new ArrayList<>();
		myBadActors = new ArrayList();

		movieActors = new HashMap<>();

		movie = "";
		actorsIn = new ArrayList();

	}

	public void runMovies() {
		parseMovieDocument();
		printMovieData();

		parseStarDocument();
		// printStarData();

		parseMovieStarDocument();
		// printMovieStarData();

		try {
			insertIntoDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertIntoDB() throws SQLException {

		System.out.println("Inserting Batch");

		// Incorporate mySQL driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///moviedb", dbUser, dbPW);
		} catch (SQLException e) {
			System.out.println("Connection Invalid");
		}
		connection.setAutoCommit(false);
		java.sql.Statement s = connection.createStatement();
		String[] tblName = { "genres", "stars" };

		// Insert Genres
		Iterator names = genres.iterator();
		while (names.hasNext()) {
			String[] specialS = { "name" };

			String name = names.next().toString();
			String tmp = "INSERT INTO " + tblName[0] + " (" + specialS[0] + ")" + " SELECT '" + name
					+ "' WHERE NOT EXISTS(SELECT * FROM " + tblName[0] + " WHERE " + specialS[0] + "='" + name + "');";

			s.addBatch(tmp);

		}
		try {
			s.executeBatch();
			connection.commit();
			s.close();
			System.out.println("DONE WITH BATCH 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Insert Stars
		java.sql.Statement s2 = connection.createStatement();
		Iterator stars = myActors.iterator();
		while (stars.hasNext()) {
			Star actor = (Star) stars.next();
			String[] specialS = { "first_name", "last_name" };
			// actor.setFirst_name(actor.getFirst_name().replace("\\",
			// "").replace("\'", "\'\'"));
			// actor.setLast_name(actor.getLast_name().replace("\\",
			// "").replace("\'", "\'\'"));
			String[] vals = { actor.getFirst_name(), actor.getLast_name() };

			String tmp = "";

			if (actor.getFirst_name() == null || actor.getFirst_name() == null || actor.getFirst_name().equals("")
					|| actor.getFirst_name().equals("")) {

				actor.setFirst_name("");
			}
			tmp = "INSERT INTO " + tblName[1] + " (" + specialS[0] + "," + specialS[1] + ")" + " SELECT '" + vals[0]
					+ "','" + vals[1] + "' WHERE NOT EXISTS(SELECT * FROM " + tblName[1] + " WHERE " + specialS[0]
					+ "='" + vals[0] + "' AND " + specialS[1] + "='" + vals[1] + "');";

			s2.addBatch(tmp);

		}
		try {
			s2.executeBatch();
			connection.commit();
			s2.close();
			System.out.println("DONE WITH BATCH 2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Insert movies
		java.sql.Statement s3 = connection.createStatement();
		Iterator stars = myActors.iterator();
		while (stars.hasNext()) {
			Star actor = (Star) stars.next();
			String[] specialS = { "first_name", "last_name" };
			// actor.setFirst_name(actor.getFirst_name().replace("\\",
			// "").replace("\'", "\'\'"));
			// actor.setLast_name(actor.getLast_name().replace("\\",
			// "").replace("\'", "\'\'"));
			String[] vals = { actor.getFirst_name(), actor.getLast_name() };

			String tmp = "";

			if (actor.getFirst_name() == null || actor.getFirst_name() == null || actor.getFirst_name().equals("")
					|| actor.getFirst_name().equals("")) {

				actor.setFirst_name("");
			}
			tmp = "INSERT INTO " + tblName[1] + " (" + specialS[0] + "," + specialS[1] + ")" + " SELECT '" + vals[0]
					+ "','" + vals[1] + "' WHERE NOT EXISTS(SELECT * FROM " + tblName[1] + " WHERE " + specialS[0]
					+ "='" + vals[0] + "' AND " + specialS[1] + "='" + vals[1] + "');";

			s3.addBatch(tmp);

		}
		try {
			s3.executeBatch();
			connection.commit();
			s3.close();
			System.out.println("DONE WITH BATCH 3");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void printMovieStarData() {

		System.out.println(movieActors);
		// for(Object actors: movieActors.values())
		// {
		// System.out.println(actors);
		// }
	}

	private void parseMovieStarDocument() {
		// TODO Auto-generated method stub
		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			// sp.parse("employees.xml", this);
			sp.parse("casts124.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private void parseStarDocument() {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			// sp.parse("employees.xml", this);
			sp.parse("actors63.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private void parseMovieDocument() {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			// sp.parse("employees.xml", this);
			sp.parse("mains243.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private void printStarData() {

		@SuppressWarnings("rawtypes")
		// Iterator it = myMovies.iterator();
		Iterator it = myActors.iterator();
		while (it.hasNext()) {
			try {

				System.out.println(it.next().toString());
			} catch (NullPointerException e) {
				e.printStackTrace();
				// System.out.println("Nothing Found");
			}
		}
		System.out.println("\nNo of Actors '" + myActors.size() + "'.");
		System.out.println("\nBadly Formatted A Found: '" + myBadActors.size() + "'\n");

	}

	/**
	 * Iterate through the list and print the contents
	 */
	private void printMovieData() {

		System.out.println("No of Movies '" + myMovies.size() + "'.");
		System.out.println("\nBadly Formatted Movies Found: '" + myBadMovies.size() + "'\n");
		@SuppressWarnings("rawtypes")
		// Iterator it = myMovies.iterator();
		Iterator it = myBadMovies.iterator();
		while (it.hasNext()) {
			try {

				System.out.println(it.next().toString());
			} catch (NullPointerException e) {
				e.printStackTrace();
				// System.out.println("Nothing Found");
			}
		}

		System.out.println("All the Genres Found:");
		System.out.println(genres);
	}

	// Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("film")) {
			// create a new instance of employee
			tempMovie = new Movie();
			// tempMovie.setType(attributes.getValue("type"));
		} else if (qName.equalsIgnoreCase("actor")) {
			// create a new instance of employee
			tempStar = new Star();
			// tempMovie.setType(attributes.getValue("type"));
		}

	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		/*
		 * id:integer (primary key) title:varchar(100) year:integer
		 * director:varchar(100) banner_url:varchar(200)
		 * trailer_url:varchar(200)
		 */
		tempVal = tempVal.replaceAll("\\*", " ").replace("\\", "").replace("\'", "\'\'");

		// tempVal=tempVal.replaceAll("'''", "''");
		if (qName.equalsIgnoreCase("film")) {
			// add it to the list
			myMovies.add(tempMovie);
			if (tempMovie.getYear() == -404)
				myBadMovies.add(tempMovie);

		} else if (qName.equalsIgnoreCase("fid")) {
			try {
				tempMovie.setId(Integer.parseInt(tempVal));
			} catch (NumberFormatException e) {
				// tempMovie.setId((tempVal.hashCode()));
				tempMovie.setId(-1);

			}
		} else if (qName.equalsIgnoreCase("t")) {
			tempMovie.setTitle(tempVal);
			// System.out.println(tempVal);
		} else if (qName.equalsIgnoreCase("year")) {
			try {
				tempMovie.setYear(Integer.parseInt(tempVal));
			} catch (NumberFormatException e) {
				// We will set the year to -404 in order to save the data (It
				// can be fixed later by employees)
				tempMovie.setYear(-404);
			}
		}

		else if (qName.equalsIgnoreCase("cat")) {
			genres.add(tempVal);
			catInMovies.add(tempVal);
		} else if (qName.equalsIgnoreCase("dirn")) {
			tempMovie.setDirector(tempVal);
		} else if (qName.equalsIgnoreCase("cats")) {
			tempMovie.setCats(catInMovies);
			catInMovies = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("t")) {
			movie = tempVal;
			// System.out.println(movie);

		}
		/// THE ACTOR
		/*
		 * id:integer (primary key) title:varchar(100) year:integer
		 * director:varchar(100) banner_url:varchar(200)
		 * trailer_url:varchar(200)
		 */
		///
		if (qName.equalsIgnoreCase("actor")) {
			// add it to the list
			if ((tempStar.getFirst_name() != null && !tempStar.getFirst_name().equals(""))
					|| (tempStar.getLast_name() != null && !tempStar.getLast_name().equals("")))
				myActors.add(tempStar);
			// if(tempStar.getYear()==-404)
			// myBadActors.add(tempStar);

		} else if (qName.equalsIgnoreCase("familyname")) {
			try {
				tempStar.setLast_name(tempVal);
			} catch (NumberFormatException e) {
				tempStar.setId((tempVal.hashCode()));

			}
		} else if (qName.equalsIgnoreCase("firstname")) {
			try {
				tempStar.setFirst_name(tempVal);
			} catch (NumberFormatException e) {
				tempStar.setId((tempVal.hashCode()));

			}
		}
		// THE Movie actor relations

		//
		else if (qName.equalsIgnoreCase("t")) {
			movie = tempVal;

		} else if (qName.equalsIgnoreCase("a")) {
			actorsIn.add(tempVal);

		} else if (qName.equalsIgnoreCase("filmc")) {
			// System.out.println(actorsIn);
			movieActors.put(movie, actorsIn);
			actorsIn = new ArrayList();

		}

	}

	public static void main(String[] args) {
		SaxParserMain spe = new SaxParserMain();
		spe.runMovies();

	}

}
