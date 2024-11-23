package use_case.open_watchlist;

/**
 * The Open Watchlist Use Case.
 */
public interface OpenWatchlistInputBoundary {

    /**
     * Execute the Open Watchlist Use Case.
     */
    void execute();

    void switchToMovieSearchView();
}
