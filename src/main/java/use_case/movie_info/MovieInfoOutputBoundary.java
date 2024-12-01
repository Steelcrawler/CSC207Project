package use_case.movie_info;

/**
 * Movie Info Output Boundary.
 */
public interface MovieInfoOutputBoundary {
    /**
     * Prepares the success view for the Movie Search Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(MovieInfoOutputData outputData);

    /**
     * Prepares the failure view for the Movie Search Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Prepares the watchlist view to switch back to the watchlist.
     */
    void switchToWatchlistView();
}
