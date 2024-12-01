package use_case.delete_from_watchlist;

/**
 * The output boundary for the Delete From Watchlist use case.
 */
public interface DeleteFromWatchlistOutputBoundary {
    /**
    * Prepares the success view for the Delete From Watchlist Use Case.
    * @param outputData the output data
    */
    void prepareSuccessView(DeleteFromWatchlistOutputData outputData);

    /**
    * Prepares the failure view for the Delete From Watchlist Use Case.
    * @param errorMessage the explanation of the failure
    */
    void prepareFailView(String errorMessage);
}
