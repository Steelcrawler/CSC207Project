package use_case.movieinfo;

import data_access.TMDBDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.movieinfo.MovieInfoPresenter;
import interface_adapter.movieinfo.MovieInfoViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.movieinfo.*;

public class MovieInfoInteractorTest {
    @Test
    void successTest() {
        MovieInfoInputData inputData = new MovieInfoInputData(671);
        MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();

        // This creates a successPresenter that tests whether the test case is as we expect.
        MovieInfoOutputBoundary successPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
            @Override
            public void prepareSuccessView(MovieInfoOutputData movie_info) {
                Assertions.assertEquals("Harry Potter and the Philosopher's Stone", movie_info.getMovieTitle());
            }

            @Override
            public void prepareFailView(String error) {
                    Assertions.fail("Use case failure is unexpected.");
                }
        };

        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}
