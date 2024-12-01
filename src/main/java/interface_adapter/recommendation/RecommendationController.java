package interface_adapter.recommendation;

import java.util.List;

import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInputData;

/**
 * The controller for the recommendation use case.
 */
public class RecommendationController {
    private final RecommendationInputBoundary recommendationInteractor;

    public RecommendationController(RecommendationInputBoundary recommendationInteractor) {
        this.recommendationInteractor = recommendationInteractor;
    }

    /**
     * Executes the recommendation use case.
     * @param list the list of movie ids that were selected to get a recommendation from.
     */
    public void execute(List<Integer> list) {
        final RecommendationInputData recommendationInputData = new RecommendationInputData(list);
        System.out.println("execute got called with " + list);
        recommendationInteractor.execute(recommendationInputData);
    }

    /**
     * Takes the user back to select view.
     */
    public void toSelectView() {
        recommendationInteractor.toSelectView();
    }
}
