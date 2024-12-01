package interface_adapter.movie_justif;

import entity.Movie;
import use_case.movie_justif.MovieJustifInputBoundary;
import use_case.movie_justif.MovieJustifInputData;
import java.util.ArrayList;
import java.util.List;


/**
 * The controller for the Movie Justif Use Case.
 */
public class MovieJustifController {
    private final MovieJustifInputBoundary movieJustifUseCaseInteractor;

    public MovieJustifController(MovieJustifInputBoundary movieJustifUseCaseInteractor) {
        this.movieJustifUseCaseInteractor = movieJustifUseCaseInteractor;
    }

    /**
     * Executes the Movie Justif Use Case.
     */
    public void execute(List<Integer> wantFrom, int recommended) {
        final MovieJustifInputData movieJustifInputData = new MovieJustifInputData(wantFrom, recommended);

        System.out.println("TEST FOR LIST<INTEGER>: " + movieJustifInputData.getWantFrom());
        System.out.println("TEST FOR INT: " + movieJustifInputData.getRecommended());

        movieJustifUseCaseInteractor.execute(movieJustifInputData);
    }

    public void switchView() {
        movieJustifUseCaseInteractor.switchView();
    }
}
