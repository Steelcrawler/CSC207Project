package use_case.add_to_watchlist;

/**
 * Output Data for the Add To Watchlist Use Case.
 */
public class AddToWatchlistOutputData {

    private final String movieTitle;
    private final boolean useCaseFailed;

    public AddToWatchlistOutputData(String movieTitle, boolean useCaseFailed) {
        this.movieTitle = movieTitle;
        this.useCaseFailed = useCaseFailed;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
