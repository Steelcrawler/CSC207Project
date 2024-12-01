package use_case.movie_search;

import java.util.List;

import entity.Movie;

/**
 * Output Data for the Movie Search Use Case.
 */
public class MovieSearchOutputData {

    private final List<Movie> movies;
    private final boolean useCaseFailed;

    /**
     * Constructor for the MovieSearchOutputData class.
     * @param movies the list of movies to return.
     * @param useCaseFailed true if the use case failed; false otherwise.
     */
    public MovieSearchOutputData(List<Movie> movies, boolean useCaseFailed) {
        this.movies = movies;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Returns the list of movies.
     * @return the list of movies.
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Returns true if the use case failed; false otherwise.
     * @return true if the use case failed; false otherwise.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
