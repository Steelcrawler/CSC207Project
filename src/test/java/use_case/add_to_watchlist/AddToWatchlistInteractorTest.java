package use_case.addtowatchlist;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.add_to_watchlist.*;

class AddToWatchlistInteractorTest {
    @Test
    void addToWatchlistTest() {
        AddToWatchlistInputData inputData = new AddToWatchlistInputData("Venom: The Last Dance", 912649);
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");

        // This creates a successPresenter that tests whether the test case is as we expect.
        AddToWatchlistOutputBoundary successPresenter = new AddToWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(AddToWatchlistOutputData outputData) {
                Assertions.assertEquals("Venom: The Last Dance has been added to the watchlist.", outputData.getAddedMessage());
            }

            public void prepareFailView(String message) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        AddToWatchlistInputBoundary interactor = new AddToWatchlistInteractor(inMemoryUserDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void alreadyInWatchlistTest() {
        AddToWatchlistInputData inputData = new AddToWatchlistInputData("Venom: The Last Dance", 912649);
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        User newUser = new CommonUserFactory().create("alaya", "yay");
        inMemoryUserDataAccessObject.save(newUser);
        inMemoryUserDataAccessObject.setCurrentUsername("alaya");
        inMemoryUserDataAccessObject.addToWatchlist("alaya", 912649);

        // This creates a successPresenter that tests whether the test case is as we expect.
        AddToWatchlistOutputBoundary successPresenter = new AddToWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(AddToWatchlistOutputData outputData) {
                Assertions.fail("Use case failure is unexpected.");
            }

            public void prepareFailView(String message) {
                Assertions.assertEquals("This movie is already in your watchlist.", message);
            }
        };

        AddToWatchlistInputBoundary interactor = new AddToWatchlistInteractor(inMemoryUserDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}

