

import java.io.IOException;
import java.util.ArrayList;
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

public class SaxParser extends DefaultHandler{

	@SuppressWarnings("rawtypes")
	List myMovies;
	List myBadMovies;
	List catInMovies;
	Set genres;
	
	private String tempVal;
	
	//to maintain context
	private Movie tempMovie;
	
	
	public SaxParser(){
		myMovies = new ArrayList();
		myBadMovies = new ArrayList();
		catInMovies = new ArrayList();
		genres=new HashSet();
	}
	
	public void runMovies() {
		parseMovieDocument();
		printMovieData();
	}

	private void parseMovieDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
//			sp.parse("employees.xml", this);
			sp.parse("mains243.xml", this);
			
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printMovieData(){
		
		System.out.println("No of Movies '" + myMovies.size() + "'.");
		System.out.println("\nBadly Formatted Movies Found: '"+myBadMovies.size()+"'\n");
		@SuppressWarnings("rawtypes")
//		Iterator it = myMovies.iterator();
		Iterator it = myBadMovies.iterator();
		while(it.hasNext()) {
			try
			{
				
				System.out.println(it.next().toString());
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
//				System.out.println("Nothing Found");
			}
		}
		
		System.out.println("All the Genres Found:");
		System.out.println(genres);
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("film")) {
			//create a new instance of employee
			tempMovie = new Movie();
//			tempMovie.setType(attributes.getValue("type"));
		}
		
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		/*
		id:integer (primary key)
		title:varchar(100) 
		year:integer 
		director:varchar(100) 
		banner_url:varchar(200) 
		trailer_url:varchar(200) 
		 */
		if(qName.equalsIgnoreCase("films")) {
			//add it to the list
			myMovies.add(tempMovie);
			if(tempMovie.getYear()==-404)
				myBadMovies.add(tempMovie);
			
		}
		else if (qName.equalsIgnoreCase("fid")) {
			try
			{
				tempMovie.setId(Integer.parseInt(tempVal));
			}
			catch(NumberFormatException e)
			{
				tempMovie.setId((tempVal.hashCode()));
//
//				StringBuilder sb = new StringBuilder();
//				for (char c : tempVal.toCharArray())
//				    sb.append((int)c);
//				if(sb.toString().length()>9)
//					tempMovie.setId(Integer.parseInt(sb.toString().substring(0, 9)));
//				else
//					tempMovie.setId(Integer.parseInt(sb.toString()));
			}
		}
		else if (qName.equalsIgnoreCase("t")) 
		{
			tempMovie.setTitle(tempVal);
//			System.out.println(tempVal);
		}
		else if (qName.equalsIgnoreCase("year")) 
		{
			try
			{
				tempMovie.setYear(Integer.parseInt(tempVal));
			}
			catch(NumberFormatException e)
			{
				//We will set the year to -404 in order to save the data (It can be fixed later by employees)
				tempMovie.setYear(-404);
			}
		}
		else if (qName.equalsIgnoreCase("dirn")) 
		{
			tempMovie.setDirector(tempVal);
		}
		else if(qName.equalsIgnoreCase("cats")) {
			tempMovie.setCats(catInMovies);
			catInMovies=new ArrayList<>();
		}
		else if(qName.equalsIgnoreCase("cat")) {
			genres.add(tempVal);
			catInMovies.add(tempVal);
		}
		
	}
	
	public static void main(String[] args){
		SaxParser spe = new SaxParser();
		spe.runMovies();
	}
	
}


