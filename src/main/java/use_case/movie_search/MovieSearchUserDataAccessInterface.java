package use_case.movie_search;

import entity.Movie;

/**
 * DAO for the Movie Search Use Case.
 */
public interface MovieSearchUserDataAccessInterface {

    /**
     * Checks if the given title exists.
     * @param title the title to look for.
     * @return true if a movie with the title exists; false otherwise.
     */
    boolean existsByTitle(String title);

    /**
     * Returns the movie with the given movie title.
     * @param title the title of the movie.
     * @return the movie with the given title.
     */
    Movie get(String title);

    /**
     * Returns the movie title of the given movie.
     * @return the title of the given movie.
     */
    String getMovieTitle();
}
