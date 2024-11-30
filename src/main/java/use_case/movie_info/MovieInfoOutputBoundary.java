package use_case.movie_info;

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

        void switchToWatchlistView();
}
