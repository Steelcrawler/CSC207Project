package use_case.add_to_watchlist;

/**
 * The output boundary for the add to watchlist use case.
 */
public interface AddToWatchlistOutputBoundary {
    /**
    * Prepares the success view for the Add To Watchlist Use Case.
    * @param outputData the output data
    */
    void prepareSuccessView(AddToWatchlistOutputData outputData);

    /**
    * Prepares the failure view for the Add To Watchlist Use Case.
    * @param errorMessage the explanation of the failure
    */
    void prepareFailView(String errorMessage);
}
