package use_case.movie_search;

import data_access.TMDBDataAccessObject;
import entity.Movie;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TMDBDataAccessObjectTest {
    private TMDBDataAccessObject tmdbDataAccessObject;

    @Before
    public void setUp() {
        tmdbDataAccessObject = new TMDBDataAccessObject();
    }

    @Test
    public void testExistsByTitle() {
        boolean exists = tmdbDataAccessObject.existsByTitle("Harry Potter and the Philosopher's Stone");
        assertTrue("The movie 'Harry Potter and the Philosopher's Stone' should exist", exists);

        exists = tmdbDataAccessObject.existsByTitle("Nonexistent Movie Title");
        assertFalse(exists, "The movie 'Nonexistent Movie Title' should not exist");
    }

    @Test
    public void testSearchMoviesByTitle() throws ParseException {
        List<Movie> movies = tmdbDataAccessObject.searchMoviesByTitle("Harry Potter");

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getTitle().equals("Harry Potter and the Philosopher's Stone")) {
                found = true;
                break;
            }
        }

        assertTrue("Harry Potter and the Philosopher's Stone is in the list", found);
    }

    @Test
    public void testPopulateGenreMap() {
        // Verify the genreMap
        String result = tmdbDataAccessObject.getGenreName(28);
        System.out.println(result);
        assertNotNull(result);
        assertTrue("Action".equals(result));

        result = tmdbDataAccessObject.getGenreName(35);
        assertNotNull(result);
        assertTrue("Comedy".equals(result));
    }

    @Test
    public void testUserReviews() {
        List<Movie> movies = tmdbDataAccessObject.searchMoviesByTitle("Harry Potter");

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        boolean found = false;
        for (Movie movie : movies) {
            String firstReview = movie.getUserReviews().get(0);
            if (firstReview.contains("Harry Potter is an Orphan who on his eleventh birthday discovers he's a wizard and is called to term at Hogwarts School.")) {
                found = true;
                break;
            }
        }
        assertTrue("Harry Potter and the Philosopher's Stone has the correct user review", found);
    }

    @Test
    public void testGetTrailerLink() {
        List<Movie> movies = tmdbDataAccessObject.searchMoviesByTitle("Harry Potter");

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        boolean found = false;
        for (Movie movie : movies) {
            System.out.println(movie.getTrailerLink());
            if (movie.getTitle().equals("Harry Potter and the Philosopher's Stone")) {
                System.out.println(movie.getTrailerLink());
            }
            if (movie.getTrailerLink().equals("https://www.youtube.com/watch?v=l91Km49W9qI")) {
                found = true;
                break;
            }
        }
        assertTrue("Harry Potter and the Philosopher's Stone has the correct trailer link", found);
    }
}
    