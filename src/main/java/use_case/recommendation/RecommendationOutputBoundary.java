package use_case.recommendation;

/**
 * Interface for the output data for the recommendation use case.
 */
public interface RecommendationOutputBoundary {

    /**
     * Prepares the success view for the recommendation use case.
     * @param outputData the lists of movie info passed to presenter.
     */
    void prepareSuccessView(RecommendationOutputData outputData);

    /**
     * Prepares the failure view for the Recommendation Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the select view.
     */
    void toSelectView();
}
