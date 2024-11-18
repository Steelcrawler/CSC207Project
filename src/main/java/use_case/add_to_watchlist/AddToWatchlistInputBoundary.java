package use_case.add_to_watchlist;

/**
 * The Add To Watchlist Use Case.
 */
public interface AddToWatchlistInputBoundary {

    /**
     * Execute the Add To Watchlist Use Case.
     * @param addToWatchlistInputData the input data for this use case
     */
    void execute(AddToWatchlistInputData addToWatchlistInputData);
}
