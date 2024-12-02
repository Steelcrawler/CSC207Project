package use_case.add_recommended_to_watchlist;

/**
 * The Input Data for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistInputData {

    private final String movieTitle;
    private final Integer movieID;
    private final String posterPath;

    public AddRecommendedToWatchlistInputData(String movieTitle, Integer movieID, String posterPath) {
        this.movieTitle = movieTitle;
        this.movieID = movieID;
        this.posterPath = posterPath;
    }

    String getMovieTitle() {
        return movieTitle;
    }

    Integer getMovieID() {
        return movieID;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
