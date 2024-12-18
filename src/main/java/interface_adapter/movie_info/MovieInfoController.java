package interface_adapter.movie_info;

import use_case.movie_info.MovieInfoInputBoundary;
import use_case.movie_info.MovieInfoInputData;

/**
 * The controller for the Movie Info Use Case.
 */
public class MovieInfoController {
    private final MovieInfoInputBoundary movieInfoUseCaseInteractor;

    public MovieInfoController(MovieInfoInputBoundary movieInfoUseCaseInteractor) {
        this.movieInfoUseCaseInteractor = movieInfoUseCaseInteractor;
    }

    /**
     * Executes the Movie Info Use Case.
     * @param movieID the movie's ID.
     */
    public void execute(int movieID) {
        final MovieInfoInputData movieInfoInputData = new MovieInfoInputData(movieID);
        movieInfoUseCaseInteractor.execute(movieInfoInputData);
    }

    /**
     * Switch to Watchlist view.
     */
    public void switchView() {
        movieInfoUseCaseInteractor.switchView();
    }

}
