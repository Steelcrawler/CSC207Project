package interface_adapter.watchlist;

import java.util.List;

/**
 * The state for the Watchlist View Model.
 */
public class WatchlistState {
    private List<Integer> watchlist;
    private List<String> movieTitles;
    private boolean emptyWatchlist;
    private String errorMessage;

    public List<Integer> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Integer> watchlist) {
        this.watchlist = watchlist;
    }

    public List<String> getMovieTitles() {
        return movieTitles;
    }

    public void setMovieTitles(List<String> movieTitles) {
        this.movieTitles = movieTitles;
    }

    public boolean isEmptyWatchlist() {
        return emptyWatchlist;
    }

    public void setEmptyWatchlist(boolean isEmpty) {
        this.emptyWatchlist = isEmpty;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
