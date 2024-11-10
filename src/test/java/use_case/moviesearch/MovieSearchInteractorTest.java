package use_case.moviesearch;
import data_access.TMDBDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.movie_search.*;


class MovieSearchInteractorTest {
    @Test
    void successTest() {
        MovieSearchInputData inputData = new MovieSearchInputData("Harry Potter");
        MovieSearchDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        MovieSearchOutputBoundary successPresenter = new MovieSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(MovieSearchOutputData moviesList) {
                Assertions.assertEquals("Harry Potter and the Philosopher's Stone", moviesList.getMovies().get(0).getTitle());
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        MovieSearchInputBoundary interactor = new MovieSearchInteractor(tmdbDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}
