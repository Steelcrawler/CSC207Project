package use_case.movie_search;

public interface MovieSearchOutputBoundary {
        /**
         * Prepares the success view for the Movie Search Use Case.
         * @param outputData the output data
         */
        void prepareSuccessView(MovieSearchOutputData outputData);

        /**
         * Prepares the failure view for the Movie Search Use Case.
         * @param errorMessage the explanation of the failure
         */
        void prepareFailView(String errorMessage);
}
