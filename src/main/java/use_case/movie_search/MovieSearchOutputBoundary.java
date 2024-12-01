package use_case.movie_search;

/**
 * The interface for the moviesearchOutputBoundary.
 */
public interface MovieSearchOutputBoundary {
    /**
     * Prepares the success view for the Movie Search Use Case.
     * @param outputData the output data, can be null
     */
    void prepareSuccessView(MovieSearchOutputData outputData);

    /**
     * Prepares the failure view for the Movie Search Use Case.
     * @param errorMessage the explanation of the failure, can be null
     */
    void prepareFailView(String errorMessage);
}
