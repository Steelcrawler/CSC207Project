package interface_adapter.moviesearch;

import java.util.List;

import use_case.moviesearch.MovieSearchInputBoundary;
import use_case.moviesearch.MovieSearchInputData;

/**
 * Controller for the Movie Search Use Case.
 */
public class MovieSearchController {
    private final MovieSearchInputBoundary movieSearchUseCaseInteractor;

    public MovieSearchController(MovieSearchInputBoundary movieSearchUseCaseInteractor) {
        this.movieSearchUseCaseInteractor = movieSearchUseCaseInteractor;
    }
//
//    /**
//     * Executes the Movie Search Use Case.
//     * @param title the title to search for
//     * @param genre the genre to filter by
//     * @param rating the rating to filter by
//     */
//    public void execute(String title, String genre, String rating) {
//        final MovieSearchInputData movieSearchInputData = new MovieSearchInputData(
//                title, genre, rating);
//
//        movieSearchUseCaseInteractor.execute(movieSearchInputData);
//    }
    /**
     * Executes the Movie Search Use Case.
     * @param title the title to search for.
     */

    public void execute(String title, String genre, String rating, List<String> keywords) {
        final MovieSearchInputData movieSearchInputData = new MovieSearchInputData(
                title, genre, rating, keywords);

        movieSearchUseCaseInteractor.execute(movieSearchInputData);
    }
}