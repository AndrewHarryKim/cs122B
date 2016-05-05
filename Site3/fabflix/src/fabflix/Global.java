package fabflix;




public class Global {
	public static final String DB_USER= "root"; 
	public static final String DB_PASS="pops711";
	
	public static final String homeServletPath="/Home";
	public static final String loginServletPath="/Login";
	public static final String loginJSPPath="/WEB-INF/login.jsp";
	public static final String logoutServletPath="Logout";
	
	public static final String browseGenreServlet="Browse?by=genre";
	public static final String browseTitleServlet="Browse?by=title";
	public static final String browseGenreJSP="/WEB-INF/browse.jsp?by=genre";
	public static final String browseTitleJSP="/WEB-INF/browse.jsp?by=title";
	public static final String movieListServletPath="MovieList";
	public static final String searchServletPath="Search";
	

	public static final String dashboardServletPath="/Dashboard";
	public static final String dashboardLoginServletPath="/_dashboard";
	public static final String insertStarServletPath="/InsertStar";
	public static final String metaDataServletPath="/DashboardMetaData";
	public static final String addMovieServletPath="/AddMovie";
	
	public static final String cartServletPath="Cart";
	public static final String checkoutServletPath="Checkout";
	public static final String checkCCServletPath="CheckCC";

	public static final String dburl = "jdbc:mysql://localhost:3306/moviedb";
    public static final String SITE_KEY ="6LfGUR4TAAAAAMFiGqSNK9vR1dZZK0_AGkpZyih-";
    public static final String SECRET_KEY ="6LfGUR4TAAAAAMB0gE1czaqYIp9wkeNos3WyVKDt";
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    

}
