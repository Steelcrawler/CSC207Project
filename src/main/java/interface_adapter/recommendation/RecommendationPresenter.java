package interface_adapter.recommendation;

import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

public class RecommendationPresenter implements RecommendationOutputBoundary {
    @Override
    public void prepareSuccessView(RecommendationOutputData outputData) {
        System.out.println("got to recpresenter");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("fialed to recpresenter");
    }
}
