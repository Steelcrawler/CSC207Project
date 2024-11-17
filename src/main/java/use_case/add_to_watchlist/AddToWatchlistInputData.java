package use_case.add_to_watchlist;

/**
 * The Input Data for the Add To Watchlist Use Case.
 */
public class AddToWatchlistInputData {

    private final int movieID;
    private final String movieTitle;

    public AddToWatchlistInputData(int movieID, String movieTitle) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
    }

    Integer getMovieID() {
        return movieID;
    }

    String getMovieTitle() {
        return movieTitle;
    }
}
