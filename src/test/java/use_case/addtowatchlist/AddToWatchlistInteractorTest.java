package use_case.addtowatchlist;
import data_access.MongoDBUserDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.add_to_watchlist.*;

class AddToWatchlistInteractorTest {
    @Test
    void successTest() {
        AddToWatchlistInputData inputData = new AddToWatchlistInputData("Venom: The Last Dance", 912649);
        MongoDBUserDataAccessObject mongoDBUserDataAccessObject = new MongoDBUserDataAccessObject();
        mongoDBUserDataAccessObject.setCurrentUsername("john");
        mongoDBUserDataAccessObject.addToWatchlist("john", 921649);

        // This creates a successPresenter that tests whether the test case is as we expect.
        AddToWatchlistOutputBoundary successPresenter = new AddToWatchlistOutputBoundary() {
            @Override
            public void prepareSuccessView(AddToWatchlistOutputData outputData) {
                Assertions.assertEquals("Venom: The Last Dance has been added to the watchlist.", outputData.getAddedMessage());
            }

            public void prepareFailView(String message) {
                Assertions.assertEquals("This movie is already in your watchlist.", message);
            }
        };

        AddToWatchlistInputBoundary interactor = new AddToWatchlistInteractor(mongoDBUserDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}
