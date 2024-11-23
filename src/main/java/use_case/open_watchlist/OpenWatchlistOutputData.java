package use_case.open_watchlist;

import java.util.List;

/**
 * Output Data for the Open Watchlist Use Case.
 */
public class OpenWatchlistOutputData {

    private List<Integer> movieIDsList;
    private List<String> movieTitlesList;
    private final boolean useCaseFailed;

    public OpenWatchlistOutputData(List<Integer> movieIDsList, List<String> movieTitlesList, boolean useCaseFailed) {
        this.movieIDsList = movieIDsList;
        this.movieTitlesList = movieTitlesList;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public List<Integer> getMovieIDsList() {
        return movieIDsList;
    }

    public void setMovieIDsList(List<Integer> newMovieIDsList) {
        movieIDsList = newMovieIDsList;
    }

    public List<String> getMovieTitlesList() {
        return movieTitlesList;
    }

    public void setMovieTitlesList(List<String> movieTitlesList) {
        this.movieTitlesList = movieTitlesList;
    }
}
