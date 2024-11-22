package interface_adapter.watchlist;

import java.util.List;

/**
 * The state for the Watchlist View Model.
 */
public class WatchlistState {
    private List<Integer> watchlist;

    public List<Integer> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Integer> watchlist) {
        this.watchlist = watchlist;
    }
}
