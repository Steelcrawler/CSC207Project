package use_case.add_recommended_to_watchlist;

/**
 * The Add Recommended To Watchlist Use Case.
 */
public interface AddRecommendedToWatchlistInputBoundary {

    /**
     * Execute the Add Recommended To Watchlist Use Case.
     * @param addRecommendedToWatchlistInputData the input data for this use case
     */
    void execute(AddRecommendedToWatchlistInputData addRecommendedToWatchlistInputData);
}
