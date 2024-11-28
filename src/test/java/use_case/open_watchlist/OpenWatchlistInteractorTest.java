package use_case.open_watchlist;
import data_access.InMemoryUserDataAccessObject;
import data_access.TMDBDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OpenWatchlistInteractorTest {
    @Test
    void openWatchlistTest() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");
        inMemoryUserDataAccessObject.addToWatchlist("alaya", 912649);
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        OpenWatchlistOutputBoundary successPresenter = new OpenWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(OpenWatchlistOutputData outputData) {
                List<Integer> watchlist = new ArrayList<>();
                watchlist.add(912649);
                Assertions.assertEquals(watchlist, outputData.getMovieIDsList());
            }

            public void prepareFailView(String message) {
                Assertions.fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToMovieSearchView() {
                return;
            }

            @Override
            public void switchToMovieInfoView(int movieID) {
                return;
            }
        };

        OpenWatchlistInputBoundary interactor = new OpenWatchlistInteractor(inMemoryUserDataAccessObject, tmdbDataAccessObject,
                successPresenter);
        interactor.execute();
    }
}