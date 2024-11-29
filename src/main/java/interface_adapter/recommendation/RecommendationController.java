package interface_adapter.recommendation;

import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInputData;
import use_case.recommendation.RecommendationOutputBoundary;

import java.util.List;

public class RecommendationController {
    private final RecommendationInputBoundary recommendationInteractor;

    public RecommendationController(RecommendationInputBoundary recommendationInteractor) {
        this.recommendationInteractor = recommendationInteractor;
    }

    public void execute(List<Integer> list) {
        final RecommendationInputData recommendationInputData = new RecommendationInputData(list);
        System.out.println("execute got called with " + list);
        recommendationInteractor.execute(recommendationInputData);
    }

    public void toSelectView() {
        recommendationInteractor.toSelectView();
    }
}
