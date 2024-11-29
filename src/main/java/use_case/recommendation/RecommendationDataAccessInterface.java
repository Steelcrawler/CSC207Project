package use_case.recommendation;

import entity.Movie;

import java.util.List;

public interface RecommendationDataAccessInterface {

    List<Movie> searchRecommendations(List<Integer> movies);
}
