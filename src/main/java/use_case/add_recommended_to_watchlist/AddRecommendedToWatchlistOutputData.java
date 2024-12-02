package use_case.add_recommended_to_watchlist;

/**
 * Output Data for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistOutputData {

    private final String addedMessage;
    private final boolean useCaseFailed;

    public AddRecommendedToWatchlistOutputData(String movieTitle, boolean useCaseFailed) {
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
