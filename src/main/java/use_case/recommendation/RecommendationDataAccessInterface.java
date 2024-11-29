package use_case.recommendation;

import entity.Movie;

import java.util.List;

public interface RecommendationDataAccessInterface {

    List<Movie> searchRecommendations(List<Integer> movies);
    List<Integer> getKeywordIDs(int IDs);
    List<Movie> searchMoviesByKeywordsHelper(List<Integer> keywords);
}
