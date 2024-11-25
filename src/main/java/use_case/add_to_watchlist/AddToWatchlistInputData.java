package use_case.add_to_watchlist;

/**
 * The Input Data for the Add To Watchlist Use Case.
 */
public class AddToWatchlistInputData {

    private final String movieTitle;
    private final Integer movieID;

    public AddToWatchlistInputData(String movieTitle, Integer movieID) {
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