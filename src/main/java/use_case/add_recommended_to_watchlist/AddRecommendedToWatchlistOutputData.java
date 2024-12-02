package use_case.add_recommended_to_watchlist;

/**
 * Output Data for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistOutputData {

    private final String addedMessage;
    private final boolean useCaseFailed;
    private final Integer movieID;
    private final String posterPath;
    private final String movieTitle;

    public AddRecommendedToWatchlistOutputData(String movieTitle, Integer movieID, String posterPath,
                                               boolean useCaseFailed) {
        this.addedMessage = movieTitle + " has been added to the watchlist.";
        this.useCaseFailed = useCaseFailed;
        this.movieID = movieID;
        this.posterPath = posterPath;
        this.movieTitle = movieTitle;
    }

    public String getAddedMessage() {
        return addedMessage;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
