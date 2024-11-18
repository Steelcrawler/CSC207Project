package interface_adapter.moviesearch;

import use_case.movie_search.MovieSearchInputBoundary;
import use_case.movie_search.MovieSearchInputData;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

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

    public void execute(String title, String genre, String rating) {
        final MovieSearchInputData movieSearchInputData = new MovieSearchInputData(
                title, genre, rating);

        movieSearchUseCaseInteractor.execute(movieSearchInputData);
    }
}