package use_case.delete_from_watchlist;

import java.util.List;

/**
 * Output Data for the Delete From Watchlist Use Case.
 */
public class DeleteFromWatchlistOutputData {
    private final List<Integer> movieIDsList;
    private final List<String> movieTitlesList;
    private final List<String> posterPathsList;
    private final boolean useCaseFailed;

    public DeleteFromWatchlistOutputData(List<Integer> movieIDsList, List<String> movieTitlesList,
                                         List<String> posterPathsList, boolean useCaseFailed) {
        this.movieIDsList = movieIDsList;
        this.movieTitlesList = movieTitlesList;
        this.posterPathsList = posterPathsList;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public List<Integer> getMovieIDsList() {
        return movieIDsList;
    }

    public List<String> getMovieTitlesList() {
        return movieTitlesList;
    }

    public List<String> getPosterPathsList() {
        return posterPathsList;
    }
}
