package use_case.delete_from_watchlist;

import data_access.InMemoryUserDataAccessObject;
import data_access.TMDBDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DeleteFromWatchlistInteractorTest {
    @Test
    void deleteFromWatchlistOneMovieTest() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");
        inMemoryUserDataAccessObject.addToWatchlist("alaya", 912649);
        inMemoryUserDataAccessObject.addToWatchlist("alaya", 671);

        List<Integer> selectedMovies = new ArrayList<>();
        selectedMovies.add(912649);
        DeleteFromWatchlistInputData inputData = new DeleteFromWatchlistInputData(selectedMovies);

        // This creates a successPresenter that tests whether the test case is as we expect.
        DeleteFromWatchlistOutputBoundary successPresenter = new DeleteFromWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteFromWatchlistOutputData outputData) {
                List<Integer> newWatchlist = new ArrayList<>();
                newWatchlist.add(671);
                Assertions.assertEquals(newWatchlist, outputData.getMovieIDsList());
            }
            @Override
            public void prepareFailView(String message) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        DeleteFromWatchlistInputBoundary interactor = new DeleteFromWatchlistInteractor(inMemoryUserDataAccessObject,
                tmdbDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void alreadyInWatchlistTest() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        User newUser = new CommonUserFactory().create("le", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("le");

        DeleteFromWatchlistInputData inputData = new DeleteFromWatchlistInputData(new ArrayList<>());

        // This creates a successPresenter that tests whether the test case is as we expect.
        DeleteFromWatchlistOutputBoundary successPresenter = new DeleteFromWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteFromWatchlistOutputData outputData) {
                Assertions.fail("Expected fail view but success view was called");
            }
            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("You did not select any movie to delete.", message);
            }
        };

        DeleteFromWatchlistInputBoundary interactor = new DeleteFromWatchlistInteractor(inMemoryUserDataAccessObject,
                tmdbDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}

