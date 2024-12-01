package use_case.open_watchlist;
import data_access.InMemoryUserDataAccessObject;
import data_access.TMDBDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.watchlist.WatchlistViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OpenWatchlistInteractorTest {
    @Test
    void openWatchlistOneMovieTest() {
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
                Assertions.fail("Expected success view, but switchToMovieSearchView was called.");
            }

            @Override
            public void switchToMovieInfoView(int movieID) {
                Assertions.fail("Expected success view, but switchToMovieInfoView was called.");
            }
        };

        OpenWatchlistInputBoundary interactor = new OpenWatchlistInteractor(inMemoryUserDataAccessObject, tmdbDataAccessObject,
                successPresenter);
        interactor.execute();
    }

    @Test
    void openEmptyWatchlistTest() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        OpenWatchlistOutputBoundary successPresenter = new OpenWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(OpenWatchlistOutputData outputData) {
                Assertions.fail("Expected fail view, but success view was called.");
            }

            public void prepareFailView(String message) {
                Assertions.assertEquals("Your watchlist is empty.", message);
            }

            @Override
            public void switchToMovieSearchView() {
                Assertions.fail("Expected fail view, but switchToMovieSearchView was called.");
            }

            @Override
            public void switchToMovieInfoView(int movieID) {
                Assertions.fail("Expected fail view, but switchToMovieInfoView was called.");
            }
        };

        OpenWatchlistInputBoundary interactor = new OpenWatchlistInteractor(inMemoryUserDataAccessObject, tmdbDataAccessObject,
                successPresenter);
        interactor.execute();
    }

    @Test
    void switchToMovieSearchViewTest() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");
        inMemoryUserDataAccessObject.addToWatchlist("alaya", 912649);
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieSearchViewModel movieSearchViewModel = new MovieSearchViewModel();

        // This creates a successPresenter that tests whether the test case is as we expect.
        OpenWatchlistOutputBoundary successPresenter = new OpenWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(OpenWatchlistOutputData outputData) {
                Assertions.fail("Expected switchToMovieSearchView, but success view was called.");
            }

            public void prepareFailView(String message) {
                Assertions.fail("Expected switchToMovieSearchView, but fail view was called.");
            }

            @Override
            public void switchToMovieSearchView() {
                viewManagerModel.setState(movieSearchViewModel.getViewName());
                viewManagerModel.firePropertyChanged();
            }

            @Override
            public void switchToMovieInfoView(int movieID) {
                Assertions.fail("Expected switchToMovieSearchView, but switchToMovieInfoView was called.");
            }
        };

        OpenWatchlistInputBoundary interactor = new OpenWatchlistInteractor(inMemoryUserDataAccessObject, tmdbDataAccessObject,
                successPresenter);
        interactor.switchToMovieSearchView();
        String finalView = viewManagerModel.getState();
        Assertions.assertEquals("movie search", finalView);
    }

    @Test
    void switchToMovieInfoViewTest() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");
        inMemoryUserDataAccessObject.addToWatchlist("alaya", 912649);
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();

        // This creates a successPresenter that tests whether the test case is as we expect.
        OpenWatchlistOutputBoundary successPresenter = new OpenWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(OpenWatchlistOutputData outputData) {
                Assertions.fail("Expected switchToMovieInfoView, but success view was called.");
            }

            public void prepareFailView(String message) {
                Assertions.fail("Expected switchToMovieInfoView, but fail view was called.");
            }

            @Override
            public void switchToMovieSearchView() {
                Assertions.fail("Expected switchToMovieSearchView, but switchToMovieInfoView was called.");
            }

            @Override
            public void switchToMovieInfoView(int movieID) {
                viewManagerModel.setState(movieInfoViewModel.getViewName());
                viewManagerModel.firePropertyChanged();
            }
        };

        OpenWatchlistInputBoundary interactor = new OpenWatchlistInteractor(inMemoryUserDataAccessObject, tmdbDataAccessObject,
                successPresenter);
        interactor.switchToMovieInfoView(912649);
        String finalView = viewManagerModel.getState();
        Assertions.assertEquals("movie info", finalView);
    }
}