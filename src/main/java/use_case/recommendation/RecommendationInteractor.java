package use_case.recommendation;
import entity.Movie;

import java.util.ArrayList;
import java.util.List;


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
            List<Movie> moviesList = this.TMDBDataAccessObject.searchRecommendations(recommendationInputData.getSelectedMoviesList());
            List<Integer> movieIDsOutput = new ArrayList<Integer>();
            List<String> movieTitlesOutput = new ArrayList<String>();
            List<String> posterPathsOutput = new ArrayList<String>();
            for (Movie movie : moviesList) {
                movieIDsOutput.add(movie.getMovieID());
                movieTitlesOutput.add(movie.getTitle());
                posterPathsOutput.add(movie.getPosterPath());
            }

            RecommendationOutputData recommendationOutputData = new RecommendationOutputData(movieIDsOutput, movieTitlesOutput, posterPathsOutput, false);
            this.recommendationPresenter.prepareSuccessView(recommendationOutputData);
        }
    }
}
