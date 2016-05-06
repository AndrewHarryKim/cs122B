

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
/*
 * movies, stars, and genres
 * stars_in_movies and genres_in_movies
 */
public class SaxParserActor extends DefaultHandler{

	@SuppressWarnings("rawtypes")
	List myActors;
	List myBadActors;
	List catInActors;
	Set genres;
	
	private String tempVal;
	
	//to maintain context
	private Star tempStar;
	private Movie tempMovie;
	
	
	public SaxParserActor(){
		myActors = new ArrayList();
		myBadActors = new ArrayList();
		catInActors = new ArrayList();
		genres=new HashSet();
	}
	
	public void runStars() {
		parseStarDocument();
		printStarData();
	}
	

	private void parseStarDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
//			sp.parse("employees.xml", this);
			sp.parse("actors63.xml", this);
			
			
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
	private void printStarData(){
		
		
		@SuppressWarnings("rawtypes")
//		Iterator it = myMovies.iterator();
		Iterator it = myActors.iterator();
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
		System.out.println("\nNo of Actors '" + myActors.size() + "'.");
		System.out.println("\nBadly Formatted A Found: '"+myBadActors.size()+"'\n");
		System.out.println("All the Genres Found:");
		System.out.println(genres);
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("actor")) {
			//create a new instance of employee
			tempStar = new Star();
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
		if(qName.equalsIgnoreCase("actor")) {
			//add it to the list
			if((tempStar.getFirst_name()!=null&&!tempStar.getFirst_name().equals("")) || 
					(tempStar.getLast_name()!=null&&!tempStar.getLast_name().equals("")))
				myActors.add(tempStar);
//			if(tempStar.getYear()==-404)
//				myBadActors.add(tempStar);
			
		}
		else if (qName.equalsIgnoreCase("familyname")) {
			try
			{
				tempStar.setLast_name(tempVal);
			}
			catch(NumberFormatException e)
			{
				tempStar.setId((tempVal.hashCode()));
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
		else if (qName.equalsIgnoreCase("firstname")) {
			try
			{
				tempStar.setFirst_name(tempVal);
			}
			catch(NumberFormatException e)
			{
				tempStar.setId((tempVal.hashCode()));
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
//		else if (qName.equalsIgnoreCase("t")) 
//		{
//			tempStar.setTitle(tempVal);
////			System.out.println(tempVal);
//		}
//		else if (qName.equalsIgnoreCase("year")) 
//		{
//			try
//			{
//				tempStar.setYear(Integer.parseInt(tempVal));
//			}
//			catch(NumberFormatException e)
//			{
//				//We will set the year to -404 in order to save the data (It can be fixed later by employees)
//				tempStar.setYear(-404);
//			}
//		}
//		else if (qName.equalsIgnoreCase("dirn")) 
//		{
//			tempStar.setDirector(tempVal);
//		}
		else if(qName.equalsIgnoreCase("cats")) {
			
			tempMovie.setCats(catInActors);
			catInActors=new ArrayList<>();
		}
		else if(qName.equalsIgnoreCase("cat")) {
			genres.add(tempVal);
			catInActors.add(tempVal);
		}
		
	}
	
	public static void main(String[] args){
		SaxParserActor spe = new SaxParserActor();
		spe.runStars();
		
	}
	
}



