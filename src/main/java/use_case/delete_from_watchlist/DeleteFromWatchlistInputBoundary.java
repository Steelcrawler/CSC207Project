package use_case.delete_from_watchlist;

/**
 * The Delete From Watchlist Use Case.
 */
public interface DeleteFromWatchlistInputBoundary {

    /**
     * Execute the Delete From Watchlist Use Case.
     */
    void execute(DeleteFromWatchlistInputData deleteFromWatchlistInputData);
}