package interface_adapter.select;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Select View Model.
 */
public class SelectState {
    private List<Integer> watchlist;
    private List<String> movieTitles;
    private List<String> posterPaths;
    private boolean emptyWatchlist;
    private String errorMessage;
    private List<Integer> selectedMovies = new ArrayList<>();

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

    public List<String> getPosterPaths() {
        return posterPaths;
    }

    public void setSelectedMovies(List<Integer> selectedMovies) {
        this.selectedMovies = selectedMovies;
    }

    public List<Integer> getSelectedMovies() {
        return selectedMovies;
    }

    public void setPosterPaths(List<String> posterPaths) {
        this.posterPaths = posterPaths;
    }
}
