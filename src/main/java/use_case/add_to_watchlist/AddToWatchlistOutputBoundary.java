package use_case.add_to_watchlist;

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
