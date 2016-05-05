package fabflix;

public class CartItem {
	public int movieId;
	public String title;
	public int year;
	public String director;
	public String banner_url;
	public String trailer_url;
/*
 * 
id:integer (primary key)
title:varchar(100) 
year:integer 
director:varchar(100) 
banner_url:varchar(200) 
trailer_url:varchar(200) 
 */
	public CartItem(int movieId, String title, int year, String director, String banner_url,
			String trailer_url) {
		this.movieId = movieId;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner_url = banner_url;
		this.trailer_url = trailer_url;
	}

	public CartItem() {
		
//		this(000, "Test, The movie", 0001, "Director, the Director", 
//				"http://ia.media-imdb.com/images/M/MV5BNTY2ODQ4OTQ2OF5BMl5BanBnXkFtZTgwNTYxNzg2ODE@._V1__SX1171_SY563_.jpg",
//				"http://ia.media-imdb.com/images/M/MV5BNTY2ODQ4OTQ2OF5BMl5BanBnXkFtZTgwNTYxNzg2ODE@._V1__SX1171_SY563_.jpg");
	}

	
}
