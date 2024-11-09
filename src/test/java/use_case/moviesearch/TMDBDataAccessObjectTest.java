package use_case.moviesearch;

import data_access.TMDBDataAccessObject;
import entity.Movie;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TMDBDataAccessObjectTest {
    private TMDBDataAccessObject tmdbDataAccessObject;

    @Before
    public void setUp() {
        tmdbDataAccessObject = new TMDBDataAccessObject();
    }

    @Test
    public void testSearchMoviesByTitle() throws ParseException {
        List<Movie> movies = tmdbDataAccessObject.searchMoviesByTitle("Harry Potter");

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        // Check if "Harry Potter and the Philosopher's Stone" is in the list
        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getTitle().equals("Harry Potter and the Philosopher's Stone") &&
                movie.getMovieID() == 671 &&
                movie.getGenre().contains(12) &&
                movie.getGenre().contains(14) &&
                movie.getReleaseDate().equals(new SimpleDateFormat("yyyy-MM-dd").parse("2001-11-16")) &&
                movie.getRating() == 7.9 &&
                movie.getDescription().equals("Harry Potter has lived under the stairs at his aunt and uncle's house his whole life. But on his 11th birthday, he learns he's a powerful wizard—with a place waiting for him at the Hogwarts School of Witchcraft and Wizardry. As he learns to harness his newfound powers with the help of the school's kindly headmaster, Harry uncovers the truth about his parents' deaths—and about the villain who's to blame.")) {
                found = true;
                break;
            }
        }

        assertTrue("Harry Potter and the Philosopher's Stone is not in the list", found);
    }
}