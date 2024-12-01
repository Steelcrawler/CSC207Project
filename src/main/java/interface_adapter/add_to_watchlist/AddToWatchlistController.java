package interface_adapter.add_to_watchlist;

import use_case.add_to_watchlist.AddToWatchlistInputBoundary;
import use_case.add_to_watchlist.AddToWatchlistInputData;

/**
 * Controller for the Add To Watchlist Use Case.
 */
public class AddToWatchlistController {
    private final AddToWatchlistInputBoundary addToWatchlistUseCaseInteractor;

    public AddToWatchlistController(AddToWatchlistInputBoundary addToWatchlistUseCaseInteractor) {
        this.addToWatchlistUseCaseInteractor = addToWatchlistUseCaseInteractor;
    }

    /**
     * Executes the Add To Watchlist Use Case.
     * @param movieTitle the title of the movie to add to the watchlist.
     * @param movieID the ID of the movie to add to the watchlist.
     */
    public void execute(String movieTitle, int movieID) {
        final AddToWatchlistInputData addToWatchlistInputData = new AddToWatchlistInputData(movieTitle, movieID);

        addToWatchlistUseCaseInteractor.execute(addToWatchlistInputData);
    }
}

