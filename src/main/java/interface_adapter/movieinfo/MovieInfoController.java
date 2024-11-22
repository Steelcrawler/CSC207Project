package interface_adapter.movieinfo;

import entity.Movie;
import use_case.movieinfo.MovieInfoInputBoundary;
import use_case.movieinfo.MovieInfoInputData;


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
     */
    public void execute(int movieID) {
        final MovieInfoInputData movieInfoInputData = new MovieInfoInputData(movieID);

        movieInfoUseCaseInteractor.execute(movieInfoInputData);
    }
}
