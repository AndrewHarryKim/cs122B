SELECT sm.star_id, s.first_name, s.last_name, sm.movie_id, m.title
FROM movies m, stars s, stars_in_movies sm
WHERE s.id=sm.star_id AND m.id=sm.movie_id AND s.id=911
ORDER BY sm.star_id;