package use_case.add_recommended_to_watchlist;

/**
 * The Input Data for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistInputData {

    private final String movieTitle;
    private final Integer movieID;

    public AddRecommendedToWatchlistInputData(String movieTitle, Integer movieID) {
        this.movieTitle = movieTitle;
        this.movieID = movieID;
    }

    String getMovieTitle() {
        return movieTitle;
    }

    Integer getMovieID() {
        return movieID;
    }
}
