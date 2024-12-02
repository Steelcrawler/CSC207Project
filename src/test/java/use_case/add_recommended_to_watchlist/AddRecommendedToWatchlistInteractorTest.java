package use_case.add_recommended_to_watchlist;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddRecommendedToWatchlistInteractorTest {
    @Test
    void addRecommendedToWatchlistTest() {
        AddRecommendedToWatchlistInputData inputData = new AddRecommendedToWatchlistInputData(
                "Venom: The Last Dance", 912649);
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");

        // This creates a successPresenter that tests whether the test case is as we expect.
        AddRecommendedToWatchlistOutputBoundary successPresenter = new AddRecommendedToWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(AddRecommendedToWatchlistOutputData outputData) {
                Assertions.assertEquals("Venom: The Last Dance has been added to the watchlist.",
                        outputData.getAddedMessage());
            }
            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("This movie is already in your watchlist.", message);
            }
        };

        AddRecommendedToWatchlistInputBoundary interactor = new AddRecommendedToWatchlistInteractor(
                inMemoryUserDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void alreadyInWatchlistTest() {
        AddRecommendedToWatchlistInputData inputData = new AddRecommendedToWatchlistInputData(
                "Venom: The Last Dance", 912649);
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("le", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("le");
        inMemoryUserDataAccessObject.addToWatchlist("le", 912649);

        // This creates a successPresenter that tests whether the test case is as we expect.
        AddRecommendedToWatchlistOutputBoundary successPresenter = new AddRecommendedToWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(AddRecommendedToWatchlistOutputData outputData) {
                Assertions.fail("Use case failure is unexpected.");
            }
            @Override
            public void prepareFailView(String message) {
                Assertions.assertEquals("This movie is already in your watchlist.", message);
            }
        };

        AddRecommendedToWatchlistInputBoundary interactor = new AddRecommendedToWatchlistInteractor(
                inMemoryUserDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}

