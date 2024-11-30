package use_case.recommendation;
import entity.Movie;
import interface_adapter.recommendation.RecommendationPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The Recommendation Interactor.
 */
public class RecommendationInteractor implements RecommendationInputBoundary {
    private final RecommendationDataAccessInterface TMDBDataAccessObject;
    private final RecommendationOutputBoundary recommendationPresenter;

    public RecommendationInteractor(RecommendationDataAccessInterface recommendationDataAccessInterface,
                                    RecommendationOutputBoundary recommendationOutputBoundary) {
        this.TMDBDataAccessObject = recommendationDataAccessInterface;
        this.recommendationPresenter = recommendationOutputBoundary;
    }

    @Override
    public void execute(RecommendationInputData recommendationInputData) {
        if (TMDBDataAccessObject != null) {
            List<Movie> moviesList;
            if (recommendationInputData.getSelectedMoviesList().size() > 1) {
                List<Integer> keywordsList = new ArrayList<Integer>();
                for (Integer movieID : recommendationInputData.getSelectedMoviesList()) {
                    keywordsList.addAll(this.TMDBDataAccessObject.getKeywordIDs(movieID));
                }
                moviesList = this.TMDBDataAccessObject.searchMoviesByKeywordsHelper(keywordsList);
            } else {
                moviesList = this.TMDBDataAccessObject.searchRecommendations(recommendationInputData.getSelectedMoviesList());
            }
            List<Integer> movieIDsOutput = new ArrayList<Integer>();
            List<String> movieTitlesOutput = new ArrayList<String>();
            List<String> posterPathsOutput = new ArrayList<String>();
            List<String> plotsOutput = new ArrayList<String>();
            List<Integer> selectedIDsOutput = new ArrayList<Integer>(recommendationInputData.getSelectedMoviesList());

            for (Movie movie : moviesList) {
                movieIDsOutput.add(movie.getMovieID());
                movieTitlesOutput.add(movie.getTitle());
                posterPathsOutput.add(movie.getPosterPath());
                plotsOutput.add(movie.getPlot());
            }
            if (movieIDsOutput.size() == 0) {
                recommendationPresenter.prepareFailView("A Recommendation could not be made");
            } else {
                RecommendationOutputData recommendationOutputData = new RecommendationOutputData(movieIDsOutput,
                        movieTitlesOutput,
                        posterPathsOutput,
                        plotsOutput,
                        selectedIDsOutput,
                        false);
                this.recommendationPresenter.prepareSuccessView(recommendationOutputData);
            }
        }
    }

    @Override
    public void toSelectView() {
        recommendationPresenter.toSelectView();
    }
}
