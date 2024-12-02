package use_case.add_recommended_to_watchlist;

/**
 * The output boundary for the add recommended to watchlist use case.
 */
public interface AddRecommendedToWatchlistOutputBoundary {
    /**
    * Prepares the success view for the Add Recommended To Watchlist Use Case.
    * @param outputData the output data
    */
    void prepareSuccessView(AddRecommendedToWatchlistOutputData outputData);

    /**
    * Prepares the failure view for the Add Recommended To Watchlist Use Case.
    * @param errorMessage the explanation of the failure
    */
    void prepareFailView(String errorMessage);
}
