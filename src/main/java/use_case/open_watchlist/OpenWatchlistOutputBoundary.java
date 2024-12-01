package use_case.open_watchlist;

/**
 * The output boundary for the Open Watchlist use case.
 */
public interface OpenWatchlistOutputBoundary {
    /**
    * Prepares the success view for the Open Watchlist Use Case.
    * @param outputData the output data
    */
    void prepareSuccessView(OpenWatchlistOutputData outputData);

    /**
    * Prepares the failure view for the Open Watchlist Use Case.
    * @param errorMessage the explanation of the failure
    */
    void prepareFailView(String errorMessage);

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
