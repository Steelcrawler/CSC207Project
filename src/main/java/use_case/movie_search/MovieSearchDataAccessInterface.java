package use_case.movie_search;

import entity.Movie;

import java.util.List;

/**
 * DAO for the Movie Search Use Case.
 */
public interface MovieSearchDataAccessInterface {

    /**
     * Checks if the given title exists.
     * @param title the title to look for.
     * @return true if a movie with the title exists; false otherwise.
     */
    boolean existsByTitle(String title);

    /**
     * Returns the list of movies with that title.
     * @return a list of Movies with that title.
     */
    List<Movie> searchMoviesByTitle(String title);

    /**
     * Returns a list of movies based on the title, genre, and rating limit provided. If a parameter is not provided, it is ignored in the search.
     * @return a list of Movies satisfying those criteria.
     */
    public List<Movie> searchMovies(String title, String genre, Integer rating);

    

}
