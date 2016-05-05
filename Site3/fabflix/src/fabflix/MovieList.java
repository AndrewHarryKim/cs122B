package fabflix;

import fabflix.CartItem;
import fabflix.CartList;
import fabflix.Global;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;


@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int STRING_ARG_TYPE = 0;
	private static final int INT_ARG_TYPE = 1;
	static String dburl = "jdbc:mysql://localhost:3306/moviedb";

    public MovieList() {
        super();
    }

    private String getGenreQueryString(HttpServletRequest request, ArrayList<String> argumentList, ArrayList<Integer> argumentTypes){
    	argumentList.add(request.getParameter("id"));
    	argumentTypes.add(INT_ARG_TYPE);
		return "SELECT sm.movie_id, m.title, m.year, m.director"
				+ " FROM movies m, stars s,stars_in_movies sm, genres_in_movies gm"
				+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id AND m.id=gm.movie_id AND gm.genre_id = ? ";
				
    }
    private String getTitleQueryString(HttpServletRequest request, ArrayList<String> argumentList, ArrayList<Integer> argumentTypes){
    	argumentList.add(request.getParameter("browseletter")+"%");
    	argumentTypes.add(STRING_ARG_TYPE);
    	return "SELECT sm.movie_id, m.title, m.year, m.director"
			+ " FROM movies m, stars s, " + "stars_in_movies sm"
			+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id AND m.title LIKE ? ";
			
    }
    private String getStarNameQueryString(HttpServletRequest request, boolean mainSearch, ArrayList<String> argumentList, 
    							ArrayList<Integer> argumentTypes) {
    	String queryString = "";
    	String names[] = request.getParameter("starname").split(" ");
		if(names.length >= 2)
		{
			if(!mainSearch)
			{
				queryString += " AND";
			}
			queryString += " (s.first_name LIKE ? ";
			argumentList.add("%" + names[0] + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			for(int i = 0; i < names[0].length(); i++)
			{
				queryString += " OR s.first_name LIKE ?";
				argumentList.add("%" + names[0].substring(0,i) + "_" + names[0].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			queryString += " OR s.first_name LIKE ?";
			argumentList.add("%" + names[0].substring(0,names[0].length()-1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			for(int i = 1; i < names[0].length(); i++)
			{
				queryString += " OR s.first_name LIKE ?";
				argumentList.add("%" + names[0].substring(0,i-1) + names[0].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			queryString += " OR s.first_name LIKE ?";
			argumentList.add("%" + names[0].substring(1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			queryString += ")";
			
			queryString += " AND (s.last_name LIKE ? ";
			argumentList.add("%" + names[1] + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			for(int i = 0; i < names[1].length(); i++)
			{
				queryString += " OR s.last_name LIKE ?";
				argumentList.add("%" + names[1].substring(0,i) + "_" + names[1].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			
			queryString += " OR s.last_name LIKE ?";
			argumentList.add("%" + names[1].substring(0,names[1].length()-1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			for(int i = 1; i < names[1].length(); i++)
			{
				queryString += " OR s.last_name LIKE ?";
				argumentList.add("%" + names[1].substring(0,i-1) + names[1].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			
			queryString += " OR s.last_name LIKE ?";
			argumentList.add("%" + names[1].substring(1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			queryString += ")";
			if(mainSearch)
			{
				queryString += " OR";
				}
		}
		else
		{
			if(!mainSearch)
			{
				queryString += " AND";
			}
			queryString += " (s.first_name LIKE ? ";
			argumentList.add("%" + names[0] + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			for(int i = 0; i < names[0].length(); i++)
			{
				queryString += " OR s.first_name LIKE ?";
				argumentList.add("%" + names[0].substring(0,i) + "_" + names[0].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			
			queryString += " OR s.first_name LIKE ?";
			argumentList.add("%" + names[0].substring(0,names[0].length()-1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			for(int i = 1; i < names[0].length(); i++)
			{
				queryString += " OR s.first_name LIKE ?";
				argumentList.add("%" + names[0].substring(0,i-1) + names[0].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			
			queryString += " OR s.first_name LIKE ?";
			argumentList.add("%" + names[0].substring(1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			queryString += " OR s.last_name LIKE ? ";
			argumentList.add("%" + names[0] + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			for(int i = 0; i < names[0].length(); i++)
			{
				queryString += " OR s.last_name LIKE ?";
				argumentList.add("%" + names[0].substring(0,i) + "_" + names[0].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			
			queryString += " OR s.last_name LIKE ?";
			argumentList.add("%" + names[0].substring(0,names[0].length()-1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			for(int i = 1; i < names[0].length(); i++)
			{
				queryString += " OR s.last_name LIKE ?";
				argumentList.add("%" + names[0].substring(0,i-1) + names[0].substring(i) + "%");
				argumentTypes.add(STRING_ARG_TYPE);
			}
			
			queryString += " OR s.last_name LIKE ?";
			argumentList.add("%" + names[0].substring(1) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
			
			queryString += ")";
			if(mainSearch){queryString += " OR";}
		}

		return queryString;
    }   
	private String getTitleSearchQueryString(HttpServletRequest request, Boolean mainSearch, ArrayList<String> argumentList, 
			ArrayList<Integer> argumentTypes) {
		String queryHelper = request.getParameter("title");
		String queryString = "";
		
		if(!mainSearch)
		{
			queryString += " AND";
		}
		
		queryString += " (m.title LIKE ?";
		argumentList.add("%" + queryHelper + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		for(int i = 0; i < queryHelper.length(); i++)
		{
			queryString += " OR m.title LIKE ?";
			argumentList.add("%" + queryHelper.substring(0,i) + "_" + queryHelper.substring(i) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
		}
		
		queryString += " OR m.title LIKE ?";
		argumentList.add("%" + queryHelper.substring(0,queryHelper.length()-1) + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		for(int i = 1; i < queryHelper.length(); i++)
		{
			queryString += " OR m.title LIKE ?";
			argumentList.add("%" + queryHelper.substring(0,i-1) + queryHelper.substring(i) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
		}
		
		queryString += " OR m.title LIKE ?";
		argumentList.add("%" + queryHelper.substring(1) + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		queryString += ")";
		if(mainSearch)
		{
			queryString += " OR";
		}
		
		return queryString;
	}
	private String getDirectorQueryString(HttpServletRequest request, Boolean mainSearch, ArrayList<String> argumentList, 
			ArrayList<Integer> argumentTypes) {
		String queryHelper = request.getParameter("director");
		String queryString = "";
		
		if(!mainSearch)
		{
			queryString += " AND";
		}
		
		queryString += " (m.director LIKE ?";
		argumentList.add("%" + queryHelper + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		for(int i = 0; i < queryHelper.length(); i++)
		{
			queryString += " OR m.director LIKE ?";
			argumentList.add("%" + queryHelper.substring(0,i) + "_" + queryHelper.substring(i) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
		}
		
		queryString += " OR m.director LIKE ?";
		argumentList.add("%" + queryHelper.substring(0,queryHelper.length()-1) + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		for(int i = 1; i < queryHelper.length(); i++)
		{
			queryString += " OR m.director LIKE ?";
			argumentList.add("%" + queryHelper.substring(0,i-1) + queryHelper.substring(i) + "%");
			argumentTypes.add(STRING_ARG_TYPE);
		}
		
		queryString += " OR m.director LIKE ?";
		argumentList.add("%" + queryHelper.substring(1) + "%");
		argumentTypes.add(STRING_ARG_TYPE);
		
		queryString += ")";
		if(mainSearch)
		{
			queryString += " OR";
		}
		return queryString;
	}


	private int getPageSize(HttpServletRequest request) {
		if(request.getParameter("pagesize")!=null)
		{
			if(!request.getParameter("pagesize").equals(""))
			{
				if(Integer.parseInt(request.getParameter("pagesize"))>0 && Integer.parseInt(request.getParameter("pagesize"))<100)
				{
					return Integer.parseInt(request.getParameter("pagesize"));
				}
			}
		}
		return 10;
	}
	private int getPageNum(HttpServletRequest request){
		if(request.getParameter("pagenum")!= null)
		{
			if(!request.getParameter("pagenum").equals(""))
			{
				return Integer.parseInt(request.getParameter("pagenum"));
			}
		}
		return 1;
	}
	private int getOffset(HttpServletRequest request, int pageNum, int pageSize) {
		if(pageNum > 0 && pageNum < 100)
		{
			return pageSize*(pageNum-1);
		}
		return 0;
	}
	private String getOrderBy(HttpServletRequest request) {
		if(request.getParameter("orderby")!=null)
		{
			if(!request.getParameter("orderby").equals(""))
			{
				if(request.getParameter("orderby").equals("title"))
				{
					return "m.title";
				}
				else if(request.getParameter("orderby").equals("year"))
				{
					return "m.year, m.title";
				}
			}
		}
		return "sm.movie_id";
	}
	

    private boolean isMainSearch(HttpServletRequest request){
    	if(request.getParameter("starname") != null){
			if(request.getParameter("starname").equals(request.getParameter("director"))&&
					request.getParameter("starname").equals(request.getParameter("year"))&&
					request.getParameter("starname").equals(request.getParameter("title")))
			{
				return true;
			}
		}
    	return false;
    }  
    private boolean searchParametersNotEmpty(HttpServletRequest request){
    	return (request.getParameter("starname") != null && request.getParameter("starname") != "") ||
		(request.getParameter("year") != null && request.getParameter("year") != "") ||
		(request.getParameter("title") != null && request.getParameter("title") != "")||
		(request.getParameter("director") != null && request.getParameter("director") != "");
    }

	private void fillPreparedStatement(PreparedStatement prepStmt, ArrayList<String> argumentList, ArrayList<Integer> argumentTypes)
    {
		try {
	    	for(int i = 0;  i < argumentList.size(); i++)
			{
				if(argumentTypes.get(i).equals(STRING_ARG_TYPE))
				{
						prepStmt.setString(i+1, argumentList.get(i));
					
				} else
				{
					try{
						prepStmt.setInt(i+1, Integer.parseInt(argumentList.get(i)));
					} catch (NumberFormatException e) {
						prepStmt.setString(i+1, argumentList.get(i));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	private void executeAddItem(HttpServletRequest request, PreparedStatement prepStmt, Connection conn, ResultSet cartQuery) {
		CartList cart = (CartList)request.getSession().getAttribute("cart");
		try {
			prepStmt = conn.prepareStatement("select * from movies m where m.id= ? ;");
		
			prepStmt.setInt(1, Integer.parseInt(request.getParameter("addToCart")));
			cartQuery = prepStmt.executeQuery();
			if(cartQuery.first())
			{
				CartItem cartItem = new CartItem(cartQuery.getInt(1), 
													cartQuery.getString(2),
													cartQuery.getInt(3),
													cartQuery.getString(4),
													cartQuery.getString(5),
													cartQuery.getString(6));
				cart.addToCart(cartItem);
				request.getSession().setAttribute("cart", cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(prepStmt != null)
			{
				try {
					prepStmt.close();
					prepStmt = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	private void populateStarLists(ResultSet rs, ArrayList<ArrayList<String>> moviesAndStars, ArrayList<String> starNames, 
			ResultSet starList, Statement stmt, Connection conn) {
		try {
			stmt = conn.createStatement();
			while(rs.next())
			{
				starList = stmt.executeQuery("select s.first_name, s.last_name"
						+ " FROM stars s, " + "stars_in_movies sm"
						+ " WHERE s.id=sm.star_id AND sm.movie_id=" + rs.getInt(1) + ";");
				
				
				starNames = new ArrayList<String>();
				while(starList.next())
				{
					starNames.add(starList.getString(1)+ " " + starList.getString(2));
				}
				moviesAndStars.add(starNames);
				
			}
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if(stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void populateGenreLists(ResultSet rs, ArrayList<ArrayList<String>> moviesAndGenres,
			ArrayList<String> genres, ArrayList<ArrayList<Integer>> moviesAndGenreIDs, ArrayList<Integer> genreIDs,
			ResultSet genreList, Statement stmt2, Connection conn) {
		try {
			stmt2 = conn.createStatement();
		
			while(rs.next())
			{
				genreList = stmt2.executeQuery("select g.name, g.id" +
							" FROM genres g, genres_in_movies gm" +
							" WHERE g.id=gm.genre_id AND gm.movie_id=" + rs.getInt(1) + ";");
				
				genres = new ArrayList<String>();
				genreIDs = new ArrayList<Integer>();
				genreList.beforeFirst();
				while(genreList.next())
				{
					genres.add(genreList.getString(1));
					genreIDs.add((Integer)genreList.getInt(2));
				}
				moviesAndGenres.add(genres);
				moviesAndGenreIDs.add(genreIDs);
				genreList.close();
			}
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    if ((session.getAttribute("email") == null) || (session.getAttribute("email") == "")) {
			String redirectURL = request.getContextPath() + Global.loginServletPath;
	        response.sendRedirect(redirectURL);
	        return;
	    }
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement prepStmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet starList = null;
		ResultSet genreList = null;
		ResultSet cartQuery = null;
		CachedRowSetImpl crs = null;
		ArrayList<String> argumentList = new ArrayList<String>();
		ArrayList<Integer> argumentTypes = new ArrayList<Integer>();
		try {

			
			
			
			/*************************   Establish SQL Connection  *****************************/
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(dburl, Global.DB_USER, Global.DB_PASS);
			
			
			stmt = conn.createStatement();
			
			if(request.getParameter("addToCart") != null && !request.getParameter("addToCart").equals(""))
			{
				executeAddItem(request, prepStmt, conn, cartQuery);
			}
			
			
			
			
			
			/******************   Build Query String Using Search Parameters   ******************/
			String queryString = "";
			if(request.getParameter("type") != null && !request.getParameter("type").equals(""))
			{
				if(request.getParameter("type").equals("genre"))
				{
					queryString = getGenreQueryString(request, argumentList, argumentTypes);
				}
				else if (request.getParameter("type").equals("title"))
				{
					queryString = getTitleQueryString(request, argumentList, argumentTypes);
				}
			} else if( searchParametersNotEmpty(request) ) {
				queryString = "SELECT sm.movie_id, m.title, m.year, m.director"
						+ " FROM movies m, stars s, " + "stars_in_movies sm"
						+ " WHERE s.id=sm.star_id AND m.id=sm.movie_id";

				Boolean mainSearch = isMainSearch(request);
				if(mainSearch)
				{
					queryString += " AND (";
					}
				if(request.getParameter("starname") != "" && request.getParameter("starname") != null)
				{
					queryString += getStarNameQueryString(request, mainSearch, argumentList, argumentTypes);
				}
				if(request.getParameter("title") != "" && request.getParameter("title") != null)
				{
					queryString += getTitleSearchQueryString(request, mainSearch, argumentList, argumentTypes);
				}
				if(request.getParameter("director") != "" && request.getParameter("director") != null)
				{
					queryString += getDirectorQueryString(request, mainSearch, argumentList, argumentTypes);
				}
				if(request.getParameter("year") != "" && request.getParameter("year") != null)
				{
					if(!mainSearch)
					{
						queryString += " AND";
					}
					
					queryString += " m.year= ? ";
					argumentList.add(request.getParameter("year"));
					argumentTypes.add(INT_ARG_TYPE);
				}
				if(mainSearch)
				{
					queryString += ") ";
				}
			}else{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp");
				dispatcher.include(request, response);
			}
			
			
			
			

			/**************   Finish Query String With Paging/Sorting Parameters(orderBypage, pageSize, offset)  ****************/
			queryString += " GROUP BY sm.movie_id ";
			
			int pageNum = getPageNum(request);
			int pageSize = getPageSize(request);
			String orderBy = getOrderBy(request);
			int offset = getOffset(request, pageNum, pageSize);
			
			prepStmt = conn.prepareStatement( queryString + "ORDER BY " + orderBy + " LIMIT " + pageSize +  " OFFSET " + offset + ";");
			fillPreparedStatement(prepStmt, argumentList, argumentTypes);
			rs = prepStmt.executeQuery();
			

			
			
			/**************   Get the Movies and Genres Associated With Each Result  ****************/
			ArrayList<ArrayList<String>> moviesAndStars = new ArrayList<ArrayList<String>>();
			ArrayList<String> starNames = null;
			populateStarLists(rs, moviesAndStars, starNames, starList, stmt2, conn);
			
			ArrayList<ArrayList<String>> moviesAndGenres = new ArrayList<ArrayList<String>>();
			ArrayList<String> genres = null;
			ArrayList<ArrayList<Integer>> moviesAndGenreIDs = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> genreIDs = null;
			populateGenreLists(rs, moviesAndGenres, genres, moviesAndGenreIDs, genreIDs, genreList, stmt2, conn);
			
			
			

			/**************   Send Request On to the JSP Passing Info Through Request Parameters  ****************/
			RequestDispatcher dispatcher = null;
			if(request.getParameter("addToCart") != null && !request.getParameter("addToCart").equals(""))
			{
				dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp?addToCart=" + request.getParameter("addToCart"));
			}
			else
			{
				dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp");
			}
			crs = new CachedRowSetImpl();
	        crs.populate(rs);
	        
			request.setAttribute("rs", crs);
			request.setAttribute("moviesAndStars", moviesAndStars);
			request.setAttribute("moviesAndGenres", moviesAndGenres);
			request.setAttribute("moviesAndGenreIDs", moviesAndGenreIDs);
			request.setAttribute("pageNum", pageNum);
			dispatcher.forward(request, response);
			
			
		} catch (SQLException e) {
			/*In Case of Failure, Forward to JSP Anyhow, Since rs is Not Set There Should Be No Visible Error*/
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/movie-list.jsp");
			dispatcher.forward(request, response);
			
			e.printStackTrace();
		} finally {
			try {
				if(cartQuery != null)
				{
					cartQuery.close();
				}
				if(genreList != null)
				{
					genreList.close();
				}
				if(starList != null)
				{
					starList.close();
				}
				if(rs != null)
				{
					rs.close();
				}
				if(prepStmt != null)
				{
					prepStmt.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}