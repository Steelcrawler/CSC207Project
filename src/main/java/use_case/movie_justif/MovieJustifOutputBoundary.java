package use_case.movie_justif;

/**
 * Output Boundary for movie justification use case.
 */
public interface MovieJustifOutputBoundary {
    /**
     * Prepares the success view for the Movie Rec Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(MovieJustifOutputData outputData);

    /**
     * Prepares the failure view for the Movie Rec Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switch the page back to Recommendation page.
     */
    void switchToRecView();
}
