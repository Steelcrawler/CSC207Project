package use_case.recommendation;

import use_case.recommendation.RecommendationOutputData;

public interface RecommendationOutputBoundary {
    void prepareSuccessView(RecommendationOutputData outputData);

    /**
     * Prepares the failure view for the Recommendation Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
