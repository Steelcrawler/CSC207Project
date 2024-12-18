package interface_adapter.open_watchlist;

import use_case.open_watchlist.OpenWatchlistInputBoundary;

/**
 * Controller for the Open Watchlist Use Case.
 */
public class OpenWatchlistController {
    private final OpenWatchlistInputBoundary openWatchlistUseCaseInteractor;

    public OpenWatchlistController(OpenWatchlistInputBoundary openWatchlistUseCaseInteractor) {
        this.openWatchlistUseCaseInteractor = openWatchlistUseCaseInteractor;
    }

    /**
     * Executes the Open Watchlist Use Case.
     */
    public void execute() {
        openWatchlistUseCaseInteractor.execute();
    }

    /**
     * Switches from the WatchlistView to the MovieSearchView.
     */
    public void switchToMovieSearchView() {
        openWatchlistUseCaseInteractor.switchToMovieSearchView();
    }

    /**
     * Switches from the WatchlistView to the MovieInfoView.
     * @param movieID the ID of the movie to display on the movie info view.
     */
    public void switchToMovieInfoView(int movieID) {
        openWatchlistUseCaseInteractor.switchToMovieInfoView(movieID);
    }
}
