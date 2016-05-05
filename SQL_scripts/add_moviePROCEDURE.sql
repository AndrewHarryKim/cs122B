use moviedb;

delimiter //
CREATE PROCEDURE add_movie( IN title varchar(100), IN year int(11), IN director varchar(100), 
				IN star_firstname varchar(50), IN star_lastname varchar(50), IN genrename varchar(32))
BEGIN

IF NOT EXISTS(SELECT * FROM movies m WHERE m.title=title) THEN
	IF NOT EXISTS(SELECT * FROM stars WHERE stars.first_name=star_firstname AND stars.last_name=star_lastname) THEN
		INSERT INTO stars (first_name, last_name) VALUES (star_firstname, star_lastname);
	END IF;
    
	IF NOT EXISTS(SELECT * FROM genres g WHERE g.name=genrename) THEN
		INSERT INTO genres (name) VALUES (genrename);
	END IF;
    
	INSERT INTO movies (title, year, director) VALUES (title, year, director);
    INSERT INTO stars_in_movies (star_id, movie_id) values 
						((select stars.id from stars where stars.first_name=star_firstname AND stars.last_name=star_lastname),
                        (select movies.id from movies where movies.title=title));
    INSERT INTO genres_in_movies (genre_id, movie_id) values 
						((select genres.id from genres where genres.name=genrename),
                        (select movies.id from movies where movies.title=title));
                        
END IF;

END//