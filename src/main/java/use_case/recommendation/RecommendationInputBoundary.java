package use_case.recommendation;

/**
 * The Recommendation Use Case.
 */

public interface RecommendationInputBoundary {

    /**
     * Execute the Recommendation Use Case.
     * @param recommendationInputData the input data for this use case
     */
    void execute(RecommendationInputData recommendationInputData);

    /**
     * Takes the user to the select view.
     */
    void toSelectView();
}
