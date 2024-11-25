package use_case.delete_from_watchlist;

/**
 * The Input Data for the Delete From Watchlist Use Case.
 */
public class DeleteFromWatchlistInputData {

    private final String movieTitle;
    private final Integer movieID;

    public DeleteFromWatchlistInputData(String movieTitle, Integer movieID) {
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