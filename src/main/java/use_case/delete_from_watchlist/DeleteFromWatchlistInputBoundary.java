package use_case.delete_from_watchlist;

/**
 * The Delete From Watchlist Use Case.
 */
public interface DeleteFromWatchlistInputBoundary {

    /**
     * Execute the Delete From Watchlist Use Case.
     * @param deleteFromWatchlistInputData the input data for the Delete From Watchlist use case.
     */
    void execute(DeleteFromWatchlistInputData deleteFromWatchlistInputData);
}
