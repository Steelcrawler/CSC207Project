package use_case.movie_search;

import java.util.List;

import entity.Movie;

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
     * @param title the title of the movie being searched for.
     * @return a list of Movies with that title.
     */
    List<Movie> searchMoviesByTitle(String title);

    /**
     * Returns a list of movies based on the title, genre, and rating limit provided. If a parameter is not provided,
     * it is ignored in the search.
     * @param title the title of the movie being searched for.
     * @param genre the genre of the movie being searched for.
     * @param rating the rating limit of the movie being searched for.
     * @param keywordIds the list of keyword ids to search for.
     * @return a list of Movies satisfying those criteria.
     */
    List<Movie> searchMovies(String title, String genre, Integer rating, List<Integer> keywordIds);
}
