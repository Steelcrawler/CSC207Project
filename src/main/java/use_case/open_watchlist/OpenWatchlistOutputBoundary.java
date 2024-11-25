package use_case.open_watchlist;

public interface OpenWatchlistOutputBoundary {
        /**
         * Prepares the success view for the Open Watchlist Use Case.
         * @param outputData the output data
         */
        void prepareSuccessView(OpenWatchlistOutputData outputData);

        /**
         * Prepares the failure view for the Open Watchlist Use Case.
         * @param errorMessage the explanation of the failure
         */
        void prepareFailView(String errorMessage);

        void switchToMovieSearchView();
}
