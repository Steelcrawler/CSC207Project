package interface_adapter.recommendation;

import java.util.List;

public class RecommendationState {
    private List<Integer> recIDslist;
    private List<String> movieTitles;
    private List<String> posterPaths;
    private List<String> plots;
    private List<Integer> inputMovieIDs;
    private boolean emptyWatchlist;
    private String errorMessage;
    private String movieAddedToWatchlist = "";

    public List<Integer> getRecIDslist() {
        return recIDslist;
    }

    public void setRecIDslist(List<Integer> reclist) {
        this.recIDslist = reclist;
    }

    public List<String> getMovieTitles() {
        return movieTitles;
    }

    public void setMovieTitles(List<String> movieTitles) {
        this.movieTitles = movieTitles;
    }

    public List<String> getPlots() {
        return plots;
    }

    public void setPlots(List<String> plots) {
        this.plots = plots;
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

    public void setPosterPaths(List<String> posterPaths) {
        this.posterPaths = posterPaths;
    }

    public List<Integer> getInputMovieIDs() {
        return inputMovieIDs;
    }

    public void setInputMovieIDs(List<Integer> inputMovieIDs) {
        this.inputMovieIDs = inputMovieIDs;
    }

    public String getMovieAddedToWatchlist() {
        return movieAddedToWatchlist;
    }

    public void setMovieAddedToWatchlist(String movieAddedToWatchlist) {
        this.movieAddedToWatchlist = movieAddedToWatchlist;
    }
}
