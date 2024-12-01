package interface_adapter.recommendation;

import interface_adapter.select.SelectViewModel;
import interface_adapter.ViewManagerModel;
import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

public class RecommendationPresenter implements RecommendationOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final RecommendationViewModel recommendationViewModel;
    private final SelectViewModel selectViewModel;

    public RecommendationPresenter(ViewManagerModel viewManagerModel, RecommendationViewModel recommendationViewModel, SelectViewModel selectViewModel ) {
        this.viewManagerModel = viewManagerModel;
        this.recommendationViewModel = recommendationViewModel;
        this.selectViewModel = selectViewModel;
    }

    @Override
    public void prepareSuccessView(RecommendationOutputData outputData) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        recommendationState.setRecIDslist(outputData.getMovieIDsList());
        recommendationState.setMovieTitles(outputData.getMovieTitlesList());
        recommendationState.setPosterPaths(outputData.getPosterPathsList());
        recommendationState.setPlots(outputData.getPlotsList());
        recommendationState.setInputMovieIDs(outputData.getSelectedIDsOutput());
        recommendationViewModel.firePropertyChanged();

        viewManagerModel.setState(recommendationViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        System.out.println(outputData.getMovieIDsList());
        System.out.println(outputData.getMovieTitlesList());
        System.out.println(outputData.getPosterPathsList());
        System.out.println("got to recpresenter");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("fialed to recpresenter");
    }

    @Override
    public void toSelectView() {
        viewManagerModel.setState(selectViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
