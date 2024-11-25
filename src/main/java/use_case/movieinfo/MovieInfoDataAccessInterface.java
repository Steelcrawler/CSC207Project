package use_case.movieinfo;

import entity.Movie;

import java.util.List;

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
