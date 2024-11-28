package use_case.movieinfo;

import data_access.TMDBDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.movieinfo.MovieInfoPresenter;
import interface_adapter.movieinfo.MovieInfoViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.movieinfo.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MovieInfoInteractorTest {
    @Test
    void testWithLongMovieNameTest() {
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
    @Test
    void negativeIDTest() {
        MovieInfoInputData inputData = new MovieInfoInputData(-1); // Invalid ID
        MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();

        MovieInfoOutputBoundary testPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
            @Override
            public void prepareSuccessView(MovieInfoOutputData movie_info) {
                Assertions.fail("Expected failure, but success view was prepared.");
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Expected NullPointerException, but prepareFailView was called.");
            }
        };

        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, testPresenter);

        // Assert that a NullPointerException is thrown
        Assertions.assertThrows(NullPointerException.class, () -> interactor.execute(inputData));
    }
    @Test
    void zeroIDTest() {
        MovieInfoInputData inputData = new MovieInfoInputData(0); // Invalid ID
        MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();

        MovieInfoOutputBoundary testPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
            @Override
            public void prepareSuccessView(MovieInfoOutputData movie_info) {
                Assertions.fail("Expected failure, but success view was prepared.");
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Expected NullPointerException, but prepareFailView was called.");
            }
        };

        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, testPresenter);

        // Assert that a NullPointerException is thrown
        Assertions.assertThrows(NullPointerException.class, () -> interactor.execute(inputData));
    }

    void testWithShortMovieNameTest() {
        MovieInfoInputData inputData = new MovieInfoInputData(402431);
        MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();

        // This creates a successPresenter that tests whether the test case is as we expect.
        MovieInfoOutputBoundary successPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
            @Override
            public void prepareSuccessView(MovieInfoOutputData movie_info) {
                Assertions.assertEquals("Wicked", movie_info.getMovieTitle());
                Assertions.assertEquals(7.7, movie_info.getMovieRating());
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
//    void nullInputTest() {
//        MovieInfoInputData inputData = new MovieInfoInputData(null);
//        MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
//        ViewManagerModel viewManagerModel = new ViewManagerModel();
//        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();
//
//        // This creates a successPresenter that tests whether the test case is as we expect.
//        MovieInfoOutputBoundary successPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
//            @Override
//            public void prepareSuccessView(MovieInfoOutputData movie_info) {
//                Assertions.assertEquals("Wicked", movie_info.getMovieTitle());
//                Assertions.assertEquals(7.7, movie_info.getMovieRating());
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                Assertions.fail("Use case failure is unexpected.");
//            }
//        };
//
//        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, successPresenter);
//        interactor.execute(inputData);
//    }
    @Test
    void switchViewToWatchlist() {
        MovieInfoInputData inputData = new MovieInfoInputData(402431);
        MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();

        MovieInfoOutputBoundary testPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
            @Override
            public void prepareSuccessView(MovieInfoOutputData movie_info) {
                Assertions.assertEquals("Wicked", movie_info.getMovieTitle());
                Assertions.assertEquals(7.7, movie_info.getMovieRating());
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, testPresenter);
        String initialView = viewManagerModel.getState();

        interactor.switchView();

        String finalView = viewManagerModel.getState();
        Assertions.assertEquals("Watchlist", finalView);
    }

//    @Test
//    void testNullTMDBDataAccessObject() {
//        ViewManagerModel viewManagerModel = new ViewManagerModel();
//        MovieInfoViewModel movieInfoViewModel = new MovieInfoViewModel();
//        MovieInfoInputData inputData = new MovieInfoInputData(402431);
//        TMDBDataAccessObject tmdbDataAccessObject = null;
//
//        MovieInfoOutputBoundary testPresenter = new MovieInfoPresenter(viewManagerModel, movieInfoViewModel) {
//            @Override
//            public void prepareSuccessView(MovieInfoOutputData movie_info) {
//                Assertions.assertEquals("Wicked", movie_info.getMovieTitle());
//                Assertions.assertEquals(7.7, movie_info.getMovieRating());
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                Assertions.fail("Use case failure is unexpected.");
//            }
//        };
//        MovieInfoInputBoundary interactor = new MovieInfoInteractor(tmdbDataAccessObject, testPresenter);
//        interactor.execute(inputData);
//
//    }



}
