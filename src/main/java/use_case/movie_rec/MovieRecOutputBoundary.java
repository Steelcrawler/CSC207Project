package use_case.movie_rec;

import use_case.movie_rec.MovieRecOutputData;

public interface MovieRecOutputBoundary {
    /**
     * Prepares the success view for the Movie Rec Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(MovieRecOutputData outputData);

    /**
     * Prepares the failure view for the Movie Rec Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}