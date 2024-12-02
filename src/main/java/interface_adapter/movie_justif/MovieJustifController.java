package interface_adapter.movie_justif;

import java.util.List;

import use_case.movie_justif.MovieJustifInputBoundary;
import use_case.movie_justif.MovieJustifInputData;

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
     * @param wantFrom is the ids of movies that the user wanted to be recommended from.
     * @param recommended is the id of the movie that is recommended to the user.
     */
    public void execute(List<Integer> wantFrom, int recommended) {
        final MovieJustifInputData movieJustifInputData = new MovieJustifInputData(wantFrom, recommended);

        System.out.println("TEST FOR LIST<INTEGER>: " + movieJustifInputData.getWantFrom());
        System.out.println("TEST FOR INT: " + movieJustifInputData.getRecommended());

        movieJustifUseCaseInteractor.execute(movieJustifInputData);
    }

    /**
     * Switch screen to the next page.
     */
    public void switchView() {
        movieJustifUseCaseInteractor.switchView();
    }
}
