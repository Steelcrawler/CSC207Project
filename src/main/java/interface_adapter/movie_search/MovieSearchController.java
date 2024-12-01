package interface_adapter.movie_search;

import java.util.List;

import use_case.movie_search.MovieSearchInputBoundary;
import use_case.movie_search.MovieSearchInputData;

/**
 * Controller for the Movie Search Use Case.
 */
public class MovieSearchController {
    private final MovieSearchInputBoundary movieSearchUseCaseInteractor;

    public MovieSearchController(MovieSearchInputBoundary movieSearchUseCaseInteractor) {
        this.movieSearchUseCaseInteractor = movieSearchUseCaseInteractor;
    }
    /**
     * Executes the Movie Search Use Case.
     * @param title the title to search for.
     * @param genre the specific genre being searched for.
     * @param rating the rating threshold for the search.
     * @param keywords the list of keywords being searched for.
     */

    public void execute(String title, String genre, String rating, List<String> keywords) {
        final MovieSearchInputData movieSearchInputData = new MovieSearchInputData(
                title, genre, rating, keywords);

        movieSearchUseCaseInteractor.execute(movieSearchInputData);
    }
}
