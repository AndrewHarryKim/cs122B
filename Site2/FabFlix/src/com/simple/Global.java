package com.simple;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
// 


public class Global
{
	public static String DB_USER= "root"; 
	public static String DB_PASS="pops711";
	// This is the location of the Login Portal
	public static String loginPath="/Index";
	// This is the location of the Login-Authentication
	public static String authLoginPath="Login";	
	// This is the location of the Logout script
	public static String logoutPath="Logout";	
	//This is the location of the HomePage
	public static String homePath="Main";
	// This is the location of the Search Page
	public static String searchPath="";	
	// This is the location of the General Browsing Page and the sub-pages
	public static String browsePath="browse.jsp";
	public static String browseGenre="Browse?by=genre";
	public static String browseTitle="Browse?by=title";
	// This is the location of the movie-list after you find one.
	public static String movieListPath="MovieList";
	// This is the Cart location.
	public static String cartPath="Cart";
	// This is the CheckOut location.
	public static String checkoutPath="Checkout";
}