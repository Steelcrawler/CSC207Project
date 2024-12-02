package interface_adapter.add_recommended_to_watchlist;

import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistInputBoundary;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistInputData;

/**
 * Controller for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistController {
    private final AddRecommendedToWatchlistInputBoundary addRecommendedToWatchlistUseCaseInteractor;

    public AddRecommendedToWatchlistController(
            AddRecommendedToWatchlistInputBoundary addRecommendedToWatchlistUseCaseInteractor) {
        this.addRecommendedToWatchlistUseCaseInteractor = addRecommendedToWatchlistUseCaseInteractor;
    }

    /**
     * Executes the Add Recommended To Watchlist Use Case.
     * @param movieTitle the title of the movie to add to the watchlist.
     * @param movieID the ID of the movie to add to the watchlist.
     */
    public void execute(String movieTitle, int movieID) {
        final AddRecommendedToWatchlistInputData addRecommendedToWatchlistInputData =
                new AddRecommendedToWatchlistInputData(movieTitle, movieID);

        addRecommendedToWatchlistUseCaseInteractor.execute(addRecommendedToWatchlistInputData);
    }
}

