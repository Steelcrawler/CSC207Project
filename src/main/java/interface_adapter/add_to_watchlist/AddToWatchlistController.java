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
     * @param movieID the movie ID to add to the watchlist.
     * @param movieTitle the title of the movie to add to the watchlist.
     */
    public void execute(Integer movieID, String movieTitle) {
        final AddToWatchlistInputData addToWatchlistInputData = new AddToWatchlistInputData(
                movieID, movieTitle);

        addToWatchlistUseCaseInteractor.execute(addToWatchlistInputData);
    }
}