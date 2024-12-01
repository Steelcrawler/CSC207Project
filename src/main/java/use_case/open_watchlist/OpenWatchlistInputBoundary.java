package use_case.open_watchlist;

/**
 * The Open Watchlist Use Case.
 */
public interface OpenWatchlistInputBoundary {

    /**
     * Execute the Open Watchlist Use Case.
     */
    void execute();

    /**
     * Switches from the WatchlistView to the MovieSearchView.
     */
    void switchToMovieSearchView();

    /**
     * Switches from the WatchlistView to the MovieInfoView.
     * @param movieID the ID of the movie to display in the MovieInfoView
     */
    void switchToMovieInfoView(int movieID);
}
