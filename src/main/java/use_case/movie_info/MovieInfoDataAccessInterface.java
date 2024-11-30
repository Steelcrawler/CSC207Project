package use_case.movie_info;

import entity.Movie;

/**
 * DAO for the Movie Info Use Case.
 */
public interface MovieInfoDataAccessInterface {
    /**
     * Retrieves a movie based on an ID.
     * @param movieID the ID of the movie to look for.
     * @return the movie matching the ID given.
     */
    Movie getMovieByID(int movieID);
}
