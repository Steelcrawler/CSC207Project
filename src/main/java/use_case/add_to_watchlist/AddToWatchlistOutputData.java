package use_case.add_to_watchlist;

/**
 * Output Data for the Add To Watchlist Use Case.
 */
public class AddToWatchlistOutputData {

    private final String addedMessage;
    private final boolean useCaseFailed;

    public AddToWatchlistOutputData(String movieTitle, boolean useCaseFailed) {
        this.addedMessage = movieTitle + " has been added to the watchlist.";
        this.useCaseFailed = useCaseFailed;
    }

    public String getAddedMessage() {
        return addedMessage;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
