package use_case.movie_search;

import java.util.List;
import entity.Movie;

/**
 * Output Data for the Movie Search Use Case.
 */
public class MovieSearchOutputData {

    private final List<Movie> movies;
    private final boolean useCaseFailed;

    public MovieSearchOutputData(List<Movie> movies, boolean useCaseFailed) {
        this.movies = movies;
        this.useCaseFailed = useCaseFailed;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
