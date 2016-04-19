<!-- This File holds the Login Details and other Globals -->
<%
	// This is the Login for the DB
	String DB_USER= "root"; 
	String DB_PASS="!FooBar1";
	// This is the location of the Login Portal
	String loginPath="index.jsp";
	// This is the location of the Login-Authentication
	String authLoginPath="login.jsp";	
	// This is the location of the Logout script
	String logoutPath="logout.jsp";	
	//This is the location of the HomePage
	String homePath="main.jsp";
	// This is the location of the Search Page
	String searchPath="";	
	// This is the location of the General Browsing Page and the sub-pages
	String browsePath="browse.jsp";
	String browseGenre="browse.jsp?by=genre";
	String browseTitle="browse.jsp?by=title";
	// This is the location of the movie-list after you find one.
	String movieListPath="movie-list.jsp";
%>